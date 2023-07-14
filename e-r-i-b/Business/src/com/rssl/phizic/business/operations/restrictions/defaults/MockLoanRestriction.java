package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.operations.restrictions.LoanRestriction;
import com.rssl.phizic.business.loans.mock.MockLoan;
import com.rssl.phizic.gate.loans.Loan;

/**
 * @author komarov
 * @ created 10.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class MockLoanRestriction implements LoanRestriction
{
	public static final LoanRestriction INSTANCE = new MockLoanRestriction();

	public boolean accept(Loan loan)
	{
		return !(loan instanceof MockLoan);
	}
}
