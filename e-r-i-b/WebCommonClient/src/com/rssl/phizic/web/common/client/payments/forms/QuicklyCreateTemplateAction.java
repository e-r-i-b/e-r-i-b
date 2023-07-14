package com.rssl.phizic.web.common.client.payments.forms;

import com.rssl.common.forms.*;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.NotConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.strategies.limits.OverallAmountPerDayTemplateLimitStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringConfirmTemplateStrategy;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.NewQuicklyTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.strategies.limits.BlockTemplateOperationLimitStrategy;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.confirm.OperationConfirmLogWriter;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.payment.ConfirmTemplateOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sim.CheckIMSIOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.actions.payments.forms.ConfirmCreateTemplateForm;
import com.rssl.phizic.web.actions.payments.forms.EditTemplateForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.confirm.AutoConfirmRequestType;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн для быстрого создания шаблона по исполненному документу
 * @author niculichev
 * @ created 10.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class QuicklyCreateTemplateAction extends EditTemplateAction
{
	private static final String GET_NAME_TEMPLATE = "GetTemplateName";
	private static final String SHOW_CONFIRM = "ShowConfrim";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.confirm",           "confirm");
		map.put("button.getTemplateName", "getUniqueTemplateName");
		map.put("button.changeToSMS", "changeToSMS");
		map.put("button.changeToPush", "changeToPush");
		map.put("button.preConfirmSMS", "preConfirmSMS");
		map.put("button.preConfirmPUSH", "preConfirmPUSH");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmCreateTemplateForm frm = (ConfirmCreateTemplateForm) form;

		try
		{
			EditTemplateDocumentOperation editOperation = createViewOperation(frm);

			//валидируем поля(имя шаблона)
			Map<String, Object> formData = checkFormData(frm, editOperation);

			//если перевод со счета запрещен - информируем о невозможности создать такой шабон.
			if (TemplateHelper.isTemplateDisallowedFromAccount(editOperation.getTemplate()))
			{
				saveError(request, Constants.EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE);
				return mapping.findForward(SHOW_CONFIRM);
			}

			//сохраняем шаблон как черновик
			saveDraftTemplate(frm, editOperation, formData);

			ConfirmTemplateOperation confirmOperation = createConfirmOperation(editOperation);
			saveOperation(request, confirmOperation);

			//для шаблонов не требующих подвтерждения по СМС сразу сохраняем.
			if (confirmOperation.getConfirmStrategy() instanceof NotConfirmStrategy)
			{
				editOperation.saveQuicklyCreatedTemplate();
				return mapping.findForward(SHOW_CONFIRM);
			}

			new BlockTemplateOperationLimitStrategy(confirmOperation.getTemplate()).checkAndThrow(null);
			new OverallAmountPerDayTemplateLimitStrategy(confirmOperation.getTemplate()).checkAndThrow(null);

			String userOptionType =  AuthenticationContext.getContext().getPolicyProperties().getProperty("userOptionType");
			if (userOptionType==null)
				userOptionType="sms";

			ConfirmStrategyType type = ConfirmStrategyType.valueOf(userOptionType);
			if (type == ConfirmStrategyType.push &&
					ConfirmHelper.strategySupported(confirmOperation.getConfirmStrategy(), ConfirmStrategyType.push))
			{
				frm.setAutoConfirmRequestType(AutoConfirmRequestType.payment);
				return changeToPush(mapping, form, request, response);
			}
			else
			{
				frm.setAutoConfirmRequestType(AutoConfirmRequestType.payment);
				return changeToSMS(mapping, form, request, response);
			}
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return mapping.findForward(SHOW_CONFIRM);
	}

	public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmTemplateOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping,form, operation, ConfirmStrategyType.sms);
	}

	public ActionForward changeToPush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmTemplateOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.push);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, operation, ConfirmStrategyType.push);
	}



	protected Map<String, Object> checkFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		FieldValuesSource valuesSource = getFormProcessorValueSource(frm, operation);
		FormProcessor<ActionMessages,?> processor = createFormProcessor(valuesSource, getAdditionalForm(operation));

		if(!processor.process())
			throw new FormProcessorException(processor.getErrors());

		return processor.getResult();
	}

	protected void saveDraftTemplate(ConfirmCreateTemplateForm frm, EditTemplateDocumentOperation operation, Map<String, Object> formData) throws BusinessLogicException, BusinessException
	{
		operation.changeName((String) formData.get(EditTemplateForm.TEMPLATE_NAME_FIELD_NAME));
	}

	public ActionForward preConfirmSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmTemplateOperation confirmOperation = createConfirmEntityOperation(form);
			saveOperation(request, confirmOperation);
			return changeToSMS(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}

		return mapping.findForward(SHOW_CONFIRM);
	}

	public ActionForward preConfirmPUSH(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmTemplateOperation confirmOperation = createConfirmEntityOperation(form);
			saveOperation(request, confirmOperation);
			return changeToPush(mapping, form, request, response);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
		}
		return mapping.findForward(SHOW_CONFIRM);
	}

	private ConfirmTemplateOperation createConfirmEntityOperation(ActionForm form) throws BusinessException, BusinessLogicException
	{
		ConfirmCreateTemplateForm frm = (ConfirmCreateTemplateForm) form;
		TemplateDocumentSource source = createExistingTemplateSource(frm.getTemplateId());
		ConfirmTemplateOperation operation = createOperation(ConfirmTemplateOperation.class, source.getMetadata().getName());
		operation.initialize(source);
		operation.resetConfirmStrategy();
		return operation;
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmCreateTemplateForm frm = (ConfirmCreateTemplateForm) form;

		TemplateDocumentSource templateSource = createExistingTemplateSource(frm.getTemplateId());
		DocumentSource documentSource = new ExistingSource(frm.getPayment(), new IsOwnDocumentValidator());

		ConfirmTemplateOperation operation = createOperation(ConfirmTemplateOperation.class, templateSource.getMetadata().getName());
		operation.initialize(templateSource, documentSource);

		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		if (!errors.isEmpty())
		{
			updateConfirmForm(frm, operation);
			updateConfirmFormAdditionalInfo(frm, operation);
			operation.getRequest().setErrorMessage(errors.get(0));

			return mapping.findForward(SHOW_CONFIRM);
		}

		try
		{
			operation.validateConfirm();
			updateForm(frm, operation.getTemplate());
		}
		catch (SecurityException e)
		{
			updateConfirmForm(frm, operation);
			updateConfirmFormAdditionalInfo(frm, operation);
			String error = SecurityMessages.translateException(e);
			saveError(request, error);
			operation.getRequest().setErrorMessage(error);

			return mapping.findForward(SHOW_CONFIRM);
		}
		catch (SecurityLogicException e)
		{
			updateConfirmForm(frm, operation);
			updateConfirmFormAdditionalInfo(frm, operation);
			String error = SecurityMessages.translateException(e);
			saveError(request, error);
			operation.getRequest().setErrorMessage(error);

			return mapping.findForward(SHOW_CONFIRM);
		}

		try
		{
			operation.saveQuicklyCreatedTemplate();
		}
		catch (BusinessLogicException e)
		{
			updateConfirmForm(frm, operation);
			saveError(request, e.getMessage());
		}

		return mapping.findForward(SHOW_CONFIRM);
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditTemplateForm frm = (EditTemplateForm) form;
		EditTemplateOperation operation = createEditOperation(frm);

		//валидируем доп поля(имя шаблона)
		ActionMessages msgs = checkAdditionFormData(frm, operation);
		if (!msgs.isEmpty())
		{
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_START);
		}

		String templateName = (String) frm.getField(EditTemplateForm.TEMPLATE_NAME_FIELD_NAME);

		addLogParameters(new BeanLogParemetersReader("Первоначальные данные", operation.getTemplate()));
		addLogParameters(new SimpleLogParametersReader("Имя шаблона", templateName));

		try
		{
			operation.saveQuicklyCreatedTemplate(templateName);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
		}

		frm.setTemplateName(operation.getTemplateInfo().getName());

		return mapping.findForward(FORWARD_START);
	}

	@Override
	protected Form getAdditionalForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		return ConfirmCreateTemplateForm.QUICKLY_CREATE_FORM;
	}

	public ActionForward getUniqueTemplateName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			EditTemplateForm frm = (EditTemplateForm) form;
			EditTemplateOperation operation = (EditTemplateOperation) createViewOperation(frm);

			//если перевод со счета запрещен - информируем о невозможности создать такой шабон.
			if (TemplateHelper.isTemplateDisallowedFromAccount(operation.getEntity()))
			{
				saveError(request, Constants.EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE);
				return mapping.findForward(GET_NAME_TEMPLATE);
			}

			frm.setTemplateName(operation.getTemplateInfo().getName());
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
		}

		return mapping.findForward(GET_NAME_TEMPLATE);
	}

	protected void updateConfirmForm(EditFormBase form, ConfirmableOperation confirmOperation) throws BusinessException
	{
		ConfirmCreateTemplateForm frm = (ConfirmCreateTemplateForm) form;
		TemplateDocument template = (TemplateDocument) confirmOperation.getConfirmableObject();

		frm.setConfirmableObject(template);
		frm.setTemplateName(template.getTemplateInfo().getName());
	}

	protected void updateConfirmFormAdditionalInfo(ConfirmCreateTemplateForm frm, ConfirmableOperation operation) throws BusinessException
	{
		ConfirmTemplateOperation confirmOperation = (ConfirmTemplateOperation) operation;

		frm.setConfirmStrategy(confirmOperation.getConfirmStrategy());
		frm.setConfirmRequest(confirmOperation.getRequest());
		frm.setHtml(confirmOperation.buildFormHtml(getTransformInfo(), getFormInfo()));
	}

	protected ActionForward preConfirm(ActionMapping mapping, ActionForm form, ConfirmTemplateOperation operation, ConfirmStrategyType confirmStrategy) throws Exception
	{
		ConfirmRequest confirmRequest = operation.getRequest();
		ConfirmCreateTemplateForm frm = (ConfirmCreateTemplateForm) form;

		try
		{
			confirmRequest.setPreConfirm(true);

			CallBackHandler callBackHandler = getCallBackHandler(operation, confirmStrategy);
			confirmRequest.addMessage(ConfirmHelper.getPreConfirmString(operation.preConfirm(callBackHandler)));

			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(currentRequest(), errors);
		}
		catch (SecurityLogicException e)
		{
			confirmRequest.setErrorMessage(SecurityMessages.translateException(e));
			confirmRequest.setPreConfirm(true);
		}
		updateForm(frm, operation.getTemplate());
		updateConfirmFormAdditionalInfo(frm, operation);
		updateConfirmForm(frm, operation);

		return mapping.findForward(SHOW_CONFIRM);
	}

	protected ConfirmTemplateOperation createConfirmOperation(EditTemplateDocumentOperation editOperation) throws BusinessException, BusinessLogicException
	{
		ConfirmTemplateOperation operation = createOperation(ConfirmTemplateOperation.class, editOperation.getMetadata().getName());
		operation.initialize(editOperation.getTemplate(), editOperation.getMetadata());
		return operation;
	}

	protected TemplateDocumentSource createExistingTemplateSource(Long id) throws BusinessException, BusinessLogicException
	{
		return new ExistingTemplateSource(id, getTemplateValidators());
	}

	protected TemplateDocumentSource createExistingPaymentTemplateSource(Long id) throws BusinessLogicException, BusinessException
	{
		return new NewQuicklyTemplateSource(id, getCreationType(), getDocumentValidators());
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("view", "html");
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return new MapValuesSource(frm.getFields());
	}

	private CallBackHandler getCallBackHandler(ConfirmableOperation operation, ConfirmStrategyType confirmStrategy) throws BusinessException
	{
		CallBackHandler callBackHandler;
		if (confirmStrategy == ConfirmStrategyType.push)
			callBackHandler = new CallBackHandlerPushImpl();
		else
			callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setLogin(PersonHelper.getContextPerson().getLogin());
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setOperationType(OperationType.CREATE_TEMPLATE_OPERATION);

		if(checkAccess(CheckIMSIOperation.class, "CheckIMSIService"))
		{
			callBackHandler.setAdditionalCheck();
		}
		return callBackHandler;
	}
}
