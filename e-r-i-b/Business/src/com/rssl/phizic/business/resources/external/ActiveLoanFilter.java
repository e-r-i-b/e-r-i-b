package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;

/**
 * Фильтр отсеивает закрытые кредиты
 * @author lukina
 * @ created 24.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ActiveLoanFilter extends LoanFilterBase
{
	public boolean accept(Loan loan)
	{
		return loan.getState() != LoanState.closed;
	}
}
