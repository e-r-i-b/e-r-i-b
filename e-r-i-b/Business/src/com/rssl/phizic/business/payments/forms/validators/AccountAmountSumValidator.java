package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.business.BusinessException;

import java.util.Map;
import java.math.BigDecimal;

/**
 * @author Egorova
 * @ created 08.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountAmountSumValidator extends AccountAmountValidator
{
	public static final String FIELD_INSURANCE_AMOUNT_RUR = "insuranceAmountRur";
	public static final String FIELD_INSURANCE_AMOUNT_COP = "insuranceAmount—op";

	protected BigDecimal getExpectedAmount(Map values)
	{
		BigDecimal amount = super.getExpectedAmount(values);
		BigDecimal insuranceAmountRur = getInsuranceAmountRur(values);
		BigDecimal insuranceAmountCop = getInsuranceAmountCop(values);
		if (insuranceAmountRur != null)
		{
			amount = amount.add(insuranceAmountRur.add(insuranceAmountCop));
		}
		if (insuranceAmountCop != null)
		{
			amount = amount.add(insuranceAmountCop);
		}
		return amount;
	}

	private BigDecimal getInsuranceAmountRur(Map values)
	{
		return (BigDecimal) retrieveFieldValue(FIELD_INSURANCE_AMOUNT_RUR, values);
	}

	private BigDecimal getInsuranceAmountCop(Map values)
	{
		return (BigDecimal) retrieveFieldValue(FIELD_INSURANCE_AMOUNT_COP, values);
	}
}
