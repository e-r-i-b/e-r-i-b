package com.rssl.phizic.utils.cache;

/**
 * ��������� ������ � ����.
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
	 * @return ���������� �� �������� ������ ����.
	 */
	boolean needRefresh(Long updatePeriod)
	{
		if (isUpdating)
			return false;

		return lastUpdateTime + updatePeriod < System.currentTimeMillis();
	}

	/**
	 * @return ����� ���������� ����������.
	 */
	long getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	/**
	 * ������������� ���� ������ ���������� ������ � ����.
	 */
    void startUpdating()
    {
        isUpdating = true;
    }

	/**
	 * @return �������������� ������.
	 */
	ReturnType getCachedValue()
	{
		return cachedValue;
	}
}
