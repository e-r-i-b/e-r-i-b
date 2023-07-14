package com.rssl.phizic.utils.cache;

/**
 * Хранитель данных в кеше.
 *
 * @author bogdanov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

class CacheElement<ReturnType>
{
	private final long lastUpdateTime;
	private volatile boolean isUpdating = false;
	private final ReturnType cachedValue;

	CacheElement(ReturnType cachedValue)
	{
		this.cachedValue = cachedValue;
		lastUpdateTime = System.currentTimeMillis();
	}

	/**
	 * @return необходимо ли обновить запись кеша.
	 */
	boolean needRefresh(Long updatePeriod)
	{
		if (isUpdating)
			return false;

		return lastUpdateTime + updatePeriod < System.currentTimeMillis();
	}

	/**
	 * @return время последнего обновления.
	 */
	long getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	/**
	 * Устанавливает флаг начала обновления данных в кеше.
	 */
    void startUpdating()
    {
        isUpdating = true;
    }

	/**
	 * @return закешированные данные.
	 */
	ReturnType getCachedValue()
	{
		return cachedValue;
	}
}
