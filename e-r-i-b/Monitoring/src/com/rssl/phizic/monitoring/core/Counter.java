package com.rssl.phizic.monitoring.core;

/**
 * @author akrenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Счетчик мониторинга внешней системы
 */

public class Counter
{
	private final Object locker = new Object();

	private final MonitoringGateServiceStateConfiguration configuration;

	private volatile long[] queue;
	private volatile int currentIndex;

	private volatile long interval;
	private volatile boolean completed = false; //достиг ли состояния счетчик

	/**
	 * конструктор
	 * @param configuration конфигурация мониторинга
	 */
	Counter(MonitoringGateServiceStateConfiguration configuration)
	{
		this.configuration = configuration;
		refresh();
	}

	/**
	 * увеличение счетчика
	 */
	public void inc()
	{
		if (completed)
			return;

		long currentTime = System.currentTimeMillis();
		synchronized (locker)
		{
			if (completed)
				return;

			long[] localQueue = queue;
			localQueue[currentIndex] = currentTime;
			currentIndex = ++currentIndex % localQueue.length;

			if (localQueue[currentIndex] == 0)
				return;

			if (currentTime - localQueue[currentIndex] > interval)
				return;

			complete();
		}
	}

	void refresh()
	{
		synchronized (locker)
		{
			currentIndex = 0;
			queue = new long[configuration.getMaxCount()];
			interval = configuration.getTime();
		}
	}

	void stopCounter()
	{
		completed = true;
	}

	void runCounter()
	{
		completed = false;
	}

	private void complete()
	{
		completed = true;
		configuration.reachState();
	}
}