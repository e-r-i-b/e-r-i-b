package com.rssl.phizgate.mobilebank.cache.techbreak;

/**
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public class GetRegistrationsCacheEntry extends GetRegistrationsCacheEntryBase
{
	public static final String CACHE_NAME = "mobilebank-registration-cache";

	@Override
	String getAppServerCacheName()
	{
		return CACHE_NAME;
	}
}
