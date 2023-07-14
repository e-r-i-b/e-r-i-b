package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.claims.SecuritiesTransferClaim;
import com.rssl.phizic.gate.depo.DeliveryType;
import com.rssl.phizic.gate.depo.TransferOperation;
import com.rssl.phizic.gate.depo.TransferSubOperation;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.StringHelper;

/**
 * Шаблон документа покупки ценных бумаг
 *
 * @author khudyakov
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */
public class SecuritiesTransferTemplate extends CommonTemplateBase
{
	public Class<? extends GateDocument> getType()
	{
		return SecuritiesTransferClaim.class;
	}

	public FormType getFormType()
	{
		return FormType.SECURITIES_TRANSFER_CLAIM;
	}

	@Override
	public TransferOperation getOperType()
	{
		String value = getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_OP_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		return TransferOperation.valueOf(value);
	}

	@Override
	public TransferSubOperation getOperationSubType()
	{
		String value = getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_OP_SUB_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		return TransferSubOperation.valueOf(value);
	}

	@Override
	public String getOperationDesc()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_OP_DESC_ATTRIBUTE_NAME);
	}

	@Override
	public String getDivisionType()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_DIVISION_TYPE_ATTRIBUTE_NAME);
	}

	@Override
	public String getDivisionNumber()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_DIVISION_NUM_ATTRIBUTE_NAME);
	}

	@Override
	public String getInsideCode()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_INSIDE_CODE_ATTRIBUTE_NAME);
	}

	@Override
	public Long getSecurityCount()
	{
		return getNullSaveAttributeLongValue(Constants.SECURITY_TRANSFER_SECURITY_COUNT_ATTRIBUTE_NAME);
	}

	@Override
	public String getOperationReason()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_OP_REASON_ATTRIBUTE_NAME);
	}

	@Override
	public String getCorrDepositary()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_CORR_DEPOSITARY_ATTRIBUTE_NAME);
	}

	@Override
	public String getCorrDepoAccount()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_CORDEPO_ACCOUNT_ATTRIBUTE_NAME);
	}

	@Override
	public String getCorrDepoAccountOwner()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_CORDEPO_OWNER_ATTRIBUTE_NAME);
	}

	@Override
	public String getAdditionalInfo()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_ADD_INFO_ATTRIBUTE_NAME);
	}

	@Override
	public DeliveryType getDeliveryType()
	{
		String value = getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_DELIVERY_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		return DeliveryType.valueOf(value);
	}

	@Override
	public String getRegistrationNumber()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_REG_NUMBER_ATTRIBUTE_NAME);
	}

	@Override
	public String getDepoExternalId()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_DEPO_EX_ID_ATTRIBUTE_NAME);
	}

	@Override
	public String getDepoAccountNumber()
	{
		return getNullSaveAttributeStringValue(Constants.SECURITY_TRANSFER_DEPO_ACC_NUMBER_ATTRIBUTE_NAME);
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
