package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Map;

import static com.rssl.phizic.business.documents.templates.Constants.*;
import static com.rssl.phizic.business.documents.templates.Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME;
import static com.rssl.phizic.business.documents.templates.Constants.RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME;

/**
 * Базовый класс шаблона платежа внешнему получателю
 *
 * @author khudyakov
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class ExternalTransferTemplate extends TransferTemplateBase
{
	private String receiverName;


	@Override
	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = super.getFormData();
		appendNullSaveString(formData, RECEIVER_ACCOUNT_ATTRIBUTE_NAME, getDestinationResource());
		appendNullSaveString(formData, RECEIVER_NAME_ATTRIBUTE_NAME, getReceiverName());

		return formData;
	}

	@Override
	public void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException
	{
		super.setFormData(source);

		//получатель платежа
		setReceiverName(source.getValue(RECEIVER_NAME_ATTRIBUTE_NAME));
	}

	@Override
	protected String getDestinationResourceAttributeName()
	{
		return RECEIVER_ACCOUNT_ATTRIBUTE_NAME;
	}

	@Override
	public String getReceiverName()
	{
		return receiverName;
	}

	@Override
	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	@Override
	public String getReceiverSurName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_SURNAME_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverSurName(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_SURNAME_ATTRIBUTE_NAME, value);
	}

	@Override
	public String getReceiverFirstName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_FIRST_NAME_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverFirstName(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_FIRST_NAME_ATTRIBUTE_NAME, value);
	}

	@Override
	public String getReceiverPatrName()
	{
		return getNullSaveAttributeStringValue(RECEIVER_PATR_NAME_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverPatrName(String value)
	{
		setNullSaveAttributeStringValue(RECEIVER_PATR_NAME_ATTRIBUTE_NAME, value);
	}

	@Override
	public String getReceiverINN()
	{
		return getNullSaveAttributeStringValue(RECEIVER_INN_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverINN(String receiverINN)
	{
		setNullSaveAttributeStringValue(RECEIVER_INN_ATTRIBUTE_NAME, receiverINN);
	}

	@Override
	public String getReceiverKPP()
	{
		return getNullSaveAttributeStringValue(RECEIVER_KPP_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverKPP(String receiverKPP)
	{
		setNullSaveAttributeStringValue(RECEIVER_KPP_ATTRIBUTE_NAME, receiverKPP);
	}

	@Override
	public String getReceiverPhone()
	{
		return getNullSaveAttributeStringValue(RECEIVER_PHONE_NUMBER_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverPhone(String receiverPhone)
	{
		setNullSaveAttributeStringValue(RECEIVER_PHONE_NUMBER_ATTRIBUTE_NAME, receiverPhone);
	}

	@Override
	public ResidentBank getReceiverBank()
	{
		return new ResidentBank(
				getNullSaveAttributeStringValue(INDIVIDUAL_RECEIVER_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
		);
	}

	@Override
	public void setReceiverBank(ResidentBank receiverBank)
	{
		setNullSaveAttributeStringValue(INDIVIDUAL_RECEIVER_BANK_NAME_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getName());
		setNullSaveAttributeStringValue(RECEIVER_BANK_BIK_ATTRIBUTE_NAME,  receiverBank == null ? null : receiverBank.getBIC());
		setNullSaveAttributeStringValue(RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getAccount());
	}

	public boolean isEinvoicing()
	{
		return false;
	}
}
