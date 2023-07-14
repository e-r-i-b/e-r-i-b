package com.rssl.phizgate.mobilebank.cache.techbreak;


/**
 *
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public class GetClientByLoginCacheEntry extends GetClientCacheEntryBase
{
	public static final String CACHE_NAME = "mobilebank-clientbylogin-cache";

	@Override
	String getCacheKey()
	{
		return getAuthIdt();
	}

	@Override
	String getAppServerCacheName()
	{
		return CACHE_NAME;
	}
}
