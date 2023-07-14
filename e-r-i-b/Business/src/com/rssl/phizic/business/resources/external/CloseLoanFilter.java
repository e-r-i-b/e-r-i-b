package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;

/**
 * Фильтр отсеивает аннуитетные и закрытые кредиты
 * @author niculichev
 * @ created 17.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class CloseLoanFilter extends LoanFilterBase
{
	private DifferentLoanFilter differentLoanFilter = new DifferentLoanFilter();

	public boolean accept(Loan loan)
	{
		return loan.getState() != LoanState.closed && differentLoanFilter.accept(loan) ;
	}
}
