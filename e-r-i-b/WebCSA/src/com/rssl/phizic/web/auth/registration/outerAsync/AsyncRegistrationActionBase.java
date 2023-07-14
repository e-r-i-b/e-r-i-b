package com.rssl.phizic.web.auth.registration.outerAsync;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.exceptions.LogableExceptionHandler;
import com.rssl.auth.csa.front.exceptions.SmsWasNotConfirmedInterruptStageException;
import com.rssl.auth.csa.front.exceptions.ValidateException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.RegistrationOperationInfo;
import com.rssl.auth.csa.front.operations.auth.ValidatePasswordOperation;
import com.rssl.auth.csa.front.operations.auth.ValidateRegistrationLoginOperation;
import com.rssl.auth.csa.front.security.NewRegistrationSecurityManager;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.config.FrontSettingHelper;
import com.rssl.phizic.config.RegistrationAccessType;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import com.rssl.phizic.web.auth.registration.RegistrationForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.rssl.auth.security.SecurityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Базовый экшен для асинхронной регистрации
 * @author niculichev
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class AsyncRegistrationActionBase extends AsyncAuthStageActionBase
{
	protected static final String START_REGISTRATION_FORWARD_NAME      = "start-reg";
	protected static final String CREATE_NEW_LOGIN_FORWARD_NAME = "create-new-login";
	protected static final String TURING_TEST_FAIL_FORWARD_NAME        = "captcha-fail";
	protected static final String INDEX_REDIRECT_FORWARD_NAME       = "index";
	protected static final String GLOBAL_LOGIN_RESTRICTION_MESSAGE = "По техническим причинам вход в систему временно ограничен. Попробуйте войти позднее";
	protected static final String REGISTRATION_CAPTCHA_SERVLET_NAME = "registrationCaptchaServlet";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("createNewLogin", "createNewLogin");
		return map;
	}

	/**
	 * метод возвращающий форвард для оповещения клиента о том что введённая им карта уже зарегистрирована
	 * @param mapping - маппинг экшенов
	 * @param frm - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return - форвард
	 */
	public ActionForward createNewLogin(ActionMapping mapping,  ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(CREATE_NEW_LOGIN_FORWARD_NAME);
	}

	/**
	 * Послать запрос на проверку логина
	 * @param mapping - маппинг экшенов
	 * @param form - текущая форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return - форвард до json-ответа с вердиктом проверки логина
	 * @throws Exception
	 */
	public ActionForward checkLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthStageFormBase frm = (AuthStageFormBase) form;
		if (FrontSettingHelper.getAsyncCheckingFieldsIsEnabled())
		{
			RegistrationOperationInfo info = (RegistrationOperationInfo) getOperationInfo(request);
			try
			{
				Map<String, Object> formData =
						checkFormData(RegistrationForm.CHECK_LOGIN_FORM, getFieldValuesSource(frm));

				ValidateRegistrationLoginOperation operation = new ValidateRegistrationLoginOperation();
				operation.initialize(info, (String) formData.get(RegistrationForm.LOGIN_FIELD_NAME));
				operation.execute();

				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_SUCCESS_STATE);
			}
			catch (ValidateException e)
			{
				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_FAIL_STATE);
				saveErrors(request, e.getMessages());
			}
			catch (FrontLogicException e)
			{
				saveError(request, new ActionMessage(RegistrationForm.LOGIN_FIELD_NAME, new ActionMessage(e.getMessage(), false)));
				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_FAIL_STATE);
			}
		}
		else
		{
			frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_SUCCESS_STATE);
		}
		return mapping.findForward(CHECK_FIELD_RESPONSE_NAME);
	}

	/**
	 * Послать запрос на проверку пароля
	 * @param mapping - маппинг экшенов
	 * @param form - текущая форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return - форвард до json-ответа с вердиктом проверки пароля
	 * @throws Exception
	 */
	public ActionForward checkPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthStageFormBase frm = (AuthStageFormBase) form;
		if (FrontSettingHelper.getAsyncCheckingFieldsIsEnabled())
		{
			RegistrationOperationInfo info = (RegistrationOperationInfo) getOperationInfo(request);
			try
			{
				Map<String, Object> formData =
						checkFormData(RegistrationForm.CHECK_PASSWORD_FORM, getFieldValuesSource(frm));

				ValidatePasswordOperation operation = new ValidatePasswordOperation();
				operation.initialize(info, (String) formData.get(RegistrationForm.PASSWORD_FIELD_NAME));
				operation.execute();

				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_SUCCESS_STATE);
			}
			catch (ValidateException e)
			{
				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_FAIL_STATE);
				saveErrors(request, e.getMessages());
			}
			catch (FrontLogicException e)
			{
				// проверка идет в разрезе поля
				saveError(request, new ActionMessage(RegistrationForm.PASSWORD_FIELD_NAME, new ActionMessage(e.getMessage(), false)));
				frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_FAIL_STATE);
			}
		}
		else
		{
			frm.setState(AsyncAuthStageActionBase.ASYNC_JSON_SUCCESS_STATE);
		}
		return mapping.findForward(CHECK_FIELD_RESPONSE_NAME);
	}

	public ActionForward specificCaptcha(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(TURING_TEST_FAIL_FORWARD_NAME);
	}

	@Override
	protected boolean processSpecificCaptha(ActionForm form, HttpServletRequest request, String operationName) throws Exception
	{
		try
		{
			if(!isCaptchaOperationName(operationName))
				return false;

			RegistrationOperationInfo operationInfo = (RegistrationOperationInfo) getOperationInfo(request);
			if(operationInfo == null || (operationInfo.getCurrentStage() != Stages.POST_CONFIRM && operationInfo.getCurrentStage() != Stages.EXIST_REG))
				return false;

			if(isRequiredShowCaptcha(request, RegistrationForm.REGISTRATION_CAPTCHA_SERVLET, NewRegistrationSecurityManager.getIt(), operationInfo.getKeyByUserInfo()))
			{
				CaptchaServlet.setActiveCaptha(request, RegistrationForm.REGISTRATION_CAPTCHA_SERVLET);
				return true;
			}

			// раз не нужно показывать, сбрасываем и флажок, чтоб не вылез где нибудь
			CaptchaServlet.resetActiveCaptha(request, RegistrationForm.REGISTRATION_CAPTCHA_SERVLET);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		return false;
	}

	protected boolean isCaptchaOperationName(String operationName)
	{
		return "next".equals(operationName) || "checkLogin".equals(operationName) || "checkPassword".equals(operationName);
	}

	protected List<String> getCaptchaMethodNames()
	{
		List<String> list = new ArrayList<String>();
		list.add("begin");
		return list;
	}

	protected boolean isRequiredShowCaptcha(HttpServletRequest request, String servletName, SecurityManager securityManager,String key, boolean isAlwaysShow)
	{

		return isRequiredShowCaptcha(request, servletName, securityManager, key, isAlwaysShow, true);
	}

	@Override
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setAttribute(LogableExceptionHandler.IS_REQUEST_AJAX_ATTRIBUTE_NAME, true);
		if(FrontSettingHelper.getRegistrationAccessType() != RegistrationAccessType.asyncPage)
		{
			throw new UnsupportedOperationException("Процедура ассинхронной регистрации запрещена");
		}
		return super.doExecute(mapping, form, request, response);
	}

	@Override
	protected OperationInfo getOperationInfo()
	{
		return new RegistrationOperationInfo(Stages.PRE_CONFIRM);
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(START_REGISTRATION_FORWARD_NAME);
	}

	@Override
	protected ActionForward doBeginStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception {
		try
		{
			return super.doBeginStage(mapping, frm, request, info);
		}
		catch (SmsWasNotConfirmedInterruptStageException e) {
			frm.setRedirect("/async/page/registration/timeout");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
	}

	@Override
	protected ActionForward doNextStage(ActionMapping mapping,  AuthStageFormBase frm, HttpServletRequest request, OperationInfo info) throws Exception {
		try
		{
			return super.doNextStage(mapping, frm, request, info);
		}
		catch (SmsWasNotConfirmedInterruptStageException e) {
			frm.setRedirect("/async/page/registration/timeout");
			return mapping.findForward(REDIRECT_FAIL_FORWARD_NAME);
		}
	}


}
