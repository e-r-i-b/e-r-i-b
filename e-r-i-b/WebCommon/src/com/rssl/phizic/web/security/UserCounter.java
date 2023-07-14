package com.rssl.phizic.web.security;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mihaylov
 * @ created 21.02.2011
 * @ $Author$
 * @ $Revision$
 */
//класс для хранения кол-ва сессий клиентов
public class UserCounter
{	
	private final Map<Long,AtomicLong> counterByTB = new HashMap<Long,AtomicLong>(); // текущее кол-во сессий пользователей для заданного тербанка
	private final Object COUNTER_LOCKER = new Object();

	private AtomicLong getCounter(Long terBank)
	{
		AtomicLong terBankCounter = counterByTB.get(terBank);
		if(terBankCounter == null)
		{
			synchronized (COUNTER_LOCKER)
			{
				terBankCounter = counterByTB.get(terBank);
				if(terBankCounter == null)
				{
					terBankCounter = new AtomicLong(0);
					counterByTB.put(terBank,terBankCounter);
				}
			}
		}
		return terBankCounter;
	}

	/**
	 * Уменьшить счетчики
	 * @param terBank - номер тербанка
	 * @return текущее значение общего числа пользователей
	 */
	public long decrementAndGet(Long terBank)
	{
		getCounter(terBank).decrementAndGet();
		return getCurrentCount();
	}

	/**
	 * Увеличить счетчики
	 * @param terBank - номер тербанка
	 * @return текущее значение общего числа пользователей
	 */
	public long incrementAndGet(Long terBank)
	{
		getCounter(terBank).incrementAndGet();
		return getCurrentCount();
	}

	private long getCurrentCount()
	{
		long count = 0;
		for(Map.Entry<Long, AtomicLong> entry : counterByTB.entrySet())
			count += entry.getValue().get();

		return count;
	}

	/**
	 * @return счетчики по тербанкам
	 */
	public Map<Long, AtomicLong> getCounterByTB()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return counterByTB;
	}
}
