package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.userprofile.SetupOTPRestrictionOperation;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 13.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class SetupOTPRestrictionAction extends EditUserProfileActionBase
{
	private static final String FORWARD_START = "Start";
	protected static final String FORWARD_SUCCESS = "Success";
	private static final String INFO_MESSAGE = "В настоящее время изменение ограничений на получение чековых паролей по %s недоступно. Повторите попытку позднее.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("button.confirmSMS", "changeToSMS");
		keys.put("button.confirmCap", "changeToCap");
		keys.put("button.confirm","confirmOTPRestrictions");
		keys.put("button.saveOTPRestrictions", "saveOTPRestrictions");
		keys.put("button.backToEdit", "backToEdit");
		keys.put("button.nextStage", "doNextStage");
		return keys;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupOTPRestrictionForm frm = (SetupOTPRestrictionForm)form;
		SetupOTPRestrictionOperation operation = createOperation(SetupOTPRestrictionOperation.class);
		operation.initialize();

		frm.setCards(operation.getClientCards());
		updateForm(frm, operation.getClientCards());

		return mapping.findForward(FORWARD_START);
	}

	protected void updateForm(SetupOTPRestrictionForm frm, List<CardLink> entity) throws Exception
	{
		for (CardLink cardLink : entity)
		{
			String id = cardLink.getId().toString();
			frm.setField(SetupOTPRestrictionForm.OTP_GET_FIELD + id, cardLink.getOTPGet());
			frm.setField(SetupOTPRestrictionForm.OTP_USE_FIELD + id, cardLink.getOTPUse());
		}
	}

	/**
	 * Сохранить изменения настроек органичений на одноразовые пароли
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward saveOTPRestrictions( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupOTPRestrictionOperation operation = createOperation(SetupOTPRestrictionOperation.class);
		operation.initialize();

		SetupOTPRestrictionForm frm = (SetupOTPRestrictionForm)form;
		Form processorForm = frm.createForm(operation.getClientCards());
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, processorForm);

		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		if (!checkFormData(formProcessor.getResult(), operation))
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не можете разблокировать заблокированные чековые пароли.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		operation.setChangedCards(formProcessor.getResult());
		frm.setCards(operation.getClientCards());
		updateForm(frm, operation.getClientCards());

		if (CollectionUtils.isEmpty(operation.getChangedCards()))
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не внесли никаких изменений в настройки ограничений на получение одноразовых паролей.", false));
			saveErrors(request, message);
			return mapping.findForward(FORWARD_START);
		}

		operation.resetConfirmStrategy();
		ConfirmationManager.sendRequest(operation);
		frm.setConfirmableObject(operation.getConfirmableObject());
        frm.setConfirmStrategy(operation.getConfirmStrategy());
        saveOperation(request,operation);
		ActionMessages message = new ActionMessages();
		saveMessages(request, message);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Клиент нажал кнопку "подтвердить по смс"
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return Форвард
	 * @throws Exception
	 */
	public ActionForward changeToSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupOTPRestrictionOperation operation = getOperation(request);
        ActionForward forward =  preConfirmOTPRestrictions(operation,mapping,form,request,response);
		preConfirmAction(request, operation);
 		return forward;
	}
    /**
	 * Клиент нажал кнопку "подтвердить по cap"
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return Форвард
	 * @throws Exception
	 */
	public ActionForward changeToCap( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupOTPRestrictionOperation operation = getOperation(request);
        operation.setUserStrategyType(ConfirmStrategyType.cap);
        ActionForward forward =  preConfirmOTPRestrictions(operation,mapping,form,request,response);
        SetupOTPRestrictionForm frm = (SetupOTPRestrictionForm)form;
        ConfirmationManager.sendRequest(operation);

        frm.setConfirmStrategy(operation.getConfirmStrategy());
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(true);
		if (confirmRequest.getAdditionInfo() != null)
		{
			for (String str : confirmRequest.getAdditionInfo())
			{
				if (!StringHelper.isEmpty(str))
					confirmRequest.addMessage(str);
			}
		}

		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(operation.getWarning().getMessage()));
			return changeToSMS(mapping, frm, request, response);
		}
		confirmRequest.addMessage(ConfirmHelper.getPreConfirmCapString());
		return forward;
	}

    private ActionForward preConfirmOTPRestrictions(SetupOTPRestrictionOperation operation, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		SetupOTPRestrictionForm frm = (SetupOTPRestrictionForm) form;
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		Form processorForm = frm.createForm(operation.getClientCards());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, processorForm);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		if (!checkFormData(formProcessor.getResult(), operation))
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не можете разблокировать заблокированные чековые пароли.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		operation.setChangedCards(formProcessor.getResult());
		if (CollectionUtils.isEmpty(operation.getChangedCards()))
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не внесли никаких изменений в настройки ограничений на получение одноразовых паролей.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}
		frm.setChangedCards(operation.getChangedCards());
		frm.setConfirmableObject(operation.getConfirmableObject());

 		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Сохраняет измененные настройки ограничений
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return Форвард
	 * @throws Exception
	 */
	public ActionForward confirmOTPRestrictions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupOTPRestrictionOperation operation = getOperation(request);

		SetupOTPRestrictionForm frm = (SetupOTPRestrictionForm) form;
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		Form processorForm = frm.createForm(operation.getClientCards());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, processorForm);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		if (!checkFormData(formProcessor.getResult(), operation))
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не можете разблокировать заблокированные чековые пароли.", false));
			saveErrors(request, message);
			return mapping.findForward(FORWARD_START);
		}

		operation.setChangedCards(formProcessor.getResult());
		frm.setChangedCards(operation.getChangedCards());
		if (CollectionUtils.isEmpty(operation.getChangedCards()))
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не внесли никаких изменений в настройки ограничений на получение одноразовых паролей.", false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		return confirmSettings(operation, mapping, frm, request, response);
	}

	private ActionForward confirmSettings(SetupOTPRestrictionOperation operation, ActionMapping mapping, SetupOTPRestrictionForm frm, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (!errors.isEmpty() )
			{
				operation.getRequest().setErrorMessage(errors.get(0));
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_START);
			}
			else
			{
				// Сохранение делается при успешной валидации в методе saveConfirm
				operation.confirm();
				//operation.sendSmsNotification();
				if(isAjax())
					if (CollectionUtils.isEmpty(operation.getNotUpdatedCards()))
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
					else
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedCards()), null);
				else
				{
					ActionMessages actionMessages = new ActionMessages();
					if (CollectionUtils.isEmpty(operation.getNotUpdatedCards()))
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, (new ActionMessage("Данные успешно сохранены.", false)));
					else
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedCards()));
					saveMessages(request, actionMessages);
				}
				return doNextStage(mapping, frm, request, response);
			}
		}
		catch (BusinessLogicException ble)
		{
			saveError(request, ble);
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			operation.getRequest().setErrorMessage(e.getMessage());
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityException e) //упал сервис
		{
			operation.getRequest().setErrorMessage("Сервис временно недоступен, попробуйте позже");
			frm.setConfirmableObject(operation.getConfirmableObject());
			log.error(e.getMessage(), e);
			return mapping.findForward(FORWARD_START);
		}
	}

	private void preConfirmAction(HttpServletRequest request, SetupOTPRestrictionOperation operation) throws BusinessException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		try
		{
			ConfirmationManager.sendRequest(operation);
			operation.getRequest().setPreConfirm(true);
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(ConfirmStrategyType.sms, login, operation.getConfirmableObject()));
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			operation.getRequest().setPreConfirm(true);
		}
		catch (BusinessLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			operation.getRequest().setPreConfirm(true);
		}
	}

	/**
	 * Используется для редиректа с окна подтверждения одноразовым паролем, открытым через ajax, на следующий шаг.
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return Форвард
	 * @throws Exception
	 */
	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}

	private ActionMessage getInfoMessage(List<CardLink> links)
	{
		StringBuilder linkNames = new StringBuilder();
		for (CardLink link : links)
		{
			if (linkNames.length() != 0)
				linkNames.append(", ");
			linkNames.append("\"");
			linkNames.append(link.getName());
			linkNames.append(" ");
			linkNames.append(link.getNumber());
			linkNames.append("\"");
		}
		return new ActionMessage(String.format(INFO_MESSAGE, linkNames), false);
	}

	/**
	 * Возврат к редактированию ограничений по картам
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return Форвард
	 * @throws Exception
	 */
	public ActionForward backToEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupOTPRestrictionForm frm = (SetupOTPRestrictionForm) form;
		SetupOTPRestrictionOperation operation = createOperation(SetupOTPRestrictionOperation.class);
		operation.initialize();
		List<CardLink> cardLinks = operation.getClientCards();
		frm.setCards(cardLinks);
		frm.setField("unsavedData", true);
		updateForm(frm, cardLinks);

		return mapping.findForward(FORWARD_START);
	}

	private boolean checkFormData(Map<String, Object> results, SetupOTPRestrictionOperation operation)
	{
		List<CardLink> cardLinks = operation.getClientCards();
		boolean check = true;

		for (CardLink cardLink : cardLinks)
		{
			String id = cardLink.getId().toString();
			Boolean otpGet = (Boolean)results.get(SetupOTPRestrictionForm.OTP_GET_FIELD + id);
			Boolean newOTPUse = (Boolean)results.get(SetupOTPRestrictionForm.OTP_USE_FIELD + id);
			Boolean oldOTPUse = cardLink.getOTPUse();
			if (!otpGet && oldOTPUse != null && !oldOTPUse && newOTPUse)
				check = false;
		}
		return check;
	}
}

