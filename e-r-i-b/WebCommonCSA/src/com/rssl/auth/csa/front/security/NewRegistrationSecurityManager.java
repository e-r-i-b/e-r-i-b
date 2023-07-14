package com.rssl.auth.csa.front.security;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import com.rssl.auth.security.SecurityManager;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author niculichev
 * @ created 14.11.13
 * @ $Author$
 * @ $Revision$
 */
public class NewRegistrationSecurityManager implements SecurityManager
{
	private static final String PREFIX = NewRegistrationSecurityManager.class.getName() + ".";
	private static volatile NewRegistrationSecurityManager this0;

	// все дейтвия должны быть синхронизированы
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	// кэш для хранения времени совершения операции.(испльзуется кэш, т.к. он поддерживает распределенность)
	private Cache timeCache;
	// кэш для хранения неуспешных попыток совершения операции.(испльзуется кэш, т.к. он поддерживает распределенность)
	private Cache attemptsCache;

	private NewRegistrationSecurityManager()
	{
		timeCache = CacheProvider.getCache(PREFIX + "time");
		attemptsCache = CacheProvider.getCache(PREFIX + "attempts");
	}

	/**
	 * @return экземпляр менеджера.
	 */
	public static NewRegistrationSecurityManager getIt()
	{
		if (this0 != null)
			return this0;

		synchronized (NewRegistrationSecurityManager.class)
		{
			if (this0 != null)
				return this0;

			this0 = new NewRegistrationSecurityManager();
			return this0;
		}
	}

	public void processUserAction(String key)
	{
		lock.writeLock().lock();
		try
		{
			long currentTime = System.currentTimeMillis();
			Long previousTime = putCacheTime(key, currentTime);

			// если впервый раз
			if(previousTime == null)
			{
				putCacheAttempts(key, 1L);
				return;
			}

			// если время вышло считаем по новой
			if((currentTime - previousTime) > getTimeDeadLine())
			{
				putCacheAttempts(key, 1L);
				return;
			}

			incrementCacheAttempts(key);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}

	public boolean userTrusted(String key)
	{
		lock.readLock().lock();
		try
		{
			Element elementTime = timeCache.get(key);
			if(elementTime == null)
				return true;

			Element elementAttempts = attemptsCache.get(key);
			if(elementAttempts == null)
				return true;

			return ((Long) elementAttempts.getValue()) < getAttemptsDeadLine();
		}
		finally
		{
			lock.readLock().unlock();
		}
	}

	public void reset(String key)
	{
		putCacheAttempts(key, 0L);
	}

	private Long putCacheTime(String key, Long value)
	{
		Element previousElement = timeCache.get(key);
		Long previousValue = previousElement != null ? (Long) previousElement.getValue() : null;

		timeCache.put(new Element(key, value));

		return previousValue;
	}

	private Long putCacheAttempts(String key, Long value)
	{
		Element previousElement = attemptsCache.get(key);
		Long previousValue = previousElement != null ? (Long) previousElement.getValue() : null;

		attemptsCache.put(new Element(key, value));

		return previousValue;
	}

	private Long incrementCacheAttempts(String key)
	{
		Element previousElement = attemptsCache.get(key);
		Long previousValue = previousElement != null ? (Long) previousElement.getValue() : null;
		long value = previousValue == null ? 1L : previousValue + 1;

		attemptsCache.put(new Element(key, value));

		return value;
	}

	private long getTimeDeadLine()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getRegistrationFinishCaptchaTime() * 1000;
	}

	private long getAttemptsDeadLine()
	{
		CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
		return config.getRegistrationFinishCaptchaAttempts();
	}
}
