package com.rssl.phizic.gate.templates.impl;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.ExtendedFieldsHelper;
import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.gate.recipients.ServiceImpl;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Шаблон операции с переменным количеством полей
 *
 * @author khudyakov
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemTransferTemplate extends ExternalTransferTemplate
{
	private String extendedFieldsAsString;
	private String receiverPointCode;
	private String multiBlockReceiverPointCode;
	private String idFromPaymentSystem;


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

		throw new IllegalStateException("Неверный тип источника списания " + resourceType);
	}

	public FormType getFormType()
	{
		return StringHelper.isEmpty(getMultiBlockReceiverPointCode()) ?
				FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER : FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER;
	}

	public String getExtendedFieldsAsString()
	{
		return extendedFieldsAsString;
	}

	public void setExtendedFieldsAsString(String extendedFieldsAsString)
	{
		this.extendedFieldsAsString = extendedFieldsAsString;
	}

	@Override
	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}

	@Override
	public void setReceiverPointCode(String receiverPointCode)
	{
		this.receiverPointCode = receiverPointCode;
	}

	@Override
	public String getMultiBlockReceiverPointCode()
	{
		return multiBlockReceiverPointCode;
	}

	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	@Override
	public String getIdFromPaymentSystem()
	{
		return idFromPaymentSystem;
	}

	@Override
	public void setIdFromPaymentSystem(String idFromPaymentSystem)
	{
		this.idFromPaymentSystem = idFromPaymentSystem;
	}

	@Override
	public String getBillingCode()
	{
		return getNullSaveAttributeStringValue(Constants.PROVIDER_BILLING_CODE_ATTRIBUTE_NAME);
	}

	@Override
	public void setBillingCode(String billingCode)
	{
		setNullSaveAttributeStringValue(Constants.PROVIDER_BILLING_CODE_ATTRIBUTE_NAME, billingCode);
	}

	@Override
	public String getBillingClientId()
	{
		return getNullSaveAttributeStringValue(Constants.PROVIDER_BILLING_CLIENT_ID_ATTRIBUTE_NAME);
	}

	@Override
	public Service getService()
	{
		return new ServiceImpl(
				getNullSaveAttributeStringValue(Constants.PROVIDER_NAME_SERVICE_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.PROVIDER_CODE_SERVICE_ATTRIBUTE_NAME)
		);
	}

	@Override
	public void setService(Service service)
	{
		setNullSaveAttributeStringValue(Constants.PROVIDER_CODE_SERVICE_ATTRIBUTE_NAME, service == null ? null : service.getCode());
		setNullSaveAttributeStringValue(Constants.PROVIDER_NAME_SERVICE_ATTRIBUTE_NAME, service == null ? null : service.getName());
	}

	@Override
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

	@Override
	public String getReceiverTransitAccount()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_TRANSIT_ACCOUNT_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverTransitAccount(String receiverTransitAccount)
	{
		setNullSaveAttributeStringValue(Constants.RECEIVER_TRANSIT_ACCOUNT_ATTRIBUTE_NAME, receiverTransitAccount);
	}

	@Override
	public ResidentBank getReceiverBank()
	{
		return new ResidentBank(
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
		);
	}

	@Override
	public void setReceiverBank(ResidentBank receiverBank)
	{
		setNullSaveAttributeStringValue(Constants.RECEIVER_BANK_NAME_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getName());
		setNullSaveAttributeStringValue(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME,  receiverBank == null ? null : receiverBank.getBIC());
		setNullSaveAttributeStringValue(Constants.RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getAccount());
	}

	@Override
	public ResidentBank getReceiverTransitBank()
	{
		return new ResidentBank(
				getNullSaveAttributeStringValue(Constants.RECEIVER_TRANSIT_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_TRANSIT_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_TRANSIT_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
		);
	}

	@Override
	public String getReceiverNameForBill()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_NAME_ON_BILL_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverNameForBill(String receiverNameForBill)
	{
		setNullSaveAttributeStringValue(Constants.RECEIVER_NAME_ON_BILL_ATTRIBUTE_NAME, receiverNameForBill);
	}

	@Override
	public boolean isNotVisibleBankDetails()
	{
		return !getNullSaveAttributeBooleanValue(Constants.PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME);
	}

	@Override
	public void setNotVisibleBankDetails(boolean notVisibleBankDetails)
	{
		setNullSaveAttributeBooleanValue(Constants.PROVIDER_IS_BANK_DETAILS_ATTRIBUTE_NAME, !notVisibleBankDetails);
	}


	@Override
	public Code getReceiverOfficeCode()
	{
		return new ExtendedCodeGateImpl(
				getNullSaveAttributeStringValue(Constants.RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME)
		);
	}

	@Override
	public void setReceiverOfficeCode(Code code)
	{
		ExtendedCodeGateImpl extendedCode = new ExtendedCodeGateImpl(code);

		setNullSaveAttributeStringValue(Constants.RECEIVER_OFFICE_REGION_ATTRIBUTE_NAME, extendedCode == null ? null : extendedCode.getRegion());
		setNullSaveAttributeStringValue(Constants.RECEIVER_OFFICE_BRANCH_ATTRIBUTE_NAME, extendedCode == null ? null : extendedCode.getBranch());
		setNullSaveAttributeStringValue(Constants.RECEIVER_OFFICE_OFFICE_ATTRIBUTE_NAME, extendedCode == null ? null : extendedCode.getOffice());
	}

	@Override
	public void setExtendedFields(List<Field> extendedFields) throws DocumentException
	{
		//на данный момент считаем, что ЕСУШ - это только средство хранения данных по шаблону
		//сохраняем описания полей.
		setExtendedFieldsAsString(ExtendedFieldsHelper.serialize(extendedFields));
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
