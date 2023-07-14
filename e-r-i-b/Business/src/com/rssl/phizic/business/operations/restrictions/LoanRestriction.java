package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.loans.Loan;

/**
 * @author gladishev
 * @ created 28.03.2008
 * @ $Author$
 * @ $Revision$
 */

public interface LoanRestriction extends Restriction
{
	/**
	 * Подходит ли кредит под критерии
	 * @param loan - кредит клиента
	 * @return true - подходит, false - не подходит
	 */
	boolean accept(Loan loan);
}
