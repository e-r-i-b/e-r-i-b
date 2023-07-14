package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.deposits.DepositProduct;

/**
 * @author Evgrafov
 * @ created 24.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1377 $
 */

public interface DepositProductRestriction extends Restriction
{
	boolean accept(DepositProduct depositProduct);
}
