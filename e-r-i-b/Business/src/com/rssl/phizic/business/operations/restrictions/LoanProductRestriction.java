package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.loans.products.LoanProduct;

/**
 * @author gladishev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */

public interface LoanProductRestriction extends Restriction
{
	boolean accept(LoanProduct loanProduct);
}
