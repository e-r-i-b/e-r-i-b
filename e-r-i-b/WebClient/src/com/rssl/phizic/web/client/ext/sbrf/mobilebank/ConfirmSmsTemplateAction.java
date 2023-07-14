package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ConfirmSmsTemplateOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.EditSmsTemplateOperation;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sim.CheckIMSIOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.AutoConfirmRequestType;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.web.client.ext.sbrf.mobilebank.MobilebankActionForwardHelper.appendRegistrationParams;
import static com.rssl.phizic.web.util.ActionForwardHelper.appendParam;

/**
 * @author Erkin
 * @ created 18.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class ConfirmSmsTemplateAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_SUCCESS = "Success";
	private static final String FORWARD_ERROR = "Error";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.preConfirm", "preConfirmSms");
		map.put("button.confirm", "confirm");
		map.put("button.confirmSMS", "preConfirmSms");
		map.put("button.confirmPush", "preConfirmPush");
		map.put("button.confirmCap", "changeToCap");
		return map;
	}

	private ConfirmSmsTemplateOperation createConfirmOperation(ActionForm form)
			throws BusinessException, BusinessLogicException
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		Long updateId = frm.getUpdateId();
		if (updateId == null)
			throw new BusinessException("Не указан ID апдейта списка шаблонов Мобильного Банка");

		ConfirmSmsTemplateOperation operation = createOperation("ConfirmMobileBankSmsTemplateOperation");
		operation.initialize(updateId);
		return operation;
	}

	private EditSmsTemplateOperation createEditOperation(ActionForm form)
			throws BusinessException, BusinessLogicException
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		Long updateId = frm.getUpdateId();
		if (updateId == null)
			throw new BusinessException("Не указан ID апдейта списка шаблонов Мобильного Банка");

		EditSmsTemplateOperation operation = createOperation("CreateMobileBankSmsTemplateOperation");
		operation.initialize(updateId);
		return operation;
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		return autoConfirm(mapping, form, request, response);
	}

	protected ActionForward autoConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    try
	    {
			SmsTemplateForm frm = (SmsTemplateForm) form;
			ConfirmSmsTemplateOperation operation = createConfirmOperation(form);
			ConfirmationManager.sendRequest(operation);
			updateForm(form, operation);

			saveOperation(request, operation);

		    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
			if (securityConfig.getNeedPaymentConfirmAutoselect() && getErrors(request).isEmpty())
		    {
				String userOptionType = AuthenticationContext.getContext().getPolicyProperties().getProperty("userOptionType");
			    if (StringHelper.isEmpty(userOptionType))
					userOptionType = "sms";

				ConfirmStrategyType type = ConfirmStrategyType.valueOf(userOptionType);
				if ((type == ConfirmStrategyType.sms || type == ConfirmStrategyType.push) && ConfirmHelper.strategySupported(operation.getConfirmStrategy(), type))
				{
					frm.setAutoConfirmRequestType(AutoConfirmRequestType.payment);
					return preConfirmBase(mapping, form, request, response, type);
				}
		    }

			return mapping.findForward(FORWARD_START);
	    }
	    catch (TemporalBusinessException e)
	    {
		    String exceptionMessage = "По техническим причинам операция временно недоступна. Повторите попытку позже";
		    ActionMessage error = new ActionMessage(exceptionMessage, false);
		    saveSessionError(exceptionMessage, error, null);
		    return mapping.findForward(FORWARD_START);
	    }
    }

	private void updateForm(ActionForm form, ConfirmSmsTemplateOperation operation)
			throws BusinessException, BusinessLogicException
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		frm.setUpdate(operation.getUpdate());
		frm.setCardLink(operation.getCardLink());
		frm.setSmsCommands(operation.getNewSmsCommands());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
	}

	public ActionForward changeToCap( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		ConfirmSmsTemplateOperation operation =  createConfirmOperation(form);	
		operation.setUserStrategyType(ConfirmStrategyType.cap);

		ConfirmationManager.sendRequest(operation);
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(true);
		frm.setConfirmStrategy(operation.getConfirmStrategy());
		if (confirmRequest.getAdditionInfo() != null)
		{
			for (String str : confirmRequest.getAdditionInfo())
			{
				if (!StringHelper.isEmpty(str))
					confirmRequest.addMessage(str);
			}
		}

		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.push)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(operation.getWarning().getMessage()));
			return preConfirmBase(mapping, frm, request, response, currentType);
		}
		updateForm(form, operation);
		confirmRequest.addMessage(ConfirmHelper.getPreConfirmCapString());
		saveOperation(request,operation);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Пользователь нажал кнопку "получить подтверждение (пароль)"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward preConfirmSms(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		return preConfirmBase(mapping, form, request, response, ConfirmStrategyType.sms);
	}

	public ActionForward preConfirmPush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return preConfirmBase(mapping, form, request, response, ConfirmStrategyType.push);
	}

	private ActionForward preConfirmBase(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ConfirmStrategyType confirmStrategy) throws Exception
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();

		ConfirmSmsTemplateOperation operation = createConfirmOperation(form);
		operation.setUserStrategyType(confirmStrategy);

		CallBackHandler callBackHandler;
		if (confirmStrategy == ConfirmStrategyType.sms)
			callBackHandler = new CallBackHandlerSmsImpl();
		else
			callBackHandler = new CallBackHandlerPushImpl();
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		// устанавливаем проверку IMSI при отправке смс(CHG043205)
		if (checkAccess(CheckIMSIOperation.class, "CheckIMSIService"))
			callBackHandler.setAdditionalCheck();

		ConfirmationManager.sendRequest(operation);

		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			operation.getRequest().setPreConfirm(true);
			if (preConfirmObject != null)
			{
				String clientInfo = (String) preConfirmObject.getPreConfirmParam(SmsPasswordConfirmStrategy.CLIENT_SEND_MESSAGE_KEY);
				if (!StringHelper.isEmpty(clientInfo))
					operation.getRequest().addMessage(clientInfo);
			}

			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader(
					"Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException ex)
		{
			operation.getRequest().setPreConfirm(true);
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(SecurityMessages.translateException(ex)));
		}

		updateForm(form, operation);
		saveOperation(request,operation);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Пользователь нажал кнопку "подтвердить"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		SmsTemplateForm frm = (SmsTemplateForm) form;
		Long updateId = frm.getUpdateId();
		ConfirmSmsTemplateOperation operation = getOperation(request);

		try
		{
			// 1. Проверяем подтверждение
			List<String> errors = validateConfirmResponse(operation, request);
			if (!CollectionUtils.isEmpty(errors)) {
				updateForm(form, operation);
				operation.getRequest().setErrorMessage(errors.get(0));
				return mapping.findForward(FORWARD_START);
			}

			// 2. Обновляем SMS-шаблоны
			EditSmsTemplateOperation editOperation = createEditOperation(form);
			editOperation.applyUpdate();

			ActionForward forward = mapping.findForward(FORWARD_SUCCESS);
			forward = appendRegistrationParams(forward, frm.getPhoneCode(), frm.getCardCode());
			forward = appendParam(forward, "updateId", updateId.toString());
			return forward;

		}
		catch (BusinessLogicException ex)
		{
			updateForm(form, operation);
			String errorMessage = ex.getMessage();
			saveSessionError(errorMessage, new ActionMessage(errorMessage, false), null);
			return mapping.findForward(FORWARD_ERROR);
		}
	}

	private List<String> validateConfirmResponse(ConfirmSmsTemplateOperation operation, HttpServletRequest request)
			throws BusinessException, BusinessLogicException
	{
		List<String> errors = ConfirmationManager.readResponse(
				operation, new RequestValuesSource(request));

		if (!CollectionUtils.isEmpty(errors))
			return errors;

		try
		{
			operation.confirm();
		}
		catch (SecurityLogicException ex)
		{
			return Arrays.asList(ex.getMessage());
		}

		return null;
	}
}
