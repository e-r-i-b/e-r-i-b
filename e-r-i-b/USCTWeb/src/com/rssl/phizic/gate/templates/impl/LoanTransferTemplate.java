package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.LoanTransfer;

import java.util.Calendar;

/**
 * Шаблон платежа оплаты кредита
 *
 * @author khudyakov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */
public class LoanTransferTemplate extends CommonTemplateBase
{
	public Class<? extends GateDocument> getType()
	{
		return LoanTransfer.class;
	}

	public FormType getFormType()
	{
		return FormType.LOAN_PAYMENT;
	}

	@Override
	public String getLoanExternalId()
	{
		return getNullSaveAttributeStringValue(Constants.LOAN_LINK_ID_ATTRIBUTE_NAME);
	}

	@Override
	public String getAccountNumber()
	{
		return getNullSaveAttributeStringValue(Constants.LOAN_ACCOUNT_NUMBER_ATTRIBUTE_NAME);
	}

	@Override
	public String getAgreementNumber()
	{
		return getNullSaveAttributeStringValue(Constants.LOAN_AGREEMENT_NUMBER_ATTRIBUTE_NAME);
	}

	@Override
	public String getIdSpacing()
	{
		//в шаблонах не используется
		return null;
	}

	@Override
	public Calendar getSpacingDate()
	{
		//в шаблонах не используется
		return null;
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}
}
