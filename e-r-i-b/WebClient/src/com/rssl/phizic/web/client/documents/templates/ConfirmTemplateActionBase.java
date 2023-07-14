package com.rssl.phizic.web.client.documents.templates;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.*;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.strategies.limits.OverallAmountPerDayTemplateLimitStrategy;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.UserIdClientTypes;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.documents.templates.*;
import com.rssl.phizic.operations.forms.ViewTemplateOperation;
import com.rssl.phizic.operations.payment.ConfirmTemplateOperation;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sim.CheckIMSIOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.InvalidUserIdException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.DefaultDocumentAction;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.documents.templates.TemplateFormBase;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 20.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ConfirmTemplateActionBase extends OperationalActionBase
{
	public static final String OPEN_PAYMENTS_AND_TEMPLATES = "PaymentsAndTemplates";

	protected abstract TemplateValidator[] getTemplateValidators();

	protected EditTemplateDocumentOperation createEditEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		EditTemplateDocumentOperation operation = createOperation(getEditOperationClass(source.getMetadata().getName()), source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	private Class<? extends EditTemplateDocumentOperation> getEditOperationClass(String serviceName)
	{
		if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER.getName().equals(serviceName))
		{
			return EditServicePaymentTemplateOperation.class;
		}
		if (FormType.JURIDICAL_TRANSFER.getName().equals(serviceName))
		{
			return EditJurPaymentTemplateOperation.class;
		}
		return EditTemplateOperation.class;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		ViewTemplateOperation operation = createOperation(ViewTemplateOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	protected RemoveTemplateOperation createRemoveEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		RemoveClientTemplateOperation operation = createOperation(RemoveClientTemplateOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	protected ConfirmTemplateOperation createConfirmEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		TemplateDocumentSource source = getTemplateSource(form);
		ConfirmTemplateOperation operation = createOperation(ConfirmTemplateOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		return operation;
	}

	protected <T extends Operation> T getOperation(HttpServletRequest request, boolean throwIfNull) throws NoActiveOperationException
	{
		T operation = super.<T>getOperation(request, throwIfNull);
		if (operation instanceof ConfirmableOperation)
		{
			ConfirmableOperation confirmableOperation = (ConfirmableOperation) operation;
			OperationContextUtil.synchronizeObjectAndOperationContext(confirmableOperation.getConfirmableObject());
		}
		return operation;
	}

	protected TemplateDocumentSource getTemplateSource(EditFormBase form) throws BusinessLogicException, BusinessException
	{
		TemplateFormBase frm = (TemplateFormBase) form;

		Long templateId = frm.getId();
		if (templateId == null)
		{
			throw new BusinessException("templateId не может быть null");
		}

		return createExistingTemplateSource(templateId);
	}

	protected TemplateDocumentSource createExistingTemplateSource(Long id) throws BusinessException, BusinessLogicException
	{
		return new ExistingTemplateSource(id, getTemplateValidators());
	}

	protected boolean needAutoConfirm(ConfirmTemplateOperation operation, HttpServletRequest request) throws BusinessLogicException, BusinessException
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		//проверка включения настройки
		if (!securityConfig.getNeedPaymentConfirmAutoselect())
		{
			return false;
		}

		//при наличии ошибок не отображаем
		if (!getErrors(request).isEmpty())
		{
			return false;
		}

		return LimitHelper.needObstructOperation(operation.getTemplate());
	}

	public ActionForward preConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ConfirmStrategyType confirmStrategy) throws Exception
	{
		ConfirmTemplateForm frm = (ConfirmTemplateForm) form;
		ConfirmTemplateOperation operation = getOperation(request);

		//При совершении операции всегда проверяется Суточный кумулятивный заградительный лимит по клиенту вне зависимости от групп получателей – в случае превышения операцию выполнить невозможно.
		if (LimitHelper.needObstructOperation(operation.getTemplate()))
		{
			saveConfirmErrors(Collections.singletonList(getResourceMessage("paymentsBundle", "payment.limit.exceeded.general.confirm.template")), operation.getRequest());

			updateForm(frm, operation);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return mapping.findForward(FORWARD_SHOW);
		}

		try
		{
			saveConfirmMessages(getConfirmMessages(operation.preConfirm(getCallBackHandler(confirmStrategy, operation))), operation.getRequest());
		}
		catch (SecurityException e)
		{
			saveError(request, new ActionMessage(e.getMessage(), false));
		}
		catch (SecurityLogicException e)
		{
			saveConfirmErrors(Collections.singletonList(SecurityMessages.translateException(e)), operation.getRequest());
		}

		updateForm(frm, operation);
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return mapping.findForward(FORWARD_SHOW);
	}

	protected ActionForward doConfirm(ActionMapping mapping, ConfirmTemplateForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmTemplateOperation operation = getOperation(request);

		try
		{

			operation.confirm();
			saveStateMachineEventMessages(request, operation, false);
			if (!isAjax())
			{
				resetOperation(request);
			}

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return createSuccessConfirmForward(operation);
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);
			saveStateMachineEventMessages(request, operation, true);

			updateForm(frm, operation);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			saveConfirmErrors(Collections.singletonList(e.getMessage()), operation.getRequest());
			saveStateMachineEventMessages(request, operation, true);

			updateForm(frm, operation);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (TemporalBusinessException ex)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					getResourceMessage("paymentsBundle", "message.operation.not.available"), false));
			saveErrors(request, actionErrors);
			saveStateMachineEventMessages(request, operation, true);

			updateForm(frm, operation);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return mapping.findForward(FORWARD_SHOW);

//			forward = forwardShow(mapping, operation, frm, request, false);
//			operation.getDocument().incrementCountError();
//			operation.save();
		}
		catch(RedirectBusinessLogicException ex)
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE,new ActionMessage(ex.getMessage(),false), null);
			saveStateMachineEventMessages(request, operation, true);
			return createNextStageDocumentForward(operation);
		}
		catch (IndicateFormFieldsException e)
		{
			// Ошибки и сообщения, для которых должны быть подсвечены поля
			ActionMessages actionErrors = new ActionMessages();
			boolean isEmptyFieldMessages = true;
			for (Map.Entry<String, String> entry : e.getFieldMessages().entrySet())
			{
				actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(entry.getKey(), new ActionMessage(entry.getValue(), false)));
				if (!StringHelper.isEmpty(entry.getValue()) && isEmptyFieldMessages)
				{
					isEmptyFieldMessages = false;
				}
			}
			if (e.isError())
			{
				saveErrors(request, actionErrors);
			}
			else
			{
				// Если ни для одного из полей нет сообщения с описанием проблемы, добавляем общее сообщение в
				// блоке информационных сообщений
				if (isEmptyFieldMessages)
				{
					actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				}
				saveMessages(request, actionErrors);
			}
			saveStateMachineEventMessages(request, operation, true);

			updateForm(frm, operation);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return mapping.findForward(FORWARD_SHOW);
		}
		catch (BusinessLogicWithBusinessDocumentException e)
		{
			// Не показываем popup-окно с предложением ввести пароль
			operation.getRequest().setPreConfirm(false);

			return createBadConfirmForward(operation);
		}
		catch (BusinessLogicException e) // ошибка логики
		{
			// Не показываем popup-окно с предложением ввести пароль
			operation.getRequest().setPreConfirm(false);

			if (isAjax())
			{
				saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
				updateForm(frm, operation);
				return createBadConfirmForward(operation);
			}
			else
			{
				saveError(request, e);
				saveStateMachineEventMessages(request, operation, true);

				updateForm(frm, operation);
				addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

				return mapping.findForward(FORWARD_SHOW);
			}
		}
		catch (SecurityException e) //упал сервис
		{
			saveConfirmErrors(Collections.singletonList("Сервис временно недоступен, попробуйте позже"), operation.getRequest());
			saveStateMachineEventMessages(request, operation, true);

			updateForm(frm, operation);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

			return mapping.findForward(FORWARD_SHOW);

			//operation.getDocument().incrementCountError();
			//operation.save();
		}
