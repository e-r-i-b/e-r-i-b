package com.rssl.phizic.business.documents.templates.impl;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.LoanTransfer;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

import static com.rssl.phizic.business.documents.templates.Constants.*;

/**
 * Шаблон платежа оплаты кредита
 *
 * @author khudyakov
 * @ created 12.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoanTransferTemplate extends TransferTemplateBase
{
	public String getStateMachineName()
	{
		return getFormType().getName();
	}

	public FormType getFormType()
	{
		return FormType.LOAN_PAYMENT;
	}

	public Class<? extends GateDocument> getType()
	{
		return LoanTransfer.class;
	}

	public Map<String, String> getFormData() throws BusinessException
	{
		Map<String, String> formData = super.getFormData();
		appendNullSaveMoney(formData, AMOUNT_VALUE_ATTRIBUTE_NAME, getChargeOffAmount());

		Object linkId = getNullSaveAttributeValue(LOAN_LINK_ID_ATTRIBUTE_NAME);
		if (linkId != null)
		{
			appendNullSaveString(formData, LOAN_ATTRIBUTE_NAME, LoanLink.CODE_PREFIX + ":" + linkId);
		}

		return formData;
	}

	public String getLoanLinkId()
	{
		return getNullSaveAttributeStringValue(LOAN_LINK_ID_ATTRIBUTE_NAME);
	}

	protected String getChargeOffResourceAmountAttributeName()
	{
		return AMOUNT_VALUE_ATTRIBUTE_NAME;
	}

	protected String getChargeOffResourceCurrencyAmountAttributeName()
	{
		return AMOUNT_VALUE_ATTRIBUTE_NAME + CURRENCY_ATTRIBUTE_SUFFIX;
	}

	protected String getDestinationAmountCurrencyValue()
	{
		return StringUtils.EMPTY;
	}

	/**
	 * @return название кредита
	 */
	public String getLoanName()
	{
		return getNullSaveAttributeStringValue(LOAN_NAME_ATTRIBUTE_NAME);
	}

	/**
	 * @return тип кредита
	 */
	public String getLoanType()
	{
		return getNullSaveAttributeStringValue(LOAN_TYPE_ATTRIBUTE_NAME);
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
