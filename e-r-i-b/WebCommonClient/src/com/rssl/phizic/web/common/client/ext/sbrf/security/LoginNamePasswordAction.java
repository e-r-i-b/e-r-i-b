package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.LoginExistValidator;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.departments.DepartmentIsNotServicedException;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.LoginInfoHelper;
import com.rssl.phizic.business.login.exceptions.*;
import com.rssl.phizic.business.operations.restrictions.NullClientRestriction;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.operations.security.GetAuthDataOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.security.password.BlockedException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import com.rssl.phizic.web.security.LoginForm;
import com.rssl.phizic.web.security.LoginMessagesHelper;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 23.07.2010
 * Time: 17:57:29
 */
public class LoginNamePasswordAction extends SBRFLoginActionBase
{
	protected static final String FORWARD_RESET_MGUID = "mGUIDError";
	protected static final String FORWARD_API_ERROR = "ApiError";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
	    keyMap.put("button.login", "login");
		return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginForm frm = (LoginForm) form;

		FormProcessor<ActionMessages, ?> formProcessor =
							FormHelper.newInstance(new RequestValuesSource(currentRequest()), getLoginForm());

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			Map<String, Object> data = formProcessor.getResult();
			updateFormData(data);
			checkDeviceIdParameter(data);

			GetAuthDataOperation operation = createGetAuthDataOperation(data);
			AuthData authData = operation.getAuthData();

			if (request.getSession(false) != null)
			{
				writeFindProfileEvent(authData, authData.getBrowserInfo());
			}

			updateForm(form, data);
			updateAuthenticationContext(getAuthenticationContext(), data, authData);

			return doLogin(authData, mapping, form, request, response);
		}
		catch (ResetMobileGUIDException e)
		{
			log.error("�������� �����������. ���������� mGUID.", e);
			return mapping.findForward(FORWARD_RESET_MGUID);
		}
		catch (MobileVersionException e)
		{
			saveError(request, new ActionMessage(e.getMessage(), false));
			return mapping.findForward(FORWARD_API_ERROR);
		}
		catch (CanNotLoginException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage("error.login.no.found.department"));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (LoginOrPasswordWrongLoginExeption e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage("error.login.failed"));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (RegistrationErrorException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage("error.registration.failed"));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (AcceptedAttemptsLoginException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage("error.login.temporaty.blocked.message"));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (WrongCodeConfirmException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(e.getMessage(), false));

			frm.setSmsPasswordLifeTime(e.getTime());
			frm.setSmsPasswordAttemptsRemain(e.getAttempts());

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(Constants.COMMON_EXCEPTION_MESSAGE, false));
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			saveError(request, new ActionMessage(e.getMessage(), false));
			return mapping.findForward(FORWARD_SHOW);
		}
	}

	protected ActionForward doLogin(AuthData authData, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//�������� ���� ��������������. ������ ��� �����������. ����� �������� ���� ��������� ���������� ���� ����������� �������.
		String logKey = RandomHelper.rand(32, RandomHelper.ENGLISH_LETTERS + RandomHelper.DIGITS);
		AuthenticationContext context = getAuthenticationContext();
		//��������� �������� �������������� ������� ��������������
		LoginHelper.updateAuthenticationContext(context, authData);

		try
		{
			Person person = LoginHelper.synchronize(authData, logKey, getAuthSource(), getUserVisitingMode(), NullClientRestriction.INSTANCE);

            SecurityUtil.checkDepartmentIsServiced(person);

			Login login = person.getLogin();
			context.setLogin(login);
			return continueStage(mapping, request, login, authData);
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
			context.setLogin(e.getLogin());

			if (e.isShowMessage())
			{
				context.putInactiveESMessage(e.getMessage());
			}

			return continueStage(mapping, request);
		}
		catch (DegradationFromUDBOToCardException e)
		{
			context.setLogin(e.getLogin());
			//��������� ��� �������
			context.putMessage(e.getMessage());
			//� ����������� ��������������
			return continueStage(mapping, request);
		}
		catch (StopClientsSynchronizationException e)
		{
			if (CollectionUtils.isEmpty(e.getIds()))
			{
                if (e.isShowMessage())
				{
					// �� ������ �� iPas ������ �� ������ � ��, �� ���� �� ����� �������� ������� ��-��
					// ���������������� ��������. ���������� ������ � ���������� � ��������������� ��������.
					saveError(request, new ActionMessage(e.getMessage(), false));
					return mapping.findForward(FORWARD_SHOW);
				}
			}

			log.info(LoginInfoHelper.getMainLogInfo(getAuthSource(), request.getParameter("AuthToken"), ". �� ������� ������� " + e.getIds().size() + "  ��������(��) � ����� ��."));

			// � ������� �������� ����� ��� ���� �������, �������������� ��� �� �������� ������ ��������,
			// � ������� �� ����� ��������. � AuthenticationContext ���������� �������������� ���������
			// ������ ��� ��������� ������ � ��������� ���� ��������������.
			context.setPersonIds(e.getIds());
			completeStage();

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessLogicException ignore)
		{
			saveError(request, new ActionMessage("error.login.no.found.department"));
			return mapping.findForward(FORWARD_SHOW);
		}
	}

	protected Form getLoginForm()
	{
		return LoginForm.LOGIN_FORM;
	}

	protected AuthentificationSource getAuthSource()
	{
		return AuthentificationSource.full_version;
	}

	protected GetAuthDataOperation createGetAuthDataOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{
		throw new UnsupportedOperationException("��������� �������������� ����� ��� ������ �� ������������ ������ �����: ������������� �� ������-������ ������ ����� ���");
	}

	protected void updateForm(ActionForm form, Map<String, Object> data) throws BusinessException {	}

	protected void updateAuthenticationContext(AuthenticationContext context, Map<String, Object> data, AuthData authData) throws BusinessException {	}

	protected ActionForward continueStage(ActionMapping mapping, HttpServletRequest request, Login login, AuthData authData) throws BusinessException, BusinessLogicException
	{
		try
		{
			LoginExistValidator loginValidator = new LoginExistValidator();
			loginValidator.validateLoginInfo(login);
		}
		catch (BlockedException e)
		{
			if (ApplicationUtil.isATMApi() || ApplicationUtil.isSocialApi())
				throw new BusinessLogicException(e);
			log.error(e.getMessage(), e);
			saveError(request, LoginMessagesHelper.getLoginBlockMessage(login));
			if (ApplicationUtil.isMobileApi())
				return mapping.findForward(FORWARD_SHOW);
			return mapping.findForward(FORWARD_START);
		}

		LoginHelper.updateClientLogonData(login, authData);
		completeStage();

		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * �������� ��������� � ����� ������
	 * @param data - ������, ��������� � �����
	 * @throws BusinessException
	 */
	protected void updateFormData(Map<String, Object> data) throws BusinessException { }

	/**
	 * ��������� ������� �������������� ���������� � ��������� ������. ��������� ��� ���������� API ������ 6.00 � ����
	 * @param data - ������, ��������� � �����
	 * @throws BusinessException
	 */
	protected void checkDeviceIdParameter(Map<String, Object> data) throws BusinessException, MalformedVersionFormatException
	{
		if (!ApplicationUtil.isMobileApi())
			return;
		VersionNumber vNumber = VersionNumber.fromString((String) data.get("version"));
		if (vNumber.ge(MobileAPIVersions.V6_00))
		{
			if (data.get("devID") == null)
				throw new BusinessException("����������� ������������� ����������");
		}
	}
}
