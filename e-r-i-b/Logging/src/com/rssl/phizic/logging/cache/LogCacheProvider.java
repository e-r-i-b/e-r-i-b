package com.rssl.phizic.logging.cache;

import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author gladishev
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * Провайдер кеша для лога
 */
public class LogCacheProvider
{
	private static volatile CacheManager logCacheManager = null;

	/**
	 * Получить кеш по имени.
	 * @param name имя кеша
	 * @return кеш или null, если кеш отсутсвует.
	 */
	public static Cache getCache(String name)
	{
		Cache cache = getCacheManager().getCache(name);

		if ( cache == null )
			throw new RuntimeException("Не найден кеш для расширенного логирования");

		return cache;
	}

	private static CacheManager getCacheManager()
	{
		if (logCacheManager != null)
		{
			return logCacheManager;
		}
		synchronized (LogCacheProvider.class)
		{
			if (logCacheManager != null)
			{
				return logCacheManager;
			}
			logCacheManager = CacheProvider.createCacheManager("log-ehcache.xml");
		}

		return logCacheManager;
	}

	/**
	 * Остановить работу кеша.
	 */
	public static synchronized void shutdown()
	{
		if (logCacheManager == null)
			return;
		
		logCacheManager.shutdown();
		logCacheManager = null;
	}
}
