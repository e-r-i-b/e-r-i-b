package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;

/**
 * @author gladishev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ServiceProviderCacheKeyComposer implements CacheKeyComposer
{
	private static final String STATIC_PART_KEY = "provider";

	public String getKey(Object object)
	{
		Long id = ((ServiceProviderBase) object).getId();
		if (id == null)
			return null;
		
		return STATIC_PART_KEY + id.toString();
	}
}