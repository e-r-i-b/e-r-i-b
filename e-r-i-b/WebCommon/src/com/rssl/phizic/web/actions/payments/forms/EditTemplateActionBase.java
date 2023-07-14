package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.CompositeFieldValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.impl.ReminderInfoImpl;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.NewTemplateSource;
import com.rssl.phizic.business.documents.templates.source.SimpleExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.reminders.ReminderHelper;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditJurPaymentTemplateOperation;
import com.rssl.phizic.operations.documents.templates.EditServicePaymentTemplateOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.ActionMessageHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.web.struts.forms.ActionMessagesCollector;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Базовый класс редактирования шаблона документа
 *
 * @author khudyakov
 * @ created 18.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class EditTemplateActionBase extends EditActionBase
{
	public static final String FORWARD_EDIT_TEMPLATE        = "EditTemplate";
	public static final String FORWARD_BACK                 = "Close";


	protected abstract TemplateValidator[] getTemplateValidators();

	protected abstract DocumentValidator[] getDocumentValidators();

	protected abstract Form getCheckForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException;

	protected abstract Form getAdditionalForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException;

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.prev",          "prev");
		map.put("button.saveAsDraft",   "saveDraft");
		map.put("button.remove",        "remove");
		return map;
	}

	@Override
	protected EditTemplateDocumentOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditTemplateForm frm = (EditTemplateForm) form;

		try
		{
			if (frm.isTemplateDataStored())
			{
				EditTemplateDocumentOperation savedOperation = (EditTemplateDocumentOperation) StoreManager.getCurrentStore().restore(Constants.TEMPLATE_DATA_STORE_KEY);
				if (savedOperation != null)
				{
					StoreManager.getCurrentStore().remove(Constants.TEMPLATE_DATA_STORE_KEY);
					return savedOperation;
				}
			}

			TemplateDocumentSource source = getTemplateSource(frm);
			EditTemplateDocumentOperation operation = createOperation(getEditOperationClass(source.getMetadata().getName()), source.getMetadata().getName());
			operation.initialize(source);

			return operation;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (FormException e)
		{
			if (e.getCause() instanceof GateLogicException)
			{
				throw new BusinessLogicException(e.getCause().getMessage(), e);
			}
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	protected Class<? extends EditTemplateDocumentOperation> getEditOperationClass(String serviceName)
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

	protected TemplateDocumentSource getTemplateSource(EditFormBase form) throws BusinessLogicException, BusinessException
	{
		EditTemplateForm frm = (EditTemplateForm) form;

		if (frm.isTemplateDataStored())
		{
			return createSimpleExistingSource(frm);
		}

		Long templateId = frm.getId();
		if (templateId != null && templateId != 0)
		{
			return createExistingTemplateSource(templateId);
		}

		Long paymentId = frm.getPayment();
		if (paymentId != null)
		{
			return createExistingPaymentTemplateSource(paymentId);
		}

		return createNewTemplateSource(frm);
	}

	protected TemplateDocumentSource createSimpleExistingSource(EditTemplateForm frm) throws BusinessLogicException, BusinessException
	{
		return new SimpleExistingTemplateSource(frm.getTemplate(), frm.getMetadata());
	}

	protected TemplateDocumentSource createNewTemplateSource(EditTemplateForm frm) throws BusinessLogicException, BusinessException
	{
		return new NewTemplateSource(getFormName(frm), new RequestValuesSource(currentRequest()), getCreationType());
	}

	protected String getFormName(EditTemplateForm form)
	{
		return form.getForm();
	}

	protected TemplateDocumentSource createExistingTemplateSource(Long id) throws BusinessException, BusinessLogicException
	{
		return new ExistingTemplateSource(id, getTemplateValidators());
	}

	protected TemplateDocumentSource createExistingPaymentTemplateSource(Long id) throws BusinessLogicException, BusinessException
	{
		return new NewTemplateSource(id, getCreationType(), getDocumentValidators());
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
	}

	protected Map<String, Object> checkFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		Map<String, Object> result = super.checkFormData(frm, operation);

		if(PermissionUtil.impliesService("ReminderManagment"))
		{
			FormProcessor<ActionMessages,?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), EditTemplateForm.REMINDER_FORM);
			if(!processor.process())
				throw new FormProcessorException(processor.getErrors());

			result.putAll(processor.getResult());
		}

		return result;
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		EditTemplateDocumentOperation operation = (EditTemplateDocumentOperation) editOperation;
		String templateName = (String) editForm.getField(EditTemplateForm.TEMPLATE_NAME_FIELD_NAME);
		TemplateDocument template = operation.getTemplate();
		template.getTemplateInfo().setName(templateName);
		if(PermissionUtil.impliesService("ReminderManagment"))
			template.setReminderInfo(buildReminderInfo(validationResult));
		operation.update(validationResult);
	}

	@Override
	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		EditTemplateForm frm = (EditTemplateForm) form;
		TemplateDocument template = (TemplateDocument) entity;

		frm.setTemplateName(template.getTemplateInfo().getName());
		frm.setTemplate(template);
		if(PermissionUtil.impliesService("ReminderManagment"))
			updateReminderFields(template.getReminderInfo(), frm);

	}

	@Override
	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws Exception
	{
		EditTemplateForm frm = (EditTemplateForm) form;
		EditTemplateDocumentOperation templateOperation = (EditTemplateDocumentOperation) operation;

		FieldValuesSource source = ActionMessageHelper.getErrors(currentRequest()).isEmpty() ?
				getTemplateFieldValuesSource(operation) : new CompositeFieldValuesSource(getRequestFieldValuesSource(),  new MapValuesSource(frm.getFields()));
		frm.setHtml(buildFormHtml(templateOperation, source));

		Metadata metadata = templateOperation.getMetadata();
		frm.setForm(metadata.getName());
		frm.setMetadata(metadata);
		frm.setMetadataPath(templateOperation.getMetadataPath());

		//некоторые документы могут в составе своих даных содержать информацию об ошибках. показываем пользователю их, если нужно
		saveErrors(currentRequest(), templateOperation.getTemplateErrors(new ActionMessagesCollector()).errors());

		if (ApplicationUtil.isNotMobileApi())
		{
			showDisallowExternalAccountPaymentMessage(templateOperation.getTemplate());
		}
	}

	protected Map<String, Object> checkFormData(FieldValuesSource valuesSource, Form form, ValidationStrategy strategy) throws BusinessException
	{
		FormProcessor<ActionMessages,?> processor = FormHelper.newInstance(valuesSource, form, strategy);
		if (!processor.process())
		{
			saveErrors(currentRequest(), processor.getErrors());
			return null;
		}

		return processor.getResult();
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		EditTemplateDocumentOperation templateOperation = (EditTemplateDocumentOperation) operation;

		return MaskPaymentFieldUtils.isRequireMasking() ?
					MaskPaymentFieldUtils.wrapUnmaskValuesSource(templateOperation.getMetadata(), getShowFormFieldValuesSource(templateOperation), getTemplateFieldValuesSource(operation)) :
					getShowFormFieldValuesSource(operation);
	}

	protected FieldValuesSource getShowFormFieldValuesSource(EditEntityOperation operation) throws BusinessException
	{
		return getRequestFieldValuesSource();
	}

	protected FieldValuesSource getTemplateFieldValuesSource(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		return new TemplateFieldValueSource((TemplateDocument) operation.getEntity());
	}

	protected FieldValuesSource getRequestFieldValuesSource() throws BusinessException
	{
		return new RequestValuesSource(currentRequest());
	}

	protected ActionMessages checkAdditionFormData(EditFormBase frm, EditEntityOperation operation) throws BusinessException
	{
		try
		{
			FieldValuesSource valuesSource = getFormProcessorValueSource(frm, operation);
			FormProcessor<ActionMessages,?> processor = createFormProcessor(valuesSource, getAdditionalForm(operation));

			if (!processor.process())
			{
				return processor.getErrors();
			}

			return new ActionMessages();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return тип канала создания шаблона документа
	 */
	protected CreationType getCreationType()
	{
		return CreationType.internet;
	}

	protected String buildFormHtml(EditTemplateDocumentOperation operation, FieldValuesSource source) throws BusinessException
	{
		return operation.buildFormHtml(getTransformInfo(), getFormInfo(), source);
	}

	protected TransformInfo getTransformInfo()
	{
		return new TransformInfo("edit", getCurrentMapping().getPath().endsWith("print") ? "print" : "html");
	}

	protected FormInfo getFormInfo()
	{
		String webRoot = WebContext.getCurrentRequest().getContextPath();
		String resourceRoot = currentServletContext().getInitParameter("resourcesRealPath");

		return new FormInfo(webRoot, resourceRoot);
	}

	protected void showDisallowExternalAccountPaymentMessage(TemplateDocument template) throws BusinessLogicException, BusinessException
	{
		if (TemplateHelper.isTemplateDisallowedFromAccount(template))
		{
			saveMessage(currentRequest(), (template.getChargeOffResourceLink() != null) ? Constants.EXTERNAL_ACCOUNT_PAYMENT_TEMPLATE_ERROR_MESSAGE : Constants.EXTERNAL_ACCOUNT_PAYMENT_ERROR_MESSAGE);
		}
	}

	/**
	 * Сохранить возможные ошибки
	 * @param template шаблон
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void saveErrors(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (TemplateHelper.isOldCPFLTemplate(template))
		{
			saveMessage(currentRequest(), Constants.OLD_TEMPLATE_ERROR_MESSAGE);
		}

		if (TemplateHelper.is40TBTemplateFromAccount(template))
		{
			saveError(currentRequest(), Constants.ACCOUNT_TEMPLATE_ERROR_MESSAGE);
		}

		if (ApplicationUtil.isNotMobileApi())
		{
			showDisallowExternalAccountPaymentMessage(template);
		}
	}

	protected ActionForward createBackActionForward(ActionForm form, ActionMapping mapping) throws Exception
	{
		return mapping.findForward(FORWARD_BACK);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		EditTemplateDocumentOperation templateOperation = (EditTemplateDocumentOperation) operation;
		return DefaultDocumentAction.createDefaultEditForward(templateOperation.getTemplate().getId(), templateOperation.getExecutor().getCurrentState());
	}

	protected void updateReminderFields(ReminderInfo reminderInfo, EditTemplateForm frm)
	{
		frm.addFields(ReminderHelper.getDefaultReminderParameters());
		if(reminderInfo != null)
		{
			frm.setField("enableReminder", Boolean.TRUE.toString());
			frm.setField("reminderType", reminderInfo.getType());
			switch (reminderInfo.getType())
			{
				case ONCE:
					frm.setField("onceDate", DateHelper.formatDateToStringWithPoint(reminderInfo.getOnceDate()));
					return;
				case ONCE_IN_MONTH:
					frm.setField("dayOfMonth", reminderInfo.getDayOfMonth());
					return;
				case ONCE_IN_QUARTER:
					frm.setField("dayOfMonth", reminderInfo.getDayOfMonth());
					frm.setField("monthOfQuarter", reminderInfo.getMonthOfQuarter());
					return;
			}
		}
		if (frm.isFromFinanceCalendar() && StringHelper.isNotEmpty(frm.getExtractId()))
		{
			frm.setField("enableReminder", Boolean.TRUE.toString());
			frm.setField("onceDate", frm.getExtractId());
		}
	}

	protected void updateForwardByFinanceCalendar(UrlBuilder urlBuilder, EditTemplateForm form)
	{
		if (form.isFromFinanceCalendar())
		{
			urlBuilder.addParameter("fromFinanceCalendar", "true");
			urlBuilder.addParameter("extractId", form.getExtractId());
		}
	}

	protected ReminderInfo buildReminderInfo(Map<String, Object> formData)
	{
		Boolean enableReminder = (Boolean) formData.get("enableReminder");
		if(enableReminder == null || !enableReminder)
			return null;

		ReminderInfoImpl reminderInfo = new ReminderInfoImpl();
		reminderInfo.setCreatedDate(Calendar.getInstance());
		reminderInfo.setType(ReminderType.valueOf((String) formData.get("reminderType")));
		switch (reminderInfo.getType())
		{
			case ONCE:
				reminderInfo.setOnceDate(DateHelper.toCalendar((Date) formData.get("onceDate")));
				break;
			case ONCE_IN_MONTH:
				reminderInfo.setDayOfMonth((Integer)formData.get("dayOfMonth"));
				break;
			case ONCE_IN_QUARTER:
				reminderInfo.setDayOfMonth((Integer) formData.get("dayOfMonth"));
				reminderInfo.setMonthOfQuarter((Integer) formData.get("monthOfQuarter"));
				break;
		}

		return reminderInfo;
	}
}