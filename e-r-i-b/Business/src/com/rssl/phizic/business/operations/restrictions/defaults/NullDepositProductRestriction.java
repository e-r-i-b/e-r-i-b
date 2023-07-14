package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.operations.restrictions.DepositProductRestriction;

/**
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author: osminin $
 * @ $Revision: 11448 $
 */
public class NullDepositProductRestriction implements DepositProductRestriction
{
	public boolean accept(DepositProduct depositProduct)
	{
		return true;
	}
}