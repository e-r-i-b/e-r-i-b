package com.rssl.phizic.gate.cache.proxy;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.ehcache.Cache;

import java.lang.reflect.Method;

/**
 * @author Omeliyanchuk
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public final class GateBusinessCacheSingleton
{
	public static final String LINKS_CACHE_NAME = "LINKS_CACHE_NAME";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);

	private static final GateBusinessCacheSingleton gateBusinessCacheSingleton = new GateBusinessCacheSingleton();
	private final GateBusinessCacheConfig cacheConfig = new GateBusinessCacheConfig();
	private final String applicationName;

	public static GateBusinessCacheSingleton getInstance()
	{
		return gateBusinessCacheSingleton;
	}

	private GateBusinessCacheSingleton()
	{
		applicationName = ApplicationConfig.getIt().getApplicationPrefixAdoptedToFileName();
		//кеш дл€ св€занных сущностей, т.е. чтоб помимо информации по счету, чистить еще и список счетов клиента
		//в среднем 5 продуктов на клиента, 500 клиентов на одном —ѕ, врем€ жизни кеша 45 минут.
		addCache(LINKS_CACHE_NAME, 5*500, 45*60, 45*60);
	}

	public synchronized void addCache(String name, int maxElementsInMemory, int timeToIdleSeconds, int timeToLiveSeconds)
	{
		addCache(name, maxElementsInMemory, false, timeToIdleSeconds, timeToLiveSeconds);
	}

	public synchronized void addCache(String name, int maxElementsInMemory, boolean eternal, int timeToIdleSeconds, int timeToLiveSeconds)
	{
		name = name + applicationName;
		CacheProvider.addCache(name, maxElementsInMemory, true, eternal, timeToIdleSeconds, timeToLiveSeconds);
		log.trace("ƒобавлен кеш дл€ xml-справочника с именем " + name);
	}

	public synchronized void registerMethod(Method method)
	{
		cacheConfig.addMethod(method);
	}

	public Cache getCache(String name)
	{
		return CacheProvider.getCache(name + applicationName);
	}

	public GateBusinessCacheConfig getCacheGateBusinessConfig()
	{
		return cacheConfig;
	}
}