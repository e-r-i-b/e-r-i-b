package com.rssl.phizic.monitoring.core.percent;

/**
 * Счетчик мониторинга внешней системы (проценты)
 * @author Jatsky
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */

public abstract class CounterPercent
{
	protected final Object locker = new Object();

	protected final MonitoringGateServiceStateConfigurationPercent configuration;

	protected volatile int maxPercent;

	protected volatile long interval;
	protected volatile boolean completed = false; //достиг ли состояния счетчик

	/**
	 * конструктор
	 * @param configuration конфигурация мониторинга
	 */
	CounterPercent(MonitoringGateServiceStateConfigurationPercent configuration)
	{
		this.configuration = configuration;
		refresh();
	}

	/**
	 * увеличение счетчика
	 */
	public abstract void inc(boolean isFail);

	abstract void refresh();

	void stopCounter()
	{
		completed = true;
	}

	void runCounter()
	{
		completed = false;
	}

	protected void complete()
	{
		completed = true;
		configuration.reachState();
	}
}
