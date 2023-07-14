package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateSingleton;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Проверка что на проверяемая сумма не меньше минимальной суммы пополнения депозита
 * Проверка работает с допущением что валюта депозита и валюта суммы совпадают
 * @author Evgrafov
 * @ created 05.12.2005
 * @ $Author: Evgrafov $
 * @ $Revision: 1355 $
 */

public class DepositMinReplenishmentAmountValidator extends MultiFieldsValidatorBase
{
    public static final String FIELD_DEPOSIT = "deposit";
    public static final String FIELD_AMOUNT  = "amount";

    public boolean validate(Map values) throws TemporalDocumentException
    {
	    BigDecimal minReplenishmentAmount = null;
	    try
	    {
		    minReplenishmentAmount = getDepositMinReplenishmentAmount(values);
	    }
	    catch (GateException e)
	    {
		   throw new RuntimeException(e);
	    }
	    BigDecimal amount = getAmount(values);

	    return amount.compareTo(minReplenishmentAmount) >= 0;
    }

    private BigDecimal getDepositMinReplenishmentAmount(Map values) throws GateException
    {
        Deposit deposit = (Deposit) retrieveFieldValue(DepositMinReplenishmentAmountValidator.FIELD_DEPOSIT, values);
	    DepositService service = GateSingleton.getFactory().service(DepositService.class);

        try
        {
	        DepositInfo depositInfo = service.getDepositInfo(deposit);
            return (depositInfo.getMinReplenishmentAmount() != null ? depositInfo.getMinReplenishmentAmount().getDecimal() : new BigDecimal(0));
        }
        catch (GateLogicException e)
        {
	        throw new RuntimeException(e);
        }
    }

    private BigDecimal getAmount(Map values)
    {
        return (BigDecimal) retrieveFieldValue(DepositMinReplenishmentAmountValidator.FIELD_AMOUNT, values);
    }
}
