package com.rssl.phizgate.mobilebank.cache.techbreak;

/**
 * @author Jatsky
 * @ created 03.08.15
 * @ $Author$
 * @ $Revision$
 */

public class GetRegistrationsPack3CacheEntry extends GetRegistrationsCachePackEntryBase
{
	public static final String CACHE_NAME = "mobilebank-registration-pack3-cache";

	@Override String getAppServerCacheName()
	{
		return CACHE_NAME;
	}
}