//		if(operation.getRequest().getStrategyType() == ConfirmStrategyType.card)
//			frm.setField("confirmByCard",true);
	}

	protected void updateForm(ConfirmTemplateForm frm, ConfirmTemplateOperation operation) throws BusinessException, BusinessLogicException
	{
		frm.setTemplate(operation.getTemplate());
		frm.setHtml(operation.buildFormHtml(getTransformInfo(), getFormInfo()));
		frm.setFormName(operation.getMetadata().getName());
		frm.setFormDescription(operation.getMetadata().getForm().getDescription());
		frm.setMetadata(operation.getMetadata());
		frm.setMetadataPath(operation.getMetadataPath());
		frm.setConfirmStrategyType(operation.getStrategyType());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
		frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());

		saveMessages(currentRequest(), operation.collectInfo());
		addDocumentLimitsInfoMessages(operation);
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", getCurrentMapping().getPath().endsWith("print") ? "print" : "html");
	}

	protected FormInfo getFormInfo()
	{
		return new FormInfo(WebContext.getCurrentRequest().getContextPath(), currentServletContext().getInitParameter("resourcesRealPath"));
	}

	protected void saveConfirmErrors(List<String> errors, ConfirmRequest confirmRequest)
	{
		ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
	}

	protected void saveConfirmMessages(List<String> messages, ConfirmRequest confirmRequest)
	{
		for(String message : messages)
		{
			confirmRequest.addMessage(message);
		}
	}

	protected void clearConfirmErrors(HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ConfirmHelper.clearConfirmErrors(confirmRequest);
	}

	/**
	 * В случае подтверждения по чеку. UserId в запросе для iPasPasswordCardConfirmStrategy
	 * необходимо указывать для выбранной карты клиента, полученный из МБ или из нашей базы.
	 * @param frm форма
	 * @param operation операция
	 * @throws BusinessException
	 */
	protected void addConfirmCardParameter(ConfirmTemplateForm frm, ConfirmableOperationBase operation, boolean useStoredValue) throws BusinessException, BusinessLogicException
	{
		String confirmCardNumberId = (String) frm.getField("confirmCardId");
		//если передан идентифакатор карты подтверждения, то подтверждать будем по ней.
		if (!StringHelper.isEmpty(confirmCardNumberId))
		{
			BusinessDocument document = (BusinessDocument) operation.getConfirmableObject();
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Pair<UserIdClientTypes, String> userId = ConfirmHelper.getUserIdByConfirmCard(Long.valueOf(confirmCardNumberId), documentOwner.getLogin(), useStoredValue);

			if (operation.getPreConfirm() == null)
			{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("confirmUserId", userId);
				operation.setPreConfirm(new PreConfirmObject(params));
			}
			else
				operation.getPreConfirm().getPreConfimParamMap().put("confirmUserId", userId);

			operation.doPreFraudControl();

			//устанавливаем поле "карта подтверждения" для отображения на странице подтверждения
			frm.setField("confirmCard", PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(confirmCardNumberId)).getNumber());
		}
	}

	protected ConfirmRequest sendChangeToCardRequest(ConfirmTemplateForm frm, ConfirmTemplateOperation operation) throws BusinessException, BusinessLogicException
	{
		try
		{
			ConfirmationManager.sendRequest(operation);
		}
		catch (InvalidUserIdException ignore)
		{
			//в случае если по запросу вернулась ошибка неверного userId полученого по карте из базы.
			//Актуализируеим значение userId через МБ и отправляем запрос снова.
			addConfirmCardParameter(frm, operation, false);
			ConfirmationManager.sendRequest(operation);
		}
		finally
		{
			frm.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
		}
		return operation.getRequest();
	}

	private CallBackHandler getCallBackHandler(ConfirmStrategyType confirmStrategy, ConfirmTemplateOperation operation) throws BusinessException
	{
		CallBackHandler callBackHandler;
		if (confirmStrategy == ConfirmStrategyType.push)
		 	callBackHandler = new CallBackHandlerPushImpl();
		else
			callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setLogin(PersonHelper.getContextPerson().getLogin());
		callBackHandler.setConfirmableObject(operation.getTemplate());
		callBackHandler.setOperationType(OperationType.CREATE_TEMPLATE_OPERATION);

		if(checkAccess(CheckIMSIOperation.class, "CheckIMSIService"))
		{
			callBackHandler.setAdditionalCheck();
		}
		return callBackHandler;
	}

	protected void addDocumentLimitsInfoMessages(ConfirmTemplateOperation operation) throws BusinessException, BusinessLogicException
	{
		if (!new OverallAmountPerDayTemplateLimitStrategy(operation.getTemplate()).check(null))
		{
			saveErrors(currentRequest(), Collections.singletonList(getResourceMessage("paymentsBundle", "payment.limit.exceeded.view.OVERALL_AMOUNT_PER_DAY.template")));
		}

		if (LimitHelper.needObstructOperation(operation.getTemplate()))
		{
			saveErrors(currentRequest(), Collections.singletonList(getResourceMessage("paymentsBundle", "payment.limit.exceeded.general.confirm.template")));
		}

		// сработало ограничение на вывод информации о получателе при переводе по номеру телефора
		if (BooleanUtils.isTrue(operation.getTemplate().hasRestrictReceiverInfoByPhone()))
		{
			saveMessage(currentRequest(), StrutsUtils.getMessage("com.rssl.phizic.web.client.payments.service.payment.limit.request.receiver.info", "paymentsBundle"));
		}
	}

	protected List<String> getConfirmMessages(PreConfirmObject preConfirmObject)
	{
		List<String> msgs = new ArrayList<String>();
		if (preConfirmObject != null)
		{
			msgs.add(ConfirmHelper.getPreConfirmString(preConfirmObject));
			String clientInfo = (String) preConfirmObject.getPreConfirmParam(SmsPasswordConfirmStrategy.CLIENT_SEND_MESSAGE_KEY);
			if (StringHelper.isNotEmpty(clientInfo))
			{
				msgs.add(clientInfo);
			}
		}
		return msgs;
	}

	protected ActionForward createSuccessConfirmForward(ConfirmTemplateOperation operation) throws BusinessException
	{
		TemplateDocument template = operation.getTemplate();
		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Шаблон \"" + StringEscapeUtils.escapeHtml(template.getTemplateInfo().getName()) + "\" успешно сохранен!", false), null);

		if (template.getState().equals(new State("WAIT_CONFIRM_TEMPLATE")))
		{
			saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(getResourceMessage("paymentsBundle", "template.limit.view"), false), null);
		}
		return DefaultDocumentAction.createDefaultEditForward(template.getId(), operation.getExecutor().getCurrentState());
	}

	protected ActionForward createNextStageDocumentForward(ConfirmTemplateOperation operation) throws BusinessException
	{
		return DefaultDocumentAction.createDefaultEditForward(operation.getTemplate().getId(), operation.getExecutor().getCurrentState());
	}

	protected ActionForward createBadConfirmForward(ConfirmTemplateOperation operation) throws BusinessException, BusinessLogicException
	{
		return null;
	}
}
