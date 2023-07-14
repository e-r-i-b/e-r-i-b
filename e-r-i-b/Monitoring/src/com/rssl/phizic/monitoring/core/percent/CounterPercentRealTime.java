package com.rssl.phizic.monitoring.core.percent;

import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.LinkedList;

/**
 * —четчик мониторинга внешней системы в реальном времени
 * @author Jatsky
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */

public class CounterPercentRealTime extends CounterPercent
{
	private volatile LinkedList<Long> allAttempts;
	private volatile LinkedList<Long> badAttempts;

	/**
	 * конструктор
	 * @param configuration конфигураци€ мониторинга
	 */
	CounterPercentRealTime(MonitoringGateServiceStateConfigurationPercent configuration)
	{
		super(configuration);
	}

	/**
	 * увеличение счетчика
	 */
	public void inc(boolean isFail)
	{
		PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; completed = " + completed);
		if (completed)
			return;

		long currentTime = System.currentTimeMillis();
		synchronized (locker)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; synchronized completed = " + completed);
			if (completed)
				return;

			allAttempts.addLast(currentTime);
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; allAttempts.size() = " + allAttempts.size());
			if (isFail)
			{
				badAttempts.addLast(currentTime);
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; badAttempts.size() = " + badAttempts.size());
			}

			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; badAttempts removeUnnesessary");
			removeUnnesessary(currentTime, badAttempts);
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; badAttempts.size() = " + badAttempts.size());
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; allAttempts removeUnnesessary");
			removeUnnesessary(currentTime, allAttempts);
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; allAttempts.size() = " + allAttempts.size());

			double currentPercent = ((double) badAttempts.size() / (double) allAttempts.size()) * 100;
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; currentPercent = " + currentPercent + "; maxPercent = " + maxPercent);
			if (currentPercent >= maxPercent)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; complete start");
				complete();
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime] monitoringState = " + configuration.getMonitoringState() + "; complete end");
			}
		}
	}

	void refresh()
	{
		synchronized (locker)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime.refresh] monitoringState = " + configuration.getMonitoringState() + "; start");
			maxPercent = configuration.getMaxPercent();
			allAttempts = new LinkedList<Long>();
			badAttempts = new LinkedList<Long>();
			interval = configuration.getTime();
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).debug("[CounterPercentRealTime.refresh] monitoringState = " + configuration.getMonitoringState() + "; maxPercent = " + maxPercent + "; interval = " + interval);
		}
	}

	private void removeUnnesessary(long currentTime, LinkedList<Long> list)
	{
		if (!list.isEmpty())
		{
			while (currentTime - list.getFirst() > interval)
			{
				list.removeFirst();
			}
		}
	}
}
