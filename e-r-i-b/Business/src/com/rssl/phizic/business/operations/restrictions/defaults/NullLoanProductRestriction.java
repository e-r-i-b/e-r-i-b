package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.operations.restrictions.LoanProductRestriction;
import com.rssl.phizic.business.loans.products.LoanProduct;

/**
 * @author gladishev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class NullLoanProductRestriction implements LoanProductRestriction
{
	public static final LoanProductRestriction INSTANCE = new NullLoanProductRestriction();

	public boolean accept(LoanProduct loanProduct)
	{
		return true;
	}
}
