package com.rssl.phizgate.mobilebank.cache.techbreak;


/**
 *
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public class GetClientByCardNumberCacheEntry extends GetClientCacheEntryBase
{
	public static final String CACHE_NAME = "mobilebank-clientbycard-cache";

	@Override
	String getCacheKey()
	{
		return getCardNumber();
	}

	@Override
	String getAppServerCacheName()
	{
		return CACHE_NAME;
	}
}
