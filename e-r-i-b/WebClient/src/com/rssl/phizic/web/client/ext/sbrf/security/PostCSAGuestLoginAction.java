package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.auth.csa.back.servises.GuestClaimType;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.GuestHelper;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.operations.security.GetAuthDataGuestOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.ext.sbrf.SBRFLoginActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niculichev
 * @ created 10.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PostCSAGuestLoginAction extends SBRFLoginActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String authToken = request.getParameter("AuthToken");
		//если клиент еще не аутентифицировался в ЦСА, перенаправляем его туда
		if (StringHelper.isEmpty(authToken))
		{
			return null;
		}

		//получаем данные аутентификации
		AuthData authData = getAuthData(authToken, request.getHeader("User-Agent"));

		// обновляем данными контекст
		AuthenticationContext context = getAuthenticationContext();
		GuestHelper.updateAuthenticationContext(context, authData);

		// обновляем типом заявки, с которой начнется работа в гостевом сболе
		String claimTypeStr = request.getParameter("claimType");
		if(StringHelper.isNotEmpty(claimTypeStr))
			context.setGuestClaimType(GuestClaimType.valueOf(claimTypeStr));

		// вытягиваем персону по данным аутентификации
		Person person = GuestHelper.synchronize(authData);

		// если установлено подразделение, проверяем его доступность
		if(person.getDepartmentId() != null)
			SecurityUtil.checkDepartmentIsServiced(person);

		// инициализируем контекст тем что нашли
		context.setPerson(person);
		context.setLogin(person.getLogin());

		// идем дальше
		return continueStage(mapping, request);
	}

	private AuthData getAuthData(String authToken, String browserInfo) throws BusinessLogicException, BusinessException
	{
		return new GetAuthDataGuestOperation(authToken, browserInfo).getAuthData();
	}

	protected ActionForward continueStage(ActionMapping mapping, HttpServletRequest request, ActionForm form) throws BusinessException, BusinessLogicException
	{
		completeStage();
		return createSuccessForward(mapping);
	}

	protected UserVisitingMode getUserVisitingMode()
	{
		return UserVisitingMode.GUEST;
	}

	protected ActionForward executeLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		tryStartAuthentication(mapping, request); // начинаем по новой, чтобы не продолжать с того, на чем остановились
		return super.executeLogin(mapping,form,request,response);
	}
}
