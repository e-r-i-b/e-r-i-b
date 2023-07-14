package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.loans.Loan;

/**
 * Фильтр отсеивает аннуитетные кредиты
 * т.е. метод accept вернет true, если кредит дифференцированный
 * @author gladishev
 * @ created 06.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class DifferentLoanFilter extends LoanFilterBase
{
	public boolean accept(Loan loan)
	{
		return !loan.getIsAnnuity();
	}
}
