package com.rssl.phizic.monitoring.core.percent;

import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * Счетчик мониторинга внешней системы по интервалам времени
 * @author Jatsky
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */

public class CounterPercentByInterval extends CounterPercent
{
	private volatile long allAttempts;
	private volatile long badAttempts;
	private volatile long lastCheck;

	/**
	 * конструктор
	 * @param configuration конфигурация мониторинга
	 */
	CounterPercentByInterval(MonitoringGateServiceStateConfigurationPercent configuration)
	{
		super(configuration);
	}

	public void inc(boolean isFail)
	{
		PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; completed = " + completed);
		if (completed)
			return;

		long currentTime = System.currentTimeMillis();
		synchronized (locker)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; synchronized completed = " + completed);
			if (completed)
				return;

			allAttempts++;
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; allAttempts = " + allAttempts);
			if (isFail)
			{
				badAttempts++;
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; badAttempts = " + badAttempts);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; currentTime = " + currentTime + "; lastCheck = " + lastCheck + "; interval = " + interval);
			if (currentTime - lastCheck > interval)
			{
				lastCheck = currentTime;
				if (allAttempts != 0)
				{
					double currentPercent = ((double) badAttempts / (double) allAttempts) * 100;
					PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; currentPercent = " + currentPercent + "; maxPercent = " + maxPercent);
					if (currentPercent >= maxPercent)
					{
						PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; complete start");
						complete();
						PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval] monitoringState = " + configuration.getMonitoringState() + "; complete end");
					}
				}
				refresh();
				return;
			}
		}
	}

	void refresh()
	{
		synchronized (locker)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval.refresh] monitoringState = " + configuration.getMonitoringState() + "start");
			maxPercent = configuration.getMaxPercent();
			allAttempts = 0;
			badAttempts = 0;
			interval = configuration.getTime();
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentByInterval.refresh] monitoringState = " + configuration.getMonitoringState() + "; maxPercent = " + maxPercent + "; interval = " + interval);
		}
	}
}
