package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentIsNotServicedException;
import com.rssl.phizic.business.login.IPasAuthHelper;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.LoginInfoHelper;
import com.rssl.phizic.business.login.exceptions.DegradationFromUDBOToCardException;
import com.rssl.phizic.business.login.exceptions.StopClientSynchronizationException;
import com.rssl.phizic.business.login.exceptions.StopClientsSynchronizationException;
import com.rssl.phizic.business.operations.restrictions.NullClientRestriction;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import com.rssl.phizic.web.security.AuthenticationManager;
import org.apache.struts.action.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 08.07.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������������ ������ � ���
 */
public class PostCSALoginAction extends SBRFLoginActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String authToken = getAuthToken(request);
		//���� ������ ��� �� ������������������ � ���, �������������� ��� ����
		if (StringHelper.isEmpty(authToken))
		{
			return csaForward(request);
		}

		AuthentificationSource authentificationSource = AuthentificationSource.full_version;
		log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, " ������ ������ � ���."));
		//� ��������� ������ ���� ����� ������� � ��������������� ��� � �������
		tryStartAuthentication(mapping, request);
		//�������� ������ ��������������
		String browserInfo = request.getHeader("User-Agent");
		AuthData authData = getAuthData(authToken, browserInfo);

		//��������� �������� �������������� ������� ��������������
		AuthenticationContext context = getAuthenticationContext();
		LoginHelper.updateAuthenticationContext(context, authData);

		StringBuilder logText = LoginInfoHelper.getPersonLogInfo(authData, authToken, "������ ������� �������������� ��� �������.", authentificationSource);
		log.info(logText);

		try
		{
			Person person = LoginHelper.synchronize(authData, authToken, authentificationSource, getUserVisitingMode(), NullClientRestriction.INSTANCE);

            SecurityUtil.checkDepartmentIsServiced(person);

			checkLogin(context, authData, person.getLogin(), null);
			return continueStage(mapping, request);
		}
        catch (DepartmentIsNotServicedException e)
        {
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(),false));
            saveErrors(request, errors);
            throw new BusinessException(e);
		}
		catch (StopClientSynchronizationException e)
		{
			checkLogin(context, authData, e.getLogin(), null);

			log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, ". ������ �� ������� �� ���������. " + e.getMessage()));

			if (e.isShowMessage())
			{
				context.putInactiveESMessage(e.getMessage());
			}

			return continueStage(mapping, request);
		}
		catch (DegradationFromUDBOToCardException e)
		{
			checkLogin(context, authData, e.getLogin(), null);
			//��������� ��� �������
			context.putMessage(e.getMessage());
			//� ����������� ��������������
			return continueStage(mapping, request);
		}
		catch (StopClientsSynchronizationException e)
		{
			checkLogin(context, authData, null, e.getIds());

			log.info(LoginInfoHelper.getMainLogInfo(authentificationSource, authToken, ". �� ������� ������� " + e.getIds().size() + " ��������� � ����� ��."));

			// � ������� �������� ����� ��� ���� �������, �������������� ��� �� �������� ������ ��������,
			// � ������� �� ����� ��������. � AuthenticationContext ���������� �������������� ���������
			// ������ ��� ��������� ������ � ��������� ���� ��������������.
			context.setPersonIds(e.getIds());
			completeStage();

			return mapping.findForward(FORWARD_SHOW);
		}
	}

	/**
	 * ��������� ������������� � ��������������� ��������.
	 *
	 * @param context �������� ��������������.
	 * @param login �����
	 * @param ids ������ ���������������.
	 * @param authData ������ �����������.
	 * @throws BusinessException ���� ����� ������ ��� ����, � ��� �� ����.
	 */
	protected void checkLogin(AuthenticationContext context, AuthData authData, Login login, List<Long> ids) throws BusinessException
	{
		if (login != null)
		{
			context.setLogin(login);
		}
	}

	protected AuthData getAuthData(String authToken, String browserInfo) throws BusinessLogicException, BusinessException
	{
		try
		{
			//TODO ������� � ��������
			AuthParamsContainer requestContainer  = IPasAuthHelper.createCheckSessionContainer(authToken);
			AuthParamsContainer responceContainer = AuthGateSingleton.getAuthService().checkSession(requestContainer);

			AuthData authData = new AuthData();
			AuthenticationHelper.fillFromIPasData(authData, responceContainer);
			return authData;
		}
		catch (AuthGateLogicException e)
		{
			log.error("�������� �����������, AuthToken = " + authToken);
			throw new BusinessLogicException(e);
		}
		catch (AuthGateException e)
		{
			log.error("������ ����������, AuthToken = " + authToken);
			throw new BusinessException(e);
		}
	}

	protected ActionForward csaForward(HttpServletRequest request)
	{
		AuthConfig config = AuthGateSingleton.getAuthService().getConfig();
		ActionRedirect forward = new ActionRedirect(config.getProperty("csa.login.url"));
		forward.addParameter("BackUrl", config.getProperty("ikfl.login.back.url"));
		forward.addParameter("NextService", "ESK");
		String returnTo = AuthenticationManager.getReturnTo(request);
		if (returnTo != null)
			forward.addParameter("returnTo", returnTo);
		return forward;
	}

	protected String getSkinUrl(ActionForm form)
	{
		//���� ��� ����� �� �����
		return "";
	}

	protected String getAuthToken(HttpServletRequest request)
	{
		return request.getParameter("AuthToken");
	}
}
