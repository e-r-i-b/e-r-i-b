package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;
import java.math.BigDecimal;

/**
 * @author Gainanov
 * @ created 07.05.2007
 * @ $Author$
 * @ $Revision$
 */
public class DepositAmountValidator extends MultiFieldsValidatorBase
{
    public static final String FIELD_DEPOSIT = "deposit";
    public static final String FIELD_AMOUNT  = "amount";

    public boolean validate(Map values) throws TemporalDocumentException
    {
	    BigDecimal depositAmount = getDepositMinReplenishmentAmount(values);
	    BigDecimal amount = getAmount(values);

	    return amount.compareTo(depositAmount) < 0;
    }

    private BigDecimal getDepositMinReplenishmentAmount(Map values)
    {
        Deposit deposit = (Deposit) retrieveFieldValue(DepositAmountValidator.FIELD_DEPOSIT, values);
        return (deposit.getAmount() != null ? deposit.getAmount().getDecimal() : new BigDecimal(0));
    }

    private BigDecimal getAmount(Map values)
    {
        return (BigDecimal) retrieveFieldValue(DepositMinReplenishmentAmountValidator.FIELD_AMOUNT, values);
    }
}
