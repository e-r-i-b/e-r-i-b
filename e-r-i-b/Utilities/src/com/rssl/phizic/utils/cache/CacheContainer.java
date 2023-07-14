package com.rssl.phizic.utils.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ��������� ��� ����
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
		//� ���������� �������� ������ �� 4� �������� ����������.
		this.outOfDateTime = System.currentTimeMillis() + (nextPeriod ? 8 : 4)*updatePeriod;
	}

	/**
	 * �������� �������� �� ����.
	 *
	 * @param key ����.
	 */
	synchronized void removeElement(KeyType key)
	{
		cache.remove(key);
		synchronizer.remove(key);
	}

	/**
	 * ������� ����.
	 */
	synchronized void clear()
	{
		cache.clear();
		synchronizer.clear();
	}

	/**
	 * @param cacheKey ���� ����.
	 * @return ���������� �� ������� � ��������� ������ � ����.
	 */
	boolean containsKey(KeyType cacheKey)
	{
		return cache.containsKey(cacheKey);
	}

	/**
	 * @param cacheKey ���� ����.
	 * @return ������ ��� �������������.
	 */
	Object getSynchronizer(KeyType cacheKey)
	{
		return synchronizer.get(cacheKey);
	}

	/**
	 * @param cacheKey ���� ����.
	 * @param cacheElement ��������� �� �����.
	 * @return ������ �� �� �������� �������.
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
	 * @return ��������� �������.
	 */
	boolean isOutOfDate()
	{
		return outOfDateTime < System.currentTimeMillis();
	}
}
