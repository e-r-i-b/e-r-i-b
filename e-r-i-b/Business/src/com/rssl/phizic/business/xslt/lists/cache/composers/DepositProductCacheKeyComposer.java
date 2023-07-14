package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.deposits.DepositProduct;

/**
 * @author gladishev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductCacheKeyComposer implements CacheKeyComposer
{
	private static final String STATIC_PART_KEY = "deposit";

	public String getKey(Object object)
	{
		Long id = ((DepositProduct) object).getId();
		if (id == null)
			return null;

		return STATIC_PART_KEY + id.toString();
	}
}
