package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizgate.common.payments.ExtendedFieldsHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.templates.ExtendedCodeImpl;
import com.rssl.phizic.business.documents.templates.ServiceImpl;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedFieldFilter;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * �������� ������� � ���������� ����������� �����
 *
 * @author khudyakov
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemTransferTemplate extends ExternalTransferTemplate
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();


	private Long receiverInternalId;
	private String extendedFieldsAsString;
	private String receiverPointCode;
	private String multiBlockReceiverPointCode;
	private String idFromPaymentSystem;


	public void initialize(BusinessDocument document) throws BusinessException
	{
		if (!(document instanceof JurPayment))
		{
			throw new BusinessException("������������ ��� ���������");
		}

		try
		{
			JurPayment payment = (JurPayment) document;

			setExtendedFields(payment.getExtendedFields());
			setReceiverOfficeCode(payment.getReceiverOfficeCode());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		return getReceiverInternalId() == null ? FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER : FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER;
	}

	public Class<? extends GateDocument> getType()
	{
		ResourceType resourceType = getChargeOffResourceType();
		if (resourceType == ResourceType.NULL)
		{
			return null;
		}

		if (resourceType == ResourceType.ACCOUNT)
		{
			return AccountPaymentSystemPayment.class;
		}

		if (resourceType == ResourceType.CARD)
		{
			return CardPaymentSystemPayment.class;
		}

		throw new IllegalStateException("�������� ��� ��������� �������� " + resourceType);
	}

	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = super.getFormData();

		appendNullSaveLong(formData, PROVIDER_INTERNAL_ID_ATTRIBUTE_NAME, getReceiverInternalId());
		appendNullSaveString(formData, PROVIDER_EXTERNAL_ID_ATTRIBUTE_NAME, getReceiverPointCode());
		appendNullSaveString(formData, PROVIDER_MULTI_BLOCK_POINT_CODE_ATTRIBUTE_NAME, getMultiBlockReceiverPointCode());

		appendNullSaveString(formData, ID_FORM_PAYMENT_SYSTE_PAYMENT_ATTRIBUTE_NAME, getIdFromPaymentSystem());
		appendNullSaveBoolean(formData, PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME, getNullSaveAttributeBooleanValue(PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME));

		try
		{
			Field mainSum = BillingPaymentHelper.getMainSumField(getExtendedFields());
			if (mainSum != null && getDestinationAmount() != null)
			{
				appendNullSaveString(formData, mainSum.getExternalId(), getDestinationAmount().getDecimal().toPlainString());
				appendNullSaveString(formData, "destination" + CURRENCY_ATTRIBUTE_SUFFIX, getDestinationAmount().getCurrency().getCode());
			}
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		return formData;
	}

	public void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException
	{
		super.setFormData(source);

		fillActualServiceProvider(source);
		setNullSaveAttributeBooleanValue(PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME, BooleanUtils.toBoolean(source.getValue(PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME)));
	}

	protected void storeChargeOffAmount(FieldValuesSource source)
	{

	}

	protected void storeDestinationAmount(FieldValuesSource source) throws BusinessException
	{
		try
		{
			Field mainSum = BillingPaymentHelper.getMainSumField(getExtendedFields());
			if (mainSum != null)
			{
				//TODO ���
				String destinationResourceAmount = ExtendedFieldFilter.filter(mainSum.getExternalId()) ?
						source.getValue(mainSum.getExternalId()) : getNullSaveAttributeStringValue(mainSum.getExternalId());

				if (StringHelper.isNotEmpty(destinationResourceAmount))
				{
					setDestinationAmount(new Money(new BigDecimal(destinationResourceAmount), MoneyUtil.getNationalCurrency()));
				}
				else
				{
					setDestinationAmount(null);
				}
			}

			removeAttribute(getDestinationResourceCurrencyAmountAttributeName());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	protected String getDestinationAmountCurrencyValue() throws BusinessLogicException, BusinessException
	{
		return MoneyUtil.getNationalCurrency().getCode();
	}

	/**
	 * ��������� ����������� �������� ����� � ���� ������
	 * @param extendedFieldsAsString �������� �����
	 */
	public void setExtendedFieldsAsString(String extendedFieldsAsString) throws DocumentException
	{
		this.extendedFieldsAsString = extendedFieldsAsString;
	}

	/**
	 * @return �������� ����������� ����� ����� ������
	 */
	public String getExtendedFieldsAsString()
	{
		return extendedFieldsAsString;
	}

	public List<Field> getExtendedFields() throws DocumentException
	{
		List<Field> fields = ExtendedFieldsHelper.deserialize(extendedFieldsAsString);
		if (CollectionUtils.isEmpty(fields))
		{
			return new ArrayList<Field>();
		}

		for (Field field : fields)
		{
			field.setValue(getExtendedFieldValue(field));
		}
		return fields;
	}

	public void setExtendedFields(List<Field> extendedFields) throws DocumentException
	{
		//�������� ��������� � ������� ����. ��� ����������� ����� ��� ��� ���
		Map<String, Field> prevFields = getFieldsMap();
		//��������� �������� ����� �����.
		setExtendedFieldsAsString(ExtendedFieldsHelper.serialize(extendedFields));
		if (extendedFields == null)
		{
			return;
		}

		boolean someKeyFieldChanged = false;
		//��������� �� ��������� �������� �����.
		for (Field newField : extendedFields)
		{
			String fieldId = newField.getExternalId();
			if (newField.isKey() && getAttribute(fieldId) != null)
			{
				someKeyFieldChanged = getAttribute(fieldId).isChanged();
			}
			if (someKeyFieldChanged)
			{
				break;
			}
		}

		//��������� ������ �������� ��� �����:
		//1) ��� ������ ����� (������� ������ �� �������� �� � ������ ���) ������ ���������� �������� �� field.getValue()
		//2) ��� ������������� ����� ���������� ��������:
		//   a) ���� ���� �������� �������� ����, �� �������� �� field.getvalue() ��� field.getDefaultValue() (��������� ����� field.getValue()).
		//   b) ���� �������� ���� �� ��������, �� ����� ������� ��������.
		//3) ��� ���� ��������� ����� ���������� �������� �� field.getValue() � field.getDefaultValue() (��������� ����� field.getValue()).
		// ���� ��� �������� �����, �������� ��������, ��������� � �������.
		for (Field newField : extendedFields)
		{
			String fieldId  = newField.getExternalId();
			Field prevField = prevFields.get(fieldId);
			prevFields.remove(fieldId);

			String value = null;
			if (prevField != null)
			{
				//��� ������ ����� (������� ������ �� �������� �� � ������ ���) ������ ���������� �������� �� field.getValue()
				value = (String) newField.getValue();
			}
			// ������� �������� ������� ��� ���
			else if (BillingPaymentHelper.isRBCBilling(this) && !newField.isVisible())
			{
				value = (String) getBillingFieldValue(newField);
			}
			//��� ����� ����� ��������� ���������������
			else if (newField.isEditable() && !StringHelper.isEmpty(getTemplateFieldValue(newField)))
			{
				if (someKeyFieldChanged)
					//���� ���������� ���� �� ���� �������� ����, �� �������� ��������� �� ��������.
					value = (String) getBillingFieldValue(newField);
				else
					//����� ������������� ���� � ���������� ��������� �������.
					continue;
			}
			//������� ������� ���� ����� ���������������, ���� ����� ������������� ��� ��������.
			else
			{
				//� ���� ������ ��������� �������� ���������� ���������� �� field.getValue() � field.getDefaultValue() (��������� ����� field.getValue()
				value = (String) getBillingFieldValue(newField);
			}

			//������������ �������� ���� � ��������� ������� �����: ��� �������� � ����������� ���������, �� ������ ��� � ���� DestinationAmount.
			if (newField.isMainSum())
			{
				Currency currency = getNationalCurrency();// �������� ������������ ������
				//����� �������� ������ ��� ������ ����� ��� �����, �� � ��������� ���������
				if (!StringHelper.isEmpty(value) && currency != null)
				{
					setDestinationAmount(new Money(new BigDecimal(value), currency));
				}
				else
				{
					setDestinationAmount(null);
				}
			}
			//������� ���� ������ � ��� �����.
			else
			{
				setExtendedFieldValue(fieldId, value);
			}
		}

		//������� ��� ���. ����, ������� ���� ������� �� �����, �� ���� � ����� ����.
		for (String fieldId: prevFields.keySet())
		{
			removeAttribute(fieldId);
		}
	}

	/**
	 * �������� ��� ��� �����: ������� ������������ -> ����
	 * @return ��� ����� ��� ������ ��� ���������.
	 */
	private Map<String, Field> getFieldsMap() throws DocumentException
	{
		Map<String, Field> result = new HashMap<String, Field>();
		List<Field> prevFields = getExtendedFields();
		if (prevFields != null)
		{
			for (Field field : prevFields)
			{
				result.put(field.getExternalId(), field);
			}
		}
		return result;
	}

	/**
	 * �������� �������� ���� �� ���������
	 * @param field ����, ��� �������� ����� �������� ��������. �� ����� ���� null
	 * @return �������
	 */
	private String getTemplateFieldValue(Field field)
	{
		if (!field.isMainSum())
		{
			//������� ���� �������� � �������
			return getNullSaveAttributeStringValue(field.getExternalId());
		}
		//������� ����� �������� � DestinationAmount
		Money amount = getDestinationAmount();
		if (amount == null)
		{
			return null;
		}
		return amount.getDecimal().toString();
	}

	/**
	 * ���������� �������� ������������ ��������
	 * @param fieldId ������������� ����
	 * @param newValue �������� ����
	 */
	private void setExtendedFieldValue(String fieldId, Object newValue)
	{
		setNullSaveAttributeStringValue(fieldId, StringHelper.getEmptyIfNull(newValue));
	}

	/**
	 * ���������� �������� �� ��������.
	 * ���� ��� �����, �� �������� �� ���������,
	 * ���� ��� �����, �� �������� �� �������.
	 * @param field ����.
	 * @return �������� ����.
	 */
	public Object getBillingFieldValue(Field field)
	{
		if (StringHelper.isNotEmpty((String)field.getValue()))
		{
			return field.getValue();
		}

		if (StringHelper.isNotEmpty(field.getDefaultValue()))
		{
			return field.getDefaultValue();
		}

		return "";
	}

	public ResidentBank getReceiverBank()
	{
		return new ResidentBank(
				getNullSaveAttributeStringValue(RECEIVER_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
			);
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{
		setNullSaveAttributeStringValue(RECEIVER_BANK_NAME_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getName());
		setNullSaveAttributeStringValue(RECEIVER_BANK_BIK_ATTRIBUTE_NAME,  receiverBank == null ? null : receiverBank.getBIC());
		setNullSaveAttributeStringValue(RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getAccount());
	}

	private Object getExtendedFieldValue(Field field)
	{
		if (field.isMainSum())
		{
			Money amount = getDestinationAmount();
			if (amount == null)
			{
				return null;
			}
			return amount.getDecimal().toString();
		}

		ExtendedAttribute attribute = getAttribute(field.getExternalId());
		if (attribute == null)
		{
			return null;
		}

		return attribute.getStringValue();
	}

	//////////////////////////////////////////////////////////
	//������ ��� ������ � ������ �������� �� ������������ ����������

	public String getBillingCode()
	{
		return getNullSaveAttributeStringValue(PROVIDER_BILLING_CODE_ATTRIBUTE_NAME);
	}

	public void setBillingCode(String code)
	{
		setNullSaveAttributeStringValue(PROVIDER_BILLING_CODE_ATTRIBUTE_NAME, code);
	}

	public String getBillingClientId()
	{
		return getNullSaveAttributeStringValue(PROVIDER_BILLING_CLIENT_ID_ATTRIBUTE_NAME);
	}

	public Service getService()
	{
		return new ServiceImpl(
				getNullSaveAttributeStringValue(PROVIDER_NAME_SERVICE_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(PROVIDER_CODE_SERVICE_ATTRIBUTE_NAME)
		);
	}

	public void setService(Service service)
	{
		setNullSaveAttributeStringValue(PROVIDER_CODE_SERVICE_ATTRIBUTE_NAME, service == null ? null : service.getCode());
		setNullSaveAttributeStringValue(PROVIDER_NAME_SERVICE_ATTRIBUTE_NAME, service == null ? null : service.getName());
	}

	public String getReceiverTransitAccount()
	{
		return getNullSaveAttributeStringValue(RECEIVER_TRANSIT_ACCOUNT_ATTRIBUTE_NAME);
	}

	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		setNullSaveAttributeStringValue(RECEIVER_TRANSIT_ACCOUNT_ATTRIBUTE_NAME, receiverTransitAccount);
	}

	public ResidentBank getReceiverTransitBank()
	{
		return new ResidentBank(
				getNullSaveAttributeStringValue(RECEIVER_TRANSIT_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_TRANSIT_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_TRANSIT_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
		);
	}

	/**
	 * ���������� ���������� ���� ����������
	 * @param bank ���������� ���� ����������
	 */
	public void setReceiverTransitBank(ResidentBank bank)
	{
		setNullSaveAttributeStringValue(RECEIVER_TRANSIT_BANK_NAME_ATTRIBUTE_NAME, bank == null ? null : bank.getName());
		setNullSaveAttributeStringValue(RECEIVER_TRANSIT_BANK_BIK_ATTRIBUTE_NAME,  bank == null ? null : bank.getBIC());
		setNullSaveAttributeStringValue(RECEIVER_TRANSIT_BANK_COR_ACCOUNT_ATTRIBUTE_NAME, bank == null ? null : bank.getAccount());
	}

	public String getReceiverNameForBill()
	{
		return getNullSaveAttributeStringValue(RECEIVER_NAME_ON_BILL_ATTRIBUTE_NAME);
	}

	public void setReceiverNameForBill(String receiverNameForBill)
	{
		setNullSaveAttributeStringValue(RECEIVER_NAME_ON_BILL_ATTRIBUTE_NAME, receiverNameForBill);
	}

	public boolean isNotVisibleBankDetails()
	{
		return !getNullSaveAttributeBooleanValue(PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME);
	}

	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{
		setNullSaveAttributeBooleanValue(PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME, !notVisibleBankDetails);
	}

	public Code getReceiverOfficeCode()
	{
		return new ExtendedCodeImpl(
				getNullSaveAttributeStringValue(RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME)
		);
	}

	public void setReceiverOfficeCode(Code code)
	{
		ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(code);

		setNullSaveAttributeStringValue(RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME, extendedCode == null ? null : extendedCode.getRegion());
		setNullSaveAttributeStringValue(RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME, extendedCode == null ? null : extendedCode.getBranch());
		setNullSaveAttributeStringValue(RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME, extendedCode == null ? null : extendedCode.getOffice());
	}

	public String getIdFromPaymentSystem()
	{
		return idFromPaymentSystem;
	}

	public void setIdFromPaymentSystem(String idFromPaymentSystem)
	{
		this.idFromPaymentSystem = idFromPaymentSystem;
	}

	public String getSalesCheck()
	{
		//��� �������� �� ������������
		return null;
	}

	public void setSalesCheck(String salesCheck)
	{
		//��� �������� �� ������������
	}

	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
	}

	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}

	public void setReceiverPointCode(String receiverPointCode)
	{
		this.receiverPointCode = receiverPointCode;
	}

	public String getMultiBlockReceiverPointCode()
	{
		return multiBlockReceiverPointCode;
	}

	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	public String generateDefaultName(Metadata metadata) throws BusinessException
	{
		String templateName = null;
		if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER == getFormType())
		{
			templateName = getReceiverName();
		}

		if (FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER == getFormType())
		{
			templateName = getReceiverName();
			if (StringHelper.isEmpty(templateName))
			{
				templateName = super.generateDefaultName(metadata);
			}
			if (StringHelper.isNotEmpty(getReceiverAccount()))
			{
				templateName = templateName + " " + getReceiverAccount();
			}
		}

		if (StringHelper.isEmpty(templateName))
		{
			return super.generateDefaultName(metadata);
		}
		return templateName;
	}

	private void fillActualServiceProvider(FieldValuesSource source) throws BusinessException
	{
		//������ �� ����������
		setReceiverPointCode(source.getValue(PROVIDER_EXTERNAL_ID_ATTRIBUTE_NAME));

		String receiverId = source.getValue(PROVIDER_INTERNAL_ID_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(receiverId))
		{
			return;
		}

		Long id = Long.valueOf(receiverId);
		ServiceProviderShort provider = serviceProviderService.findShortProviderById(id);
		if (provider == null)
		{
			throw new BusinessException("��������� providerId = " + id + " �� ������.");
		}

		setReceiverInternalId(id);
		setMultiBlockReceiverPointCode(provider.getUuid());
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
