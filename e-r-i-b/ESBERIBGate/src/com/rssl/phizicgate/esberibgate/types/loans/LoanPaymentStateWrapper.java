package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizic.gate.loans.LoanPaymentState;

import java.util.HashMap;
import java.util.Map;

/**
 * Соответсвия кодов состояний платежа из ВС типам состояний платежа нашей системы
 * @author gladishev
 * @ created 12.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentStateWrapper
{
	private static final Map<String, LoanPaymentState> loanPaymentStates = new HashMap<String, LoanPaymentState>();

	static
	{
		loanPaymentStates.put("1", LoanPaymentState.paid);
		loanPaymentStates.put("2", LoanPaymentState.current);
		loanPaymentStates.put("3", LoanPaymentState.future);
	}

	public static LoanPaymentState getLoanPaymentState(String code)
	{
		return loanPaymentStates.get(code);
	}
}
