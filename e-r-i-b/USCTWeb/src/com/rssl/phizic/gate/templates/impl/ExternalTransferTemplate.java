package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.gate.dictionaries.ResidentBank;

/**
 * Базовый класс шаблонов внешних операций
 *
 * @author khudyakov
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ExternalTransferTemplate extends CommonTemplateBase
{
	private String receiverName;


	@Override
	public String getReceiverKPP()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_KPP_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverKPP(String receiverKPP)
	{
		setNullSaveAttributeStringValue(Constants.RECEIVER_KPP_ATTRIBUTE_NAME, receiverKPP);
	}

	@Override
	public String getReceiverPhone()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_PHONE_NUMBER_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverPhone(String receiverPhone)
	{
		setNullSaveAttributeStringValue(Constants.RECEIVER_PHONE_NUMBER_ATTRIBUTE_NAME, receiverPhone);
	}

	@Override
	public String getReceiverINN()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_INN_ATTRIBUTE_NAME);
	}

	@Override
	public void setReceiverINN(String receiverINN)
	{
		setNullSaveAttributeStringValue(Constants.RECEIVER_INN_ATTRIBUTE_NAME, receiverINN);
	}

	@Override
	public ResidentBank getReceiverBank()
	{
		return new ResidentBank(
				getNullSaveAttributeStringValue(Constants.INDIVIDUAL_RECEIVER_BANK_NAME_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME),
				getNullSaveAttributeStringValue(Constants.RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME)
		);
	}

	@Override
	public void setReceiverBank(ResidentBank receiverBank)
	{
		setNullSaveAttributeStringValue(Constants.INDIVIDUAL_RECEIVER_BANK_NAME_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getName());
		setNullSaveAttributeStringValue(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getBIC());
		setNullSaveAttributeStringValue(Constants.RECEIVER_BANK_COR_ACCOUNT_ATTRIBUTE_NAME, receiverBank == null ? null : receiverBank.getAccount());
	}

	@Override
	public String getReceiverSurName()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_SURNAME_ATTRIBUTE_NAME);
	}

	@Override
	public String getReceiverFirstName()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_FIRST_NAME_ATTRIBUTE_NAME);
	}

	@Override
	public String getReceiverPatrName()
	{
		return getNullSaveAttributeStringValue(Constants.RECEIVER_PATR_NAME_ATTRIBUTE_NAME);
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
}
