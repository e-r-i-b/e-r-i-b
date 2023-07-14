package com.rssl.phizgate.mobilebank.cache.techbreak;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Запись кеша в БД для тех. перерывов МБК
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class MbkCacheEntry implements Serializable
{
	private Calendar requestTime = Calendar.getInstance();

	/**
	 * @return строковый ключ кеша
	 */
	abstract String getCacheKey();

	/**
	 * @return наименование кеша СП
	 */
	abstract String getAppServerCacheName();

	public Calendar getRequestTime()
	{
		return requestTime;
	}

	public void setRequestTime(Calendar requestTime)
	{
		this.requestTime = requestTime;
	}
}
