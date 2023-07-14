package com.rssl.phizgate.mobilebank.cache.techbreak;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ������ ���� � �� ��� ���. ��������� ���
 * @author Puzikov
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class MbkCacheEntry implements Serializable
{
	private Calendar requestTime = Calendar.getInstance();

	/**
	 * @return ��������� ���� ����
	 */
	abstract String getCacheKey();

	/**
	 * @return ������������ ���� ��
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
