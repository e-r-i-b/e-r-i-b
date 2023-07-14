package com.rssl.phizic.web.common.mobile.payments.forms;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.auth.modes.SmsPasswordExistException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.strategies.limits.DocumentLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.RecipientByCardDocumentMobileChannelLimitStrategy;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.security.password.OneTimePassword;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.MoneyHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import com.rssl.phizic.web.servlet.HttpServletEditableRequest;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 01.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmMobilePaymentAction extends ConfirmDocumentAction
{
	private static final String SMS_PASSWORD_REAL_NAME = "confirmSmsPassword";
	private static final String PUSH_PASSWORD_REAL_NAME = "confirmPushPassword";
	private static final String CARD_PASSWORD_REAL_NAME = "confirmCardPassword";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("edit",    "edit");
		map.put("confirm", "confirm");
		map.put("button.preConfirm", "preConfirm");
		map.put("confirmSMS", "changeToSMS");
		map.put("confirmCard", "changeToCard");
		map.put("confirmPush", "changeToPush");
		return map;
	}

	protected String buildFormHtml(ConfirmFormPaymentOperation operation, ActionForm form) throws BusinessException
	{
		return operation.buildMobileXml(getTransformInfo(), getFormInfo(form));
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", "mobile");
	}

	protected FormInfo getFormInfo(ActionForm form) throws BusinessException
	{
		//достаем из сессии Set названий изменившихс€ полей и сразу же очищаем
		Set<String> changedFields = HttpSessionUtils.removeSessionAttribute(currentRequest(), SESSION_CHANGED_FIELDS_KEY);
		return new FormInfo(changedFields);
	}

	protected PreConfirmObject operationPreConfirm(HttpServletRequest request, CallBackHandler callBackHandler) throws Exception
	{
		PreConfirmObject preConfirmObject = null;
		try
		{
			ConfirmFormPaymentOperation operation = getOperation(request);
			ConfirmRequest confirmRequest = operation.getRequest();
			preConfirmObject = operation.preConfirm(callBackHandler);
			if( confirmRequest.getStrategyType() == ConfirmStrategyType.sms )
				updateSmsInfo(request, callBackHandler.getConfimableObject());
		}
		catch(Exception e)
		{
			if (e.getCause() instanceof SmsPasswordExistException)
				updateSmsInfo(request, callBackHandler.getConfimableObject());

			throw e;
		}
		return preConfirmObject;
	}

	/** ќбработка заполненой формы */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpServletEditableRequest req = new HttpServletEditableRequest (request);
		checkRequestParams(request);
		checkIdPayment(request);

		// дл€ SMS парол€
		if (!StringHelper.isEmpty(request.getParameter(SMS_PASSWORD_REAL_NAME)))
		{
			req.putParameter(Constants.CONFIRM_SMS_PASSWORD_FIELD, request.getParameter(SMS_PASSWORD_REAL_NAME));
		}
		else if (!StringHelper.isEmpty(request.getParameter(CARD_PASSWORD_REAL_NAME)))
		{
			// дл€ карточного парол€
			req.putParameter(Constants.CONFIRM_CARD_PASSWORD_FIELD, request.getParameter(CARD_PASSWORD_REAL_NAME));
		}
		else if (!StringHelper.isEmpty(request.getParameter(PUSH_PASSWORD_REAL_NAME)))
		{   //дл€ паролей по "Push"
			req.putParameter(Constants.CONFIRM_PUSH_PASSWORD_FIELD, request.getParameter(PUSH_PASSWORD_REAL_NAME));
		}

		ActionForward forward = super.confirm(mapping, form, req, response);

		if (FORWARD_SHOW_FORM.equals(forward.getName()))
		{
			ConfirmFormPaymentOperation operation = getOperation(request);
			updateSmsInfo(req, operation.getConfirmableObject());
		}
		return forward;
	}

	@Override
	protected DocumentLimitStrategy getRecipientLimitStrategy(BusinessDocument document) throws BusinessLogicException, BusinessException
	{
		return new RecipientByCardDocumentMobileChannelLimitStrategy(document);
	}

	@Override
	protected void addRecipientLimitMessages(DocumentLimitStrategy limitStrategy, HttpServletRequest request)
	{
		Money availableAmount = limitStrategy.getCurrentLimit().getAmount().sub(limitStrategy.getAccumulatedAmount());
		String message = String.format(getResourceMessage("paymentsBundle", "payment.limit.exceeded.general.confirm.recipient"), MoneyHelper.formatAmount(availableAmount)).replace("..", ".");
		saveError(request, message);
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmPaymentByFormForm    form      = (ConfirmPaymentByFormForm) frm;
		ConfirmFormPaymentOperation operation = getOperation(request, false);

		/*
		 * ≈сли нет активной операции
		 */
		if (operation == null)
		{
			operation = getConfirmOperation(request, form);
			ConfirmationManager.sendRequest(operation);
			saveOperation(request, operation);
		}

		return super.edit( mapping,  frm,  request,  response);
	}

	protected void saveConfirmErrors(List<String> errors, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		saveErrors(request, errors);
	}

	protected void clearConfirmErrors(HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		clearErrors(request); 
	}

	protected void saveConfirmMessages(List<String> messages, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ActionMessages errors  = new ActionMessages();
		for(String message : messages)
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( message , false));
		saveMessages(request, errors);
	}

	protected void addConfirmMessage(String message, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message,false));
		addMessages(request, messages);
	}

	/**
	 * ќбновл€ет в риквесте информацию об смс-пароле: оставшеес€ врем€ жизни и оставшеес€ кол-во попыток ввода
	 * @param request
	 * @param confirmableObject
	 * @throws SecurityDbException
	 */
	private void updateSmsInfo(HttpServletRequest request, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		if (confirmableObject==null || ((BusinessDocument)confirmableObject).getConfirmStrategyType() != ConfirmStrategyType.sms)
			return;

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		OneTimePassword smsPassword = SmsPasswordConfirmStrategy.getSmsPassword(login, confirmableObject);

		//врем€ жизни
		long lifeTime = (smsPassword == null) ? 0L : ((smsPassword.getExpireDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000);
		request.setAttribute(Constants.SMS_CURRENT_TIME_TO_LIVE, lifeTime);

		//кол-во попыток
		Long attemptsLeft = (smsPassword == null) ? 0L : (SmsPasswordConfirmStrategy.getWrongAttemptsCount() - smsPassword.getWrongAttempts());
		request.setAttribute(Constants.SMS_ATTEMPTS_LEFT, attemptsLeft);
	}

	@Override
	public ActionForward changeToSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		checkIdPayment(request);
		return super.changeToSMS(mapping, form, request, response);
	}

	@Override
	public ActionForward changeToPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		checkIdPayment(request);
		return super.changeToPush(mapping, form, request, response);
	}

	@Override
	public ActionForward changeToCard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		checkIdPayment(request);
		return super.changeToCard(mapping, form, request, response);
	}

	protected void checkIdPayment(HttpServletRequest request) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);

		String id = request.getParameter("id");
		if (StringHelper.isEmpty(id))
			throw new BusinessLogicException("Ќе заполнено поле Id платежа");

		if (!Long.valueOf(id).equals(operation.getDocument().getId()))
			throw new BusinessLogicException("id не соответствует подтверждаемому документу");
	}

	protected void checkRequestParams(HttpServletRequest request) throws BusinessException
	{
		String mobileSdkData = request.getParameter("mobileSdkData");
		if(StringHelper.isEmpty(mobileSdkData) && MobileApiUtil.getApiVersionNumber().ge(new VersionNumber(9, 1)))
			throw new BusinessException("Ќе указан об€зательный параметр \"»нформаци€ о мобильном устройстве\"");
	}
}
