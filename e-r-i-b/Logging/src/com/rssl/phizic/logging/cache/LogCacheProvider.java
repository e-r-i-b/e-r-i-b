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
 * ��������� ���� ��� ����
 */
public class LogCacheProvider
{
	private static volatile CacheManager logCacheManager = null;

	/**
	 * �������� ��� �� �����.
	 * @param name ��� ����
	 * @return ��� ��� null, ���� ��� ����������.
	 */
	public static Cache getCache(String name)
	{
		Cache cache = getCacheManager().getCache(name);

		if ( cache == null )
			throw new RuntimeException("�� ������ ��� ��� ������������ �����������");

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
	 * ���������� ������ ����.
	 */
	public static synchronized void shutdown()
	{
		if (logCacheManager == null)
			return;
		
		logCacheManager.shutdown();
		logCacheManager = null;
	}
}
