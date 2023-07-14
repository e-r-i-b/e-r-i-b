package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.OneTimePassword;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.ext.sbrf.security.ConfirmLoginAction;
import com.rssl.phizic.web.servlet.HttpServletEditableRequest;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author rydvanskiy
 * @ created 04.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmSocialLoginAction extends ConfirmLoginAction
{
	protected static final String FORWARD_CHANGE_STRATEGY  = "ChangeStrategy";
	protected static final String FORWARD_VALIDATE_ERROR = "ValidateError";
	private static final String SMS_PASSWORD_REAL_NAME = "smsPassword";
	private static final String PUSH_PASSWORD_REAL_NAME = "pushPassword";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map <String, String> keyMap= new HashMap<String,String>();
		keyMap.put("validate", "validate");
		keyMap.put("confirmSMS", "changeToSMS");
		keyMap.put("confirmCard", "changeToCard");
		keyMap.put("confirmPush", "changeToPush");
		return keyMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmSocialLoginForm frm = (ConfirmSocialLoginForm)form;

		// авто отправка sms если в настройках стоит необходимость + отсутствует друга€ стратеги€ подтверждени€
		if (ConfigFactory.getConfig(SecurityConfig.class).getNeedLoginConfirmAutoselect())
		{
			frm.setConfirmAutoStart(true);

			String confirmValue = getAuthenticationContext().getPolicyProperties().getProperty("userOptionType");
			if (confirmValue==null)
				confirmValue="sms";
			ConfirmStrategyType type = ConfirmStrategyType.valueOf(confirmValue);

			if (type == ConfirmStrategyType.sms)
			{
				changeToSMS(mapping, form, request, response);
				return mapping.findForward(FORWARD_SHOW);
			}
			else if (type == ConfirmStrategyType.push)
			{
				changeToPush(mapping, form, request, response);
				return mapping.findForward(FORWARD_SHOW);
			}
		}

		return super.start(mapping, form, request, response);
	}

	public ActionForward changeToCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.changeToCard(mapping, form, request, response);

		return mapping.findForward(FORWARD_CHANGE_STRATEGY);
	}

	public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.changeToSMS(mapping, form, request, response);

		return mapping.findForward(FORWARD_CHANGE_STRATEGY);
	}

	public ActionForward changeToPush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.changeToPush(mapping, form, request, response);

		return mapping.findForward(FORWARD_CHANGE_STRATEGY);
	}

	protected void doPreConfirm (HttpServletRequest request, ConfirmStrategy strategy, CallBackHandler callBackHandler, ConfirmRequest confirmRequest) throws SecurityDbException
	{
		super.doPreConfirm(request, strategy, callBackHandler, confirmRequest);
		updateSmsInfo(request, callBackHandler.getConfimableObject());
	}

	protected void saveConfirmError(String error, HttpServletRequest request)
	{
		if (StringHelper.isEmpty(error))
			return;
		// провер€ем ошибку среди сохраненных ошибок
		ActionMessages actionMessages = getErrors(currentRequest());
		Iterator iter = actionMessages.get();
		while (iter.hasNext())
		{
			ActionMessage actionError  = (ActionMessage)iter.next();
			// не добавл€ть уже имеющиес€ ошибки
			if (error.equals(actionError.getKey()) )
			   return;
		}

		ActionMessages actionErrors = new ActionMessages();
		actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
		saveErrors(request, actionErrors);
	}

	protected void saveConfirmErrors(List<String> errors, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		for (String error: errors)
			saveConfirmError(error.trim(), request);
	}

	protected void clearConfirmErrors(HttpServletRequest httpServletRequest, ConfirmRequest confirmRequest)
	{
		clearErrors(httpServletRequest);
	}

	protected void saveConfirmMessage(String message, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ActionMessages errors  = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( message , false));
		saveMessages(request, errors);
	}

	protected ActionForward forwardError(ActionMapping mapping, ActionForm form, ConfirmRequest confirmRequest)
	{
		return mapping.findForward(FORWARD_VALIDATE_ERROR);
	}

	public ActionForward validate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException, SecurityDbException
	{
		Map<String,String[]> aditionalParams = new TreeMap<String,String[]>();
		// дл€ SMS парол€
		if (!StringHelper.isEmpty(request.getParameter(SMS_PASSWORD_REAL_NAME)))
		{
			aditionalParams.put(Constants.CONFIRM_SMS_PASSWORD_FIELD,
					new String[]{request.getParameter(SMS_PASSWORD_REAL_NAME)});
		}
		else
		{
			//дл€ SMS парол€, полученного по технологии "Push"
			aditionalParams.put(Constants.CONFIRM_PUSH_PASSWORD_FIELD,
					new String[]{request.getParameter(PUSH_PASSWORD_REAL_NAME)});
		}

		HttpServletEditableRequest req = new HttpServletEditableRequest (request, aditionalParams);
		// валидируем данные с обернутым реквестом
		ActionForward actionForward = super.validate(mapping, form, req, response);
		updateSmsInfo(req, AuthModule.getAuthModule().getPrincipal().getLogin());
		return actionForward;
	}

	/**
	 * ќбновл€ет в риквесте информацию об смс-пароле: оставшеес€ врем€ жизни и оставшеес€ кол-во попыток ввода
	 * @param request
	 * @param confirmableObject
	 * @throws com.rssl.phizic.security.SecurityDbException
	 */
	private void updateSmsInfo(HttpServletRequest request, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		if (confirmableObject==null)
			return;

		//здесь confirmableObject = логин.
		OneTimePassword smsPassword = SmsPasswordConfirmStrategy.getSmsPassword((Login)confirmableObject, confirmableObject);

		//врем€ жизни
		long lifeTime = (smsPassword == null) ? 0L : ((smsPassword.getExpireDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000);
		request.setAttribute(Constants.SMS_CURRENT_TIME_TO_LIVE, lifeTime);

		//кол-во попыток
		Long attemptsLeft = (smsPassword == null) ? 0L : (SmsPasswordConfirmStrategy.getWrongAttemptsCount() - smsPassword.getWrongAttempts());
		request.setAttribute(Constants.SMS_ATTEMPTS_LEFT, attemptsLeft);
	}
}
