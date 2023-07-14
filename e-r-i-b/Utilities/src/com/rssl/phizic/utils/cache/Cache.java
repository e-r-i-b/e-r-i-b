package com.rssl.phizic.utils.cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Менеджер для работы с кешем.
 *
 * @author bogdanov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

public class Cache<KeyType, ResultType>
{
	private final OnCacheOutOfDateListener<KeyType, ResultType> listener;
	private final Long updatePeriod;

	private final Lock LOCK = new ReentrantLock();
	private volatile CacheContainer<KeyType, ResultType> container;
	private volatile CacheContainer<KeyType, ResultType> nextContainer;

	/**
	 * Создает кеш без слушателя.
	 *
	 * @param updatePeriod период обновления данных в кеше (минуты).
	 */
	public Cache(Long updatePeriod)
	{
		this(null, updatePeriod);
	}

	/**
	 * Создает кеш.
	 *
	 * @param listener слушатель кеша.
	 * @param updatePeriod период обновления данных в кеше (минуты).
	 */
	public Cache(OnCacheOutOfDateListener<KeyType, ResultType> listener, Long updatePeriod)
	{
		this.listener = listener == null ? new OnCacheOutOfDateListener<KeyType, ResultType>()
		{
			public ResultType onRefresh(KeyType key)
			{
				return null;
			}
		} : listener;
		this.updatePeriod = updatePeriod*60*1000;

		container = new CacheContainer<KeyType, ResultType>(this.updatePeriod);
		nextContainer = new CacheContainer<KeyType, ResultType>(this.updatePeriod, true);
	}

	/**
	 * Удаление элемента из кеша.
	 *
	 * @param key ключ.
	 */
	public void removeElement(KeyType key)
	{
		synchronized (getSynchronizer(key))
		{
			container.removeElement(key);
			nextContainer.removeElement(key);
		}
	}

	/**
	 * Очистка кеша.
	 */
	public void clear()
	{
		synchronized (listener)
		{
			container.clear();
			nextContainer.clear();
		}
	}

	/**
	 * @param cacheKey ключ для поиска элемента.
	 * @return закешированные или новые данные.
	 */
	@SuppressWarnings("unchecked")
	public ResultType getValue(KeyType cacheKey)
	{
		update();
		//Если данных нет в кеше, необходимо их загрузить.
		if (!containsKey(cacheKey))
		{
			synchronized (getSynchronizer(cacheKey))
			{
				if (!containsKey(cacheKey))
					return reloadData(cacheKey);
			}
		}

		{
			CacheElement<ResultType> element = get(cacheKey);
			if (!element.needRefresh(updatePeriod))
				return element.getCachedValue();
		}

		//Нужно перезагрузить данные кеша.
		synchronized (getSynchronizer(cacheKey))
		{
			CacheElement<ResultType> element = get(cacheKey);
			if (!element.needRefresh(updatePeriod))
				//Данные уже обновились, отдаем их.
				return element.getCachedValue();

			element.startUpdating();
		}
		return reloadData(cacheKey);
	}

	private ResultType reloadData(KeyType cacheKey)
	{
		ResultType result = listener.onRefresh(cacheKey);
		put0(cacheKey, result);
		return result;
	}

	private boolean containsKey(KeyType key)
	{
		return container.containsKey(key) || nextContainer.containsKey(key);
	}

	private CacheElement<ResultType> get(KeyType key)
	{
		if (container.containsKey(key))
			return container.getElement(key);

		return nextContainer.getElement(key);
	}

	private Object getSynchronizer(KeyType key)
	{
		Object sync = container.getSynchronizer(key);

		if (sync == null)
		{
			sync = nextContainer.getSynchronizer(key);
			if (sync == null)
				sync = listener;
		}

		return sync;
	}

	public void put(KeyType key, ResultType result)
	{
		update();
		put0(key, result);
	}

	private void put0(KeyType key, ResultType result)
	{
		CacheElement<ResultType> ce = new CacheElement<ResultType>(result);
	 	if (!container.putElement(key, ce))
	    {
		    nextContainer.putElement(key, ce);
	    }
	}

	private void update()
	{
		LOCK.lock();
		try
		{
			if (nextContainer.isOutOfDate())
			{
				nextContainer = new CacheContainer<KeyType, ResultType>(updatePeriod, true);
				container = new CacheContainer<KeyType, ResultType>(updatePeriod);
				return;
			}

			if (container.isOutOfDate())
		    {
			    container = nextContainer;
			    nextContainer = new CacheContainer<KeyType, ResultType>(updatePeriod, true);
		    }
		}
		finally {
			LOCK.unlock();
		}
	}
}
