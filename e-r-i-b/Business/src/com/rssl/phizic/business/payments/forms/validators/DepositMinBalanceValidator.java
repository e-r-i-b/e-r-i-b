package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;
import java.math.BigDecimal;

/**
 * ѕроверка на то, что при списании указанной суммы остаток не станет меньше неснижаемого
 * ѕроверка работает с допущением что комиссии нет
 * @author eMakarov
 * @ created 04.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class DepositMinBalanceValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_DEPOSIT = "deposit";
    public static final String FIELD_AMOUNT  = "amount";

    public boolean validate(Map values) throws TemporalDocumentException
    {
	    BigDecimal minBalanceAmount = getDepositMinBalanceAmount(values);
	    BigDecimal currentBalanceAmount = getDepositBalanceAmount(values);
	    BigDecimal amount = getAmount(values);

	    return amount.compareTo(currentBalanceAmount.add(minBalanceAmount.negate())) <= 0;
    }

    private BigDecimal getDepositMinBalanceAmount(Map values)
    {
        Deposit deposit = (Deposit) retrieveFieldValue(FIELD_DEPOSIT, values);
	    DepositService service = GateSingleton.getFactory().service(DepositService.class);
	    try
	    {
		    DepositInfo depositInfo = service.getDepositInfo(deposit);
		    return (depositInfo.getMinBalance() != null ? depositInfo.getMinBalance().getDecimal() : new BigDecimal(0));
	    }
	    catch (GateException e)
	    {
		    throw new RuntimeException(e);
	    }
	    catch (GateLogicException e)
	    {
		    throw new RuntimeException(e);
	    }
    }

	private BigDecimal getDepositBalanceAmount(Map values)
    {
        Deposit deposit = (Deposit) retrieveFieldValue(FIELD_DEPOSIT, values);
        return (deposit.getAmount() != null ? deposit.getAmount().getDecimal() : new BigDecimal(0));
    }

    private BigDecimal getAmount(Map values)
    {
        return (BigDecimal) retrieveFieldValue(FIELD_AMOUNT, values);
    }
}
