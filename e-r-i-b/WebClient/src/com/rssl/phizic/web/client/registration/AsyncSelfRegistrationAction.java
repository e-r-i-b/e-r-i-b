package com.rssl.phizic.web.client.registration;

import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.NoMoreAttemptsRegistrationException;
import com.rssl.phizic.auth.modes.OneTimePasswordConfirmStrategy;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.SelfRegistrationHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.forms.validators.login.ExistsLoginValidator;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.registration.SelfRegistrationOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.SmsErrorLogicException;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.util.StringFunctions;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Асинхронное получение данных для самостоятельной регистрации
 * @author basharin
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncSelfRegistrationAction extends SelfRegistrationAction
{
	private static final String FORWARD_SUCCESS = "Success";
	private static final String FORWARD_FIELD_ERROR = "FieldError";
	private static final String FORWARD_MESSAGE_ERROR = "MessageError";
	private static final String FORWARD_LOGIN_CHECKED = "LoginChecked";
	private static final String MESSAGE_SMS_ERROR = "Вы ввели неверный SMS-пароль";
	private static final String MESSAGE_SEND_NEW_SMS = "Вы несколько раз неправильно ввели пароль, получите новый пароль, нажав на ссылку \"Выслать новый SMS-пароль\"";
	private static final String INVALID_CODE_MESSAGE = "Введенный код не совпадает с кодом на картинке.";
	protected static final String CAPTCHA_CODE_PARAMETER_NAME   = "field(captchaCode)";
	private static final String URL_COMPLETE = "accounts.do?completeRegistration=true";

	@Override protected Map<String, String> getKeyMethodMap()
	{
	    Map<String,String> map = super.getKeyMethodMap();
		map.put("ajax.checkLogin", "checkLoginOld");
		map.put("checkLogin","checkLogin");
		map.put("save","save");
		map.put("sms","confirm");
		map.put("sendNewSms","sendNewSms");
		return map;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationOperation operation = createOperation(SelfRegistrationOperation.class);
		AsyncSelfRegistrationForm frm = (AsyncSelfRegistrationForm) form;

		if(CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME) && CaptchaServlet.wrongCaptcha(request, DEFAULT_CAPTCHA_SERVLET_NAME))
		{
			CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			frm.setNameFieldError(CAPTCHA_CODE_PARAMETER_NAME);
			frm.setTextError(INVALID_CODE_MESSAGE);
			frm.setNeedShowCaptcha(true);
			return mapping.findForward(FORWARD_FIELD_ERROR);
		}

		if (!validateForm(frm, request, operation))
		{
			frm.setNeedShowCaptcha(CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME));
			return mapping.findForward(FORWARD_FIELD_ERROR);
		}

		try
		{
			operation.startRegistration(
					(String) frm.getField(SelfRegistrationForm.LOGIN_FIELD_NAME),
					(String) frm.getField(SelfRegistrationForm.PASSWORD_FIELD_NAME),
					(String) frm.getField(SelfRegistrationForm.EMAIL_FIELD_NAME)
			);
			operation.setStrategyType();
			operation.setUserStrategyType(ConfirmStrategyType.sms);
			ConfirmationManager.sendRequest(operation);
			operation.getRequest().setPreConfirm(true);
			saveOperation(request, operation);
			frm.setToken((String)request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY));

			return doSendSms(mapping, frm, request, operation);
		}
		catch (NoMoreAttemptsRegistrationException ignore)
		{
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
		catch (BackLogicException ex)
		{
			frm.setTextError(ex.getMessage());
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
	}

	public ActionForward sendNewSms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationOperation op = getOperation(request);
		AsyncSelfRegistrationForm frm = (AsyncSelfRegistrationForm) form;
		return doSendSms(mapping, frm, request, op);
	}

	public ActionForward doSendSms(ActionMapping mapping, AsyncSelfRegistrationForm frm, HttpServletRequest request, SelfRegistrationOperation operation)
	{
		try
		{
			CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setAdditionalCheck();
			callBackHandler.setConfirmableObject(operation.getConfirmableObject());
			callBackHandler.setOperationType(OperationType.REGISTRATION_OPERATION);
			callBackHandler.setLogin(operation.getPersonData().getPerson().getLogin());
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));

			frm.setTimer(OneTimePasswordConfirmStrategy.getLifeTimePassword());
			CaptchaServlet.resetActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			return mapping.findForward(FORWARD_SUCCESS);
		}
		catch (SecurityLogicException ex)
		{
			frm.setTextError(StringFunctions.replaceQuotes(ex.getMessage()));
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
		catch (BusinessException ignore)
		{
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
		catch (BusinessLogicException e)
		{
			frm.setTextError(StringFunctions.replaceQuotes(e.getMessage()));
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
	}

	@Override public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationOperation op = getOperation(request);
		AsyncSelfRegistrationForm frm = (AsyncSelfRegistrationForm) form;

		List<String> errors = ConfirmationManager.readResponse(op, new RequestValuesSource(request));
		updateForm(op, frm);
		ConfirmHelper.clearConfirmErrors(op.getRequest());

		if (!errors.isEmpty())
		{
			ConfirmHelper.saveConfirmErrors(op.getRequest(), errors);
			frm.setTextError(StringFunctions.replaceQuotes(op.getRequest().getErrorMessage()));
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
		try
		{
			op.confirm();
			resetOperation(request);

			SelfRegistrationHelper.getIt().setWindowShowed(true);
			frm.setRedirect(URL_COMPLETE);
			return mapping.findForward(FORWARD_SUCCESS);
		}
		catch (SmsErrorLogicException ex)
		{
			op.getRequest().setPreConfirm(true);
			frm.setNameFieldError(Constants.CONFIRM_SMS_PASSWORD_FIELD);
			frm.setTextError(StringFunctions.replaceQuotes(MESSAGE_SMS_ERROR));
			return mapping.findForward(FORWARD_FIELD_ERROR);
		}
		catch (BusinessLogicException ex)
		{
			op.getRequest().setPreConfirm(false);
			frm.setTextError(StringFunctions.replaceQuotes(ex.getMessage()));
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
		catch (SecurityLogicException ex)
		{
			op.getRequest().setPreConfirm(true);
			frm.setTextError(StringFunctions.replaceQuotes(MESSAGE_SEND_NEW_SMS));
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
		catch (SecurityException e) //упал сервис
	    {
		    op.getRequest().setPreConfirm(true);
		    log.error(e.getMessage(), e);
		    return mapping.findForward(FORWARD_MESSAGE_ERROR);
	    }
	}

	@Override protected void saveErrors(HttpServletRequest request, ActionMessages errors, SelfRegistrationForm form)
	{
		AsyncSelfRegistrationForm frm = (AsyncSelfRegistrationForm) form;
		Iterator iterator = errors.get(ActionMessages.GLOBAL_MESSAGE);
		while (iterator.hasNext())
		{
			ActionMessage message = (ActionMessage)iterator.next();
			if (message.getValues() != null)
			{
				frm.setNameFieldError("field(" + message.getKey() + ")");
				frm.setTextError(StringFunctions.replaceQuotes(((ActionMessage)message.getValues()[0]).getKey()));
			}
		}
	}

	/**
	 * проверка логина на существование в базе ЦСА.
	 */
	public ActionForward checkLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME))
			return mapping.findForward(FORWARD_SUCCESS);

		AsyncSelfRegistrationForm frm = (AsyncSelfRegistrationForm) form;
		String loginForTest = (String)frm.getField(SelfRegistrationForm.LOGIN_FIELD_NAME);

		ExistsLoginValidator validator = new ExistsLoginValidator();
		if (StringHelper.isNotEmpty(loginForTest))
		{
			if (validator.validate(loginForTest))
				return mapping.findForward(FORWARD_SUCCESS);

			SelfRegistrationHelper.getIt().incCheckLoginCount();
			if (SelfRegistrationHelper.getIt().needShowCaptcha())
				CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
		}
		frm.setNeedShowCaptcha(SelfRegistrationHelper.getIt().needShowCaptcha());
		frm.setTextError(StringFunctions.replaceQuotes(validator.getMessage()));
		return mapping.findForward(FORWARD_FIELD_ERROR);
	}

	/**
	 * проверка логина на существование в базе ЦСА.
	 */
	public ActionForward checkLoginOld(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SelfRegistrationForm frm = (SelfRegistrationForm) form;
		String loginForTest = frm.getLogin();

		if (StringHelper.isEmpty(loginForTest))
		{
			frm.setLoginExists(false);
		}
		else
		{
			frm.setLoginExists(SelfRegistrationHelper.getIt().checkLogin(loginForTest));
		}
		return mapping.findForward(FORWARD_LOGIN_CHECKED);
	}

	@Override protected boolean isAjax()
	{
		return true;
	}

	@Override protected void updateForm(SelfRegistrationOperation operation, SelfRegistrationForm form) throws BusinessException{}
}
