package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.operations.restrictions.LoanRestriction;
import com.rssl.phizic.gate.loans.Loan;

/**
 * @author gladishev
 * @ created 28.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class NullLoanRestriction implements LoanRestriction
{
	public static final LoanRestriction INSTANCE = new NullLoanRestriction();

	public boolean accept(Loan loan)
	{
		return true;
	}
}
