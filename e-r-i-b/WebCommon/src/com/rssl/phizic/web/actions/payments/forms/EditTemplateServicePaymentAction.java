package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.strategy.TemplateValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.documents.payments.validators.CheckPermissionTemplateCreationValidator;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.validators.ERIBTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.ProviderFieldValuesUtils;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.documents.templates.EditServicePaymentTemplateOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateDocumentOperation;
import com.rssl.phizic.operations.documents.templates.EditTemplateOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн создания шаблона для оплаты услуг
 * @author niculichev
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTemplateServicePaymentAction extends EditTemplateActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.saveastemplate", "next");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditServicePaymentTemplateForm frm = (EditServicePaymentTemplateForm) form;
		frm.setOperationUID(OperationContext.getCurrentOperUID());

		try
		{
			EditServicePaymentTemplateOperation operation = (EditServicePaymentTemplateOperation) createViewOperation(frm);
			//заносим на форму информацию о полях и поставщиках(биллинговых услугах)
			updateProviderFormAdditionalData(frm, operation);
			//теперь надо получить из документа инициализируюющие значения полей в случае редактирования.
			frm.setFields(operation.getTemplateFieldValues());
		}
		catch (BusinessLogicException ex)
		{
			saveError(request, ex);
		}
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditServicePaymentTemplateForm frm = (EditServicePaymentTemplateForm) form;
		EditServicePaymentTemplateOperation operation = (EditServicePaymentTemplateOperation) createEditOperation(frm);

		Map<String, Object> formData = checkFormData(getFieldValuesSource(frm, operation), operation.getMetadata().getForm(), TemplateValidationStrategy.getInstance());
		if (formData != null)
		{
			try
			{
				//обновляем шаблон
				operation.update(formData);
				//сохраняем шаблон
				operation.saveAsDraft();
				//сохраняем операцию, т.к. ввод имени шаблона осуществляется на след шаге
				StoreManager.getCurrentStore().save(Constants.TEMPLATE_DATA_STORE_KEY, operation);

				UrlBuilder urlBuilder = new UrlBuilder();
				urlBuilder.setUrl(getCurrentMapping().findForward(FORWARD_EDIT_TEMPLATE).getPath());

				updateForwardByFinanceCalendar(urlBuilder, frm);

				return new ActionForward(urlBuilder.getUrl(), true);
			}
			catch (BusinessLogicException e)
			{
				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
				saveErrors(request, actionErrors);
			}
		}

		updateProviderFormAdditionalData(frm, operation);

		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	@Override
	protected TemplateValidator[] getTemplateValidators()
	{
		return new TemplateValidator[]{new OwnerTemplateValidator(), new ERIBTemplateValidator()};
	}

	@Override
	protected DocumentValidator[] getDocumentValidators()
	{
		return new DocumentValidator[]{new IsOwnDocumentValidator(), new CheckPermissionTemplateCreationValidator()};
	}

	@Override
	protected Form getCheckForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected Form getAdditionalForm(EditEntityOperation operation) throws BusinessLogicException, BusinessException
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected EditTemplateDocumentOperation createViewOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentTemplateForm frm = (EditServicePaymentTemplateForm) form;
		EditTemplateDocumentOperation operation = super.createViewOperation(form);

		operation.initialize(frm.getRecipient());
		return operation;
	}

	@Override
	protected String getFormName(EditTemplateForm form)
	{
		return FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER.getName();
	}

	@Override
	protected EditTemplateDocumentOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createViewOperation(frm);
	}

	protected void updateProviderFormAdditionalData(EditFormBase form, EditTemplateDocumentOperation operation) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentTemplateForm frm = (EditServicePaymentTemplateForm) form;
		EditServicePaymentTemplateOperation templateOperation = (EditServicePaymentTemplateOperation) operation;

		PaymentAbilityERL chargeOffResourceLink = templateOperation.getChargeOffResourceLink();
		if (chargeOffResourceLink != null)
		{
			frm.setFromResource(chargeOffResourceLink.getCode());
		}
		frm.setRecipient(templateOperation.getProvider().getId());
		frm.setProviders(templateOperation.getProviderAllServices());
		frm.setChargeOffResources(templateOperation.getProviderChargeOffResources());
		frm.setExternalPayment(false);

		frm.setTemplate(templateOperation.getEntity());

		frm.setMetadata(templateOperation.getMetadata());
		frm.setFieldsDescription(templateOperation.getProviderAllServicesFields());

		FieldValuesSource fieldValuesSource = new MapValuesSource(frm.getFields());
		if (MaskPaymentFieldUtils.isRequireMasking())
		{
			fieldValuesSource =	MaskPaymentFieldUtils.wrapMaskValuesSource(templateOperation.getMetadata(), fieldValuesSource);
		}

		Map<String, String> fieldsMap = fieldValuesSource.getAllValues();
		for(String key : fieldsMap.keySet())
		{
			frm.setField(key, fieldsMap.get(key));
			frm.setMaskedField(key, fieldValuesSource.isMasked(key));
		}

		updateMessages(templateOperation);
	}

	protected void updateMessages(EditTemplateOperation operation) throws BusinessException, BusinessLogicException
	{
		super.saveErrors(operation.getEntity());

		ActionMessages msgs = new ActionMessages();
		for (String error : operation.getMessageCollector().getInactiveErrors())
		{
			if (!StringHelper.isEmpty(error))
			{
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
			}
		}
		if (!msgs.isEmpty())
		{
			saveInactiveESMessage(currentRequest(), msgs);
		}
	}

	public FieldValuesSource getFieldValuesSource(EditTemplateForm form, EditTemplateDocumentOperation operation) throws BusinessException
	{
		EditServicePaymentTemplateForm frm = (EditServicePaymentTemplateForm) form;
		EditServicePaymentTemplateOperation editOperation = (EditServicePaymentTemplateOperation) operation;

		FieldValuesSource resultValueSource = getFormDataValueSource(frm, editOperation);
		return MaskPaymentFieldUtils.isRequireMasking() ?
				MaskPaymentFieldUtils.wrapUnmaskValuesSource(editOperation.getMetadata(), resultValueSource, getUnmaskFieldValuesSource(frm, editOperation)) : resultValueSource;

	}

	private FieldValuesSource getFormDataValueSource(EditServicePaymentTemplateForm form, EditServicePaymentTemplateOperation operation)
	{
		Map<String, Object> source = new HashMap<String, Object>();
		source.put(PaymentFieldKeys.PROVIDER_KEY, StringHelper.getEmptyIfNull(form.getRecipient()));
		source.put(PaymentFieldKeys.FROM_RESOURCE_KEY, StringHelper.getEmptyIfNull(form.getFromResource()));
		source.put(PaymentFieldKeys.OPERATION_CODE, StringHelper.getEmptyIfNull(operation.getEntity().getOperationCode()));
		source.putAll(form.getFields());
		return new MapValuesSource(source);
	}

	private FieldValuesSource getUnmaskFieldValuesSource(EditServicePaymentTemplateForm form, EditServicePaymentTemplateOperation operation) throws BusinessException
	{
		String phoneField = form.getPhoneFieldParam();
		BillingServiceProviderBase provider = operation.getProvider();
		Map<String, String> result = operation.getFieldValuesSource().getAllValues();

		if (operation.getEntity().getId() == null && provider != null && CollectionUtils.isNotEmpty(provider.getFieldDescriptions()))
		{
			for(FieldDescription field : provider.getFieldDescriptions())
			{
				// если задано поле для указания номера телефона
				if (StringHelper.isNotEmpty(phoneField) && phoneField.equals(String.format(FilterActionForm.FIELD_FORMAT, field.getExternalId())))
				{
					result.put(field.getExternalId(), QuickPaymentPanelUtil.getPhoneNumber());
				}
				else
				{
					String defaultProviderFieldValue = ProviderFieldValuesUtils.getDefaultValue((String)provider.getSynchKey(), provider.getCode(), field.getExternalId());
					if(defaultProviderFieldValue != null)
						result.put(field.getExternalId(), defaultProviderFieldValue);
				}
			}
		}

		return new MapValuesSource(result);
	}
}
