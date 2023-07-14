package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.*;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.FieldValueValuesSource;
import com.rssl.common.forms.processing.*;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.types.SubType;
import com.rssl.common.forms.validators.strategy.ByTemplateValidationStrategy;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.common.forms.validators.strategy.PrepareDocumentValidatorStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessLogicWithBusinessDocumentException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.AutoPaymentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.source.*;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanelUtil;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.ProviderFieldValuesUtils;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.DefineTypeAutoPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.FilterActionForm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 14.01.2011
 * @ $Author$
 * @ $Revision$
 * ����� ��������������/�������� ������� ������ �����(1 ���).
 * �� ������ ���� ������ �������� ���������� � ������ ����, ������� � ��.
 * ������ ����� ����� ���������� � 4 �������:
 * 1) �������� ������ �������.
 * 2) �������������� �������������(����� �����������)
 * 3) ����� �������
 * 4) ������ �� �������
 * �� ���� ������� ������ ���� �������� ���������
 */
public class EditServicePaymentAction extends EditExternalPaymentAction
{

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		EditServicePaymentForm frm = (EditServicePaymentForm) form;
		frm.setOperationUID(OperationContext.getCurrentOperUID());
		try
		{
			EditServicePaymentOperation editServicePaymentOperation = createEditOperation(request, frm, messageCollector);
			//������� �� ����� ���������� � ����� � �����������(����������� �������)
			updateFormAdditionalData(frm, editServicePaymentOperation);
			//������ ���� �������� �� ��������� ����������������� �������� ����� � ������ ��������������.
			Map documentFieldValues = editServicePaymentOperation.getDocumentFieldValues();

			if(editServicePaymentOperation.getDocument() instanceof CreateAutoPayment)
				initKeyFieldValue(editServicePaymentOperation, documentFieldValues);

			updateFormFields(frm, editServicePaymentOperation, documentFieldValues);
		}
		catch (BusinessLogicException ex)
		{
			saveError(request, ex);
		}
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		EditServicePaymentForm frm = (EditServicePaymentForm) form;
		OperationContext.setCurrentOperUID(frm.getOperationUID());
		//���� ����� next -> ������ ����������(������), ����/����� �������� � �������� ��� ����.
		EditServicePaymentOperation editServicePaymentOperation = createEditOperation(request, frm, messageCollector);
		frm.setNeedSelectProvider(true);
		try
		{
			try
			{
				//��������� �����, ���� � ������ ������ �� �������
				//������ ���������� ����� (��������� ����������(������), ������ ��������, ����)
				FormProcessor<ActionMessages, ?> processor = createForm(frm, editServicePaymentOperation);
				if (processor.process())
				{
					//��� ������ -> ��������� �������� � ���� ������
					//1) ��������� �������� �������
					Map<String, Object> result = processor.getResult();
					ServiceProviderShort regionProvider = RegionHelper.getBarCodeProvider();
					if (regionProvider != null && editServicePaymentOperation.getProvider().getSynchKey().equals(regionProvider.getSynchKey()))
					{
						result.put(ConfigFactory.getConfig(PaymentsConfig.class).getBarcodeField(), frm.getBarCode());
						addBarcodeFields(result);
					}
					//����������� ���������� ������� � ����������� �� �������
					if (StringHelper.isNotEmpty(frm.getBarCode()))
						addBillingFields(result);
					editServicePaymentOperation.updateDocument(result);
					//���������� ���������� �������
					if (editServicePaymentOperation.getDocument() instanceof JurPayment)
						addOperationDescription(editServicePaymentOperation, editServicePaymentOperation.getDocument(), frm);
					//2) ��������� ��������
					editServicePaymentOperation.save();
					//3) ����� ����������� ����� ����� ��������������
					showRiskRecipietMessage(editServicePaymentOperation);
					//3)������ ������� �� ��������� �����.
					return createNextStageDocumentForward(editServicePaymentOperation);
				}
				else
				{
					saveErrors(request, processor.getErrors()); // ��������� ������ ���������
				}
			}
			catch (BusinessLogicWithBusinessDocumentException e)
			{
				ActionForward forward = processDocumentInError(e, request, mapping);
				if (forward != null)
					return forward;
			}
			catch (BusinessLogicException e)
			{
				ActionMessages actionErrors = new ActionMessages();
				actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(), false));
				saveErrors(request, actionErrors);
			}

			updateFormAdditionalData(frm, editServicePaymentOperation);  //��������� �����, ����� �� �������� ������

			return mapping.findForward(FORWARD_SHOW_FORM);//���� �� ���������.
		}
		finally
		{
			saveStateMachineEventMessages(request, editServicePaymentOperation, false);
		}
	}

	protected void updateFormFields(EditServicePaymentForm frm, EditServicePaymentOperation operation, Map<String, String> documentFieldValues) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource fieldValuesSource = new MapValuesSource(documentFieldValues);
		BillingServiceProviderBase provider = operation.getProvider();
		if(MaskPaymentFieldUtils.isRequireMasking((String)provider.getSynchKey(),provider.getCode()))
			fieldValuesSource =	MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(operation, fieldValuesSource), fieldValuesSource);

		Map<String, String> fieldsMap = fieldValuesSource.getAllValues();
		for(String key : fieldsMap.keySet())
		{
			frm.setField(key, fieldsMap.get(key));
			frm.setMaskedField(key, fieldValuesSource.isMasked(key));
		}
	}

	/**
	 * ����������� ���������� �����
	 * �����!!! ���������� ������ ��� ���������� ����� ������ ���� ������ ������ ��������:
	 * ��: BUG024357,BUG022458,BUG022777 � �����������
	 * �� ������ ������ ��������� ������ �� �������.
	 * ��� ��������� �������� ���������� ������������ (�������) ���������� ����
	 * ��� ���������������� -> ��������������� �������� ����� ��� ���������������� �������� ����� ����� ��� ������ �����.
	 * @param frm �����.
	 * @param editServicePaymentOperation �������� ��� ��������� ������.
	 * @return ���� ���������.
	 */
	private FormProcessor<ActionMessages, ?> createForm(EditServicePaymentForm frm, EditServicePaymentOperation editServicePaymentOperation) throws BusinessException, BusinessLogicException
	{
		return createFormProcessor(getValidateFormFieldValuesSource(frm, editServicePaymentOperation), editServicePaymentOperation.getMetadata().getForm(), getValidationStrategy(editServicePaymentOperation));
	}

	private ValidationStrategy getValidationStrategy(EditServicePaymentOperation editServicePaymentOperation) throws BusinessException
	{
		if (editServicePaymentOperation.isExternal())
		{
			return DocumentValidationStrategy.getInstance();
		}
		if (editServicePaymentOperation.getDocument().isByTemplate())
		{
			return ByTemplateValidationStrategy.getInstance();
		}
		return PrepareDocumentValidatorStrategy.getInstance();
	}

	/**
	 * ���������� �������� �������� ����� ��� ��������� �����
	 */
	protected FieldValuesSource getValidateFormFieldValuesSource(CreatePaymentForm form, EditDocumentOperation operation) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentForm frm = (EditServicePaymentForm) form;
		EditServicePaymentOperation op = (EditServicePaymentOperation) operation;

		Map<String, Object> data = new HashMap<String, Object>();
		//����� �������� ����������(������)
		data.put(PaymentFieldKeys.PROVIDER_KEY, StringHelper.getEmptyIfNull(frm.getRecipient()));
		//����� �������� ��������� ��������.
		data.put(PaymentFieldKeys.FROM_RESOURCE_KEY, StringHelper.getEmptyIfNull(frm.getFromResource()));
		//����� �������� ���� �������� �������.
		data.put(PaymentFieldKeys.OPERATION_CODE, StringHelper.getEmptyIfNull(((JurPayment) operation.getDocument()).getOperationCode()));
		//��� iTunes � ��� ������� ��������� ��� ���� "Agreement" ��� ������ �� �������
		BillingServiceProviderBase provider = op.getProvider();
		if (ApplicationUtil.isATMApi() && ProviderFieldValuesUtils.isITunesProvider((String) provider.getSynchKey(), provider.getCode()))
			data.put(JurPayment.AGREEMENT_FIELD, true);
		//����� �������� �������������� ����� �����.
		data.putAll(frm.getFields());

		FieldValuesSource resultValueSource = new MapValuesSource(data);
		if (operation.getDocument().isByTemplate())
		{
			resultValueSource = new CompositeFieldValuesSource(resultValueSource, new FieldValueValuesSource(operation.getMetadata().getForm(), Collections.<String, String>emptyMap()));
		}

		if(MaskPaymentFieldUtils.isRequireMasking())
		{
			FieldValuesSource unmaskedValuesSource = getUnmaskFieldValuesSource(operation, frm, resultValueSource);
			resultValueSource = MaskPaymentFieldUtils.wrapUnmaskValuesSource(operation.getMetadata(), resultValueSource, unmaskedValuesSource);
		}

		return  resultValueSource ;
	}

	/**
	 * ValuesSource ��� ��������������� ��������
	 * @param operation ��������
	 * @param form �����
	 * @param valuesSource ������� ValuesSource
	 * @return �������� ������ ��� ���������������
	 * @throws BusinessException
	 */
	protected FieldValuesSource getUnmaskFieldValuesSource(EditDocumentOperation operation, CreatePaymentForm form, FieldValuesSource valuesSource) throws BusinessException
	{
		FieldValuesSource documentFieldValuesSource = ((EditDocumentOperationBase) operation).getDocumentFieldValuesSource();
		// ��� �������� iqw ����������� �������� ������
		if(!(operation instanceof EditServicePaymentOperation))
			return documentFieldValuesSource;

		EditServicePaymentForm frm = (EditServicePaymentForm) form;
		EditServicePaymentOperation op = (EditServicePaymentOperation) operation;

		Map<String, String> result = documentFieldValuesSource.getAllValues();
		BillingServiceProviderBase provider = op.getProvider();
		BusinessDocument document = operation.getDocument();
		// ��������� �� ������� ������ �� ���������(��� ��������������, ����� ��� �� �������)
		boolean isOnlyDocumentFillData = document.getId() != null || document.isCopy() || document.isByTemplate();
		String phoneField = frm.getPhoneFieldParam();

		if(!isOnlyDocumentFillData && provider != null && CollectionUtils.isNotEmpty(provider.getFieldDescriptions()))
		{
			for(FieldDescription field : provider.getFieldDescriptions())
			{
				// ���� ������ ���� ��� �������� ������ ��������
				if(StringHelper.isNotEmpty(phoneField) && phoneField.equals(String.format(FilterActionForm.FIELD_FORMAT, field.getExternalId())))
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


	protected void updateFormAdditionalData(EditServicePaymentForm frm, EditServicePaymentOperation operation) throws BusinessException, BusinessLogicException
	{
		PaymentAbilityERL chargeOffResourceLink = operation.getChargeOffResourceLink();
		if (chargeOffResourceLink != null)
		{
			frm.setFromResource(chargeOffResourceLink.getCode());
		}
		frm.setRecipient(operation.getProvider().getId());
		frm.setProviders(operation.getProviderAllServices());
		frm.setChargeOffResources(operation.getChargeOffResources());
		frm.setExternalPayment(operation.isExternal());

		BusinessDocument document = operation.getDocument();
		frm.setDocument(document);

		Metadata metadata = operation.getMetadata();
		frm.setMetadata(metadata);

		List<FieldDescription> fieldDescriptions = operation.getProviderAllServicesFields();
		frm.setFieldsDescription(fieldDescriptions);

		if (document.isByTemplate() && CollectionUtils.isNotEmpty(fieldDescriptions))
		{
			FieldValuesSource requestValueSource = getRequestFieldValuesSource();
			FieldValuesSource operationValueSource = operation.getFieldValuesSource();

			List<Field> fields = operation.getMetadata().getForm().getFields();
			for (FieldDescription fieldDescription : fieldDescriptions)
			{
				Field field = FormHelper.getFieldById(fields, fieldDescription.getExternalId());
				if (field == null)
				{
					continue;
				}

				String fieldValue = SubType.STATIC == field.getSubType() ? operationValueSource.getValue(fieldDescription.getExternalId()) : requestValueSource.getValue(fieldDescription.getExternalId());
				frm.setField(fieldDescription.getExternalId(), fieldValue);
			}
		}

		frm.setOrder(operation.getOrder());

		frm.setSocialNetProviderId(operation.getSocialNetProviderId());
		frm.setSocialNetUserId(operation.getSocialNetUserIdentifier());
		frm.setSocialNetPaymentFieldName(operation.getSocialNetPaymentFieldName());

		updateMessages(operation);
	}

	protected void updateMessages(EditServicePaymentOperation editServicePaymentOperation) throws BusinessException, BusinessLogicException
	{
		ActionMessages messages = new ActionMessages();
		for (String error : editServicePaymentOperation.getMessageCollector().getInactiveErrors())
		{
			if (!StringHelper.isEmpty(error))
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
		}
		if (!messages.isEmpty())
			saveInactiveESMessage(currentRequest(), messages);

		super.saveErrors(editServicePaymentOperation.getDocument());
	}

	protected EditServicePaymentOperation createEditOperation(HttpServletRequest request, CreatePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentForm frm = (EditServicePaymentForm) form;
		Long paymentId = form.getId();
		Long templateId = form.getTemplate();
		boolean copy = form.getCopying() != null && form.getCopying();

		try
		{
			FieldValuesSource fieldValuesSource = new MapValuesSource(prepareFieldInputValue(frm));
			DocumentSource source = null;
			if (paymentId != null && paymentId > 0)
			{
				source = new InitalServicePaymentDocumentSource(
						copy ?
								createCopyDocumentSource(paymentId) :
								createExistingDocumentSource(paymentId),
						fieldValuesSource);
			}
			else if (isCreateLongOfferFromServiceProvidersList(frm))
			{
				Long originalPaymentId = frm.getOriginalPaymentId();
				if (originalPaymentId != null && originalPaymentId > 0)
					source = createNewDocumentSource(getFormName(frm), getDocumentFieldValuesSource(originalPaymentId, fieldValuesSource), messageCollector);
				else
					source = createNewDocumentSource(getFormName(frm), fieldValuesSource, messageCollector);
			}
			else if (frm.isPersonal())
			{
				source = new PersonalProviderNewDocumentSource(getFormName(frm), fieldValuesSource, getNewDocumentCreationType(), CreationSourceType.ordinary);
			}
			else if (templateId != null && templateId > 0)
			{
				source = createCopyTemplateSource(templateId, form.isMarkReminder());
			}
			else
			{
				source = createNewDocumentSource(getFormName(frm), fieldValuesSource, messageCollector);
			}
			return makeEditOperation(source, frm);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param form �����
	 * @return ������� �� ���������� � ����� ������ ����������� ����� ����� �������� �� �������� ��� �����������.
	 */
	private boolean isCreateLongOfferFromServiceProvidersList(EditDocumentForm form)
	{
		return form instanceof CreateAutoSubscriptionPaymentForm && ((CreateAutoSubscriptionPaymentForm) form).isCreateLongOffer();
	}

	public ActionForward makeLongOffer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditServicePaymentForm frm = (EditServicePaymentForm) form;
		MessageCollector messageCollector = new MessageCollector();
		try
		{
			EditDocumentOperation operation = createAutoPaymentOperation(frm, messageCollector);

			FieldValuesSource valueSource = new CompositeFieldValuesSource(getRequestFieldValuesSource(), new MapValuesSource(frm.getFields()));
			if(MaskPaymentFieldUtils.isRequireMasking())
			{
				FieldValuesSource unmaskedValuesSource = getUnmaskFieldValuesSource(operation, frm, valueSource);
				valueSource = MaskPaymentFieldUtils.wrapUnmaskValuesSource(getMaskingInfo(operation, valueSource), valueSource, unmaskedValuesSource);
			}

			boolean doLongOffer = doLongOffer(operation, valueSource);

			if (!doLongOffer)
			{
				updateFormAdditionalData(frm, createEditOperation(request, frm, messageCollector));
				return mapping.findForward(FORWARD_SHOW_FORM);
			}

			saveAutoPaymentMessages(request, operation);
			return createNextStageDocumentForward(operation);

		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(request, e);

			updateFormAdditionalData(frm, createEditOperation(request, frm, messageCollector));
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);

			updateFormAdditionalData(frm, createEditOperation(request, frm, messageCollector));
			return mapping.findForward(FORWARD_SHOW_FORM);
		}
	}

	protected void saveAutoPaymentMessages(HttpServletRequest request, EditDocumentOperation operation){}

	private FieldValuesSource getDocumentFieldValuesSource(Long originalPaymentId, FieldValuesSource initialFieldValuesSource) throws BusinessLogicException, BusinessException
	{
		if (originalPaymentId == null || originalPaymentId <= 0L)
			return new NullFieldValuesSource();

		DocumentSource existingSource = new ExistingSource(originalPaymentId, new IsOwnDocumentValidator());
		BusinessDocument originalDocument = existingSource.getDocument();
		Map<String, String> documentValuesSource = new DocumentFieldValuesSource(existingSource.getMetadata(), originalDocument).getAllValues();
		documentValuesSource.putAll(initialFieldValuesSource.getAllValues());
		return new MapValuesSource(documentValuesSource);
	}

	protected EditDocumentOperation createAutoPaymentOperation(EditServicePaymentForm form, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource initalFieldValuesSource = new MapValuesSource(prepareFieldInputValue(form));
		boolean copy = form.getCopying() != null && form.getCopying();
		DocumentSource source = null;
		Long paymentId = form.getId();
		boolean isAutoSubscriptionPay = isAutoSubscriptionPay(form, null);

		Long originalPaymentId = form.getOriginalPaymentId();
		if(paymentId != null && paymentId > 0L)
		{
			source =  copy ? createCopyDocumentSource(paymentId) : createExistingDocumentSource(paymentId);
			if (isAutoSubscriptionPay)
			{
				source = new InitalServicePaymentDocumentSource(source, initalFieldValuesSource);
			}
		}
		else if (originalPaymentId != null && originalPaymentId > 0L)
		{
			FieldValuesSource documentFieldValuesSource = getDocumentFieldValuesSource(originalPaymentId, initalFieldValuesSource);
			source = new NewDocumentSource(FormConstants.CREATE_AUTOPAYMENT_FORM, documentFieldValuesSource, CreationType.internet, CreationSourceType.ordinary, messageCollector);
		}
		else
		{
			FieldValuesSource valuesSource = new MapValuesSource(prepareFieldInputValue(form));
			source = isAutoSubscriptionPay ? createNewDocumentSource(FormConstants.SERVICE_PAYMENT_FORM, valuesSource, messageCollector)
					: createNewDocumentSource(FormConstants.CREATE_AUTOPAYMENT_FORM, valuesSource, messageCollector);
		}

		// ���� ������ ����������, �� ������� �������� ��� ������� �����������, ����� ��� iqw
		if(isAutoSubscriptionPay)
		{
			CreateESBAutoPayOperation operation = createOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
			operation.initialize(source, form.getRecipient(), ObjectEvent.CLIENT_EVENT_TYPE);

			return operation;
		}
		else
		{
			EditDocumentOperation operation = createOperation("CreateFormPaymentOperation", "CreateAutoPaymentPayment");
			operation.initialize(source, initalFieldValuesSource);

			return operation;
		}
	}

	protected boolean isAutoSubscriptionPay(EditServicePaymentForm form, JurPayment doc) throws BusinessException, BusinessLogicException
	{
		DefineTypeAutoPaymentOperation operation = createOperation(DefineTypeAutoPaymentOperation.class);
		if (doc != null && doc.getReceiverInternalId() != null)
			operation.initialize(doc.getReceiverInternalId());
		else
			operation.initialize(form.getRecipient());
		return operation.isESBAutoPayProvider();
	}

	protected String getServiceName(EditServicePaymentForm frm)
	{
		return "RurPayJurSB";
	}

	protected Map<String, String> prepareFieldInputValue(EditServicePaymentForm frm) throws BusinessException
	{
		Map<String, String> params = new HashMap<String, String>();
		params.putAll((Map) frm.getFields());
		if (!StringHelper.isEmpty(frm.getBarCode()))
		{
			ServiceProviderShort provider = RegionHelper.getBarCodeProvider();
			if (provider != null)
				frm.setRecipient(provider.getId());
		}
		params.put(PaymentFieldKeys.PROVIDER_KEY, StringHelper.getEmptyIfNull(frm.getRecipient()));
		return params;
	}

	protected String getFormName(EditPaymentForm frm)
	{
		return FormConstants.SERVICE_PAYMENT_FORM;
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MessageCollector messageCollector = new MessageCollector();
		EditServicePaymentForm frm = (EditServicePaymentForm) form;
		EditServicePaymentOperation operation = createEditOperation(request, frm, messageCollector);
		RemoveDocumentOperation removeOperation = createOperation(RemoveDocumentOperation.class, operation.getMetadata().getName());
		removeOperation.initialize(operation.getDocument());
		removeOperation.remove();
		return mapping.findForward(FORWARD_BACK);
	}

	/**
	 * ��������� � ��� ����� ��������� �������� ���� � ��������� �� �������� ���������� � ��������� requisite �� �������.
	 * ���������� ��� ����������� �������� ��������� ���� ��� iqWave �� ��� �������� �� ������ ��� �������� �����������.
	 * @param editServicePaymentOperation
	 * @param documentFieldValues
	 */
	protected void initKeyFieldValue(EditServicePaymentOperation editServicePaymentOperation, Map documentFieldValues)
	{
		for(FieldDescription field: editServicePaymentOperation.getProvider().getFieldDescriptions())
		{
			if(field.isKey())
			{
				documentFieldValues.put(field.getExternalId(), documentFieldValues.get(AutoPaymentBase.AUTO_PAYMENT_REQUISITE_ATTRIBUTE_NAME));
				break;
			}
		}
	}

	/**
	 * ���������� �������������������� �������� � ����������� ��
	 * ���� ��������� � ��������(�������� �������/����������� ����� ��� �������� �����������)
	 * @param source ������������
	 * @param frm �����
	 * @return ���������������� EditServicePaymentOperation
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected EditServicePaymentOperation makeEditOperation(DocumentSource source, EditServicePaymentForm frm) throws BusinessException, BusinessLogicException
	{
		EditServicePaymentOperation operation;
		if(source.getDocument() instanceof CreateAutoPayment)
		{
			operation = createOperation("EditServicePaymentOperation", getServiceName(frm));
			operation.initialize(source, frm.getRecipient());
			return operation;
		}
		JurPayment doc = (JurPayment) source.getDocument();
		if ((doc.isLongOffer() || isCreateLongOfferFromServiceProvidersList(frm)) && isAutoSubscriptionPay(frm, doc))
		{
			operation = createOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
			((CreateESBAutoPayOperation) operation).initialize(source, frm.getRecipient(), ObjectEvent.CLIENT_EVENT_TYPE);
		}
		else if (isCreateLongOfferFromServiceProvidersList(frm))
		{
			operation = createOperation("CreateIQWaveAutoPayOperation", "CreateAutoPaymentPayment");
			operation.initialize(source, frm.getRecipient());
		}
		else
		{
			String serviceKey = getServiceName(frm);   // ��� �������������� �������� null, ����� �������� �������� �������� ����� �� �����
			operation = createOperation("EditServicePaymentOperation", StringHelper.isEmpty(serviceKey) ? source.getDocument().getFormName() : serviceKey);
			operation.initialize(source, frm.getRecipient());
		}
		return operation;
	}

	protected void addBarcodeFields(Map<String, Object> data) throws BusinessException {}

	/**
	 * ����������� ���������� ������� �� �������
	 * @param data
	 * @throws BusinessException
	 */

	protected void addBillingFields(Map<String, Object> data) throws BusinessException {}

	/**
	 * ���������� ���������� �������
	 *
	 * @param operation - ��������
	 * @param document - ������
	 * @param frm - ����� �������
	 * @throws BusinessException
	 */
	protected void addOperationDescription(EditServicePaymentOperation operation, BusinessDocument document, EditServicePaymentForm frm) throws BusinessException, BusinessLogicException
	{}
}
