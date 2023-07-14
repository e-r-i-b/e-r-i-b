package com.rssl.phizic.utils.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Контейнер для кеша
 *
 * @author bogdanov
 * @ created 02.10.14
 * @ $Author$
 * @ $Revision$
 */

class CacheContainer<KeyType, ResultType>
{
	private final long outOfDateTime;
	private final long updatePeriod;
	private final Map<KeyType, CacheElement<ResultType>> cache = new ConcurrentHashMap<KeyType, CacheElement<ResultType>>();
	private final Map<KeyType, Object> synchronizer = new ConcurrentHashMap<KeyType, Object>();

	CacheContainer(long updatePeriod)
	{
		this(updatePeriod, false);
	}

	CacheContainer(long updatePeriod, boolean nextPeriod)
	{
		this.updatePeriod = updatePeriod;
		//В контейнере хранится данные до 4х периодов обновления.
		this.outOfDateTime = System.currentTimeMillis() + (nextPeriod ? 8 : 4)*updatePeriod;
	}

	/**
	 * Удаление элемента из кеша.
	 *
	 * @param key ключ.
	 */
	synchronized void removeElement(KeyType key)
	{
		cache.remove(key);
		synchronizer.remove(key);
	}

	/**
	 * Очистка кеша.
	 */
	synchronized void clear()
	{
		cache.clear();
		synchronizer.clear();
	}

	/**
	 * @param cacheKey ключ кеша.
	 * @return содержится ли элемент с указанным ключем в кеше.
	 */
	boolean containsKey(KeyType cacheKey)
	{
		return cache.containsKey(cacheKey);
	}

	/**
	 * @param cacheKey ключ хеша.
	 * @return объект для синхронизации.
	 */
	Object getSynchronizer(KeyType cacheKey)
	{
		return synchronizer.get(cacheKey);
	}

	/**
	 * @param cacheKey ключ кеша.
	 * @param cacheElement результат по ключу.
	 * @return смогли ли мы добавить элемент.
	 */
	boolean putElement(KeyType cacheKey, CacheElement<ResultType> cacheElement)
	{
		if (cacheElement.getLastUpdateTime() + updatePeriod > outOfDateTime)
		{
			removeElement(cacheKey);
			return false;
		}

		cache.put(cacheKey, cacheElement);
		synchronizer.put(cacheKey, new Object());
		return true;
	}

	CacheElement<ResultType> getElement(KeyType cacheKey)
	{
		return cache.get(cacheKey);
	}

	/**
	 * @return контейнер устарел.
	 */
	boolean isOutOfDate()
	{
		return outOfDateTime < System.currentTimeMillis();
	}
}
