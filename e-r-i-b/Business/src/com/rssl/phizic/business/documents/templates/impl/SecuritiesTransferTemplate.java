package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.claims.SecuritiesTransferClaim;
import com.rssl.phizic.gate.depo.DeliveryType;
import com.rssl.phizic.gate.depo.TransferOperation;
import com.rssl.phizic.gate.depo.TransferSubOperation;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * Шаблон перевода ценных бумаг
 *
 * @author khudyakov
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class SecuritiesTransferTemplate extends TransferTemplateBase
{
	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		return FormType.SECURITIES_TRANSFER_CLAIM;
	}

	public Class<? extends GateDocument> getType()
	{
		return SecuritiesTransferClaim.class;
	}

	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = super.getFormData();
		appendNullSaveLong(formData, SECURITY_TRANSFER_SECURITY_COUNT_ATTRIBUTE_NAME, getSecurityCount());

		return formData;
	}

	public void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException
	{
		super.setFormData(source);

		String securityCount = source.getValue(SECURITY_TRANSFER_SECURITY_COUNT_ATTRIBUTE_NAME);
		setNullSaveAttributeLongValue(SECURITY_TRANSFER_SECURITY_COUNT_ATTRIBUTE_NAME, StringHelper.isEmpty(securityCount) ? null : Long.valueOf(securityCount));
	}

	/**
	 * @return наименование ценных бумаг
	 */
	public String getSecurityName()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_SECURITY_NAME_ATTRIBUTE_NAME);
	}

	//////////////////////////////////////////////////////////
	//методы для работы с полями перевода ценных бумаг

	public TransferOperation getOperType()
	{
		String value = getNullSaveAttributeStringValue(SECURITY_TRANSFER_OP_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		return TransferOperation.valueOf(value);
	}

	public TransferSubOperation getOperationSubType()
	{
		String value = getNullSaveAttributeStringValue(SECURITY_TRANSFER_OP_SUB_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		return TransferSubOperation.valueOf(value);
	}

	public String getOperationDesc()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_OP_DESC_ATTRIBUTE_NAME);
	}

	public String getDivisionType()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_DIVISION_TYPE_ATTRIBUTE_NAME);
	}

	public String getDivisionNumber()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_DIVISION_NUM_ATTRIBUTE_NAME);
	}

	public String getInsideCode()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_INSIDE_CODE_ATTRIBUTE_NAME);
	}

	public Long getSecurityCount()
	{
		return getNullSaveAttributeLongValue(SECURITY_TRANSFER_SECURITY_COUNT_ATTRIBUTE_NAME);
	}

	public String getOperationReason()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_OP_REASON_ATTRIBUTE_NAME);
	}

	public String getCorrDepositary()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_CORR_DEPOSITARY_ATTRIBUTE_NAME);
	}

	public String getCorrDepoAccount()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_CORDEPO_ACCOUNT_ATTRIBUTE_NAME);
	}

	public String getCorrDepoAccountOwner()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_CORDEPO_OWNER_ATTRIBUTE_NAME);
	}

	public String getAdditionalInfo()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_ADD_INFO_ATTRIBUTE_NAME);
	}

	public DeliveryType getDeliveryType()
	{
		String value = getNullSaveAttributeStringValue(SECURITY_TRANSFER_DELIVERY_TYPE_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(value))
		{
			return null;
		}
		return DeliveryType.valueOf(value);
	}

	public String getRegistrationNumber()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_REG_NUMBER_ATTRIBUTE_NAME);
	}

	public String getDepoExternalId()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_DEPO_EX_ID_ATTRIBUTE_NAME);
	}

	public String getDepoAccountNumber()
	{
		return getNullSaveAttributeStringValue(SECURITY_TRANSFER_DEPO_ACC_NUMBER_ATTRIBUTE_NAME);
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
