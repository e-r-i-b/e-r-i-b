package com.rssl.phizic.monitoring.core.percent;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.MonitoringConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import com.rssl.phizic.monitoring.core.MonitoringGateServiceConfiguration;
import com.rssl.phizic.monitoring.core.MonitoringGateServiceStateConfiguration;

/**
 *  ��������� ����������� ������� ������� ������� ��� ����������� ��������
 * @author Jatsky
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringGateServiceStateConfigurationPercent extends MonitoringGateServiceStateConfiguration
{
	private volatile CounterPercent counterPercentRealTime;
	private volatile CounterPercent counterPercentInterval;

	/**
	 * �����������
	 * @param serviceConfig ������ ����� �������
	 * @param monitoringState ���������
	 * @param serviceStateConfig ������ �����������
	 */
	public MonitoringGateServiceStateConfigurationPercent(MonitoringGateServiceConfiguration serviceConfig, MonitoringGateState monitoringState, MonitoringServiceGateStateConfig serviceStateConfig)
	{
		super(serviceConfig, monitoringState, serviceStateConfig);
	}

	/**
	 * �������� ������������ ����������� �������
	 * @param newState ����� ������
	 */
	public void update(MonitoringGateState newState) throws GateException
	{
		CounterPercent localCounter = getCounterPercent();
		localCounter.refresh();

		if (!serviceStateConfig.isUseMonitoring() || newState.compare(monitoringState) >= 0)
			localCounter.stopCounter();
		else
			localCounter.runCounter();
	}

	public CounterPercent getCounterPercent() throws GateException
	{
		MonitoringConfig config = ConfigFactory.getConfig(MonitoringConfig.class);
		return config.isUseRealTimeCounter() ? getCounterPercentRealTime() : getCounterPercentInterval();
	}

	/**
	 * @return ������� � �������� �������
	 */
	private CounterPercent getCounterPercentRealTime() throws GateException
	{
		if (counterPercentRealTime != null)
			return counterPercentRealTime;

		synchronized (COUNTER_LOCKER)
		{
			if (counterPercentRealTime != null)
				return counterPercentRealTime;

			counterPercentRealTime = new CounterPercentRealTime(this);
		}
		return counterPercentRealTime;
	}

	/**
	 * @return ������� �� ���������� �������
	 */
	private CounterPercent getCounterPercentInterval() throws GateException
	{
		if (counterPercentInterval != null)
			return counterPercentInterval;

		synchronized (COUNTER_LOCKER)
		{
			if (counterPercentInterval != null)
				return counterPercentInterval;

			counterPercentInterval = new CounterPercentByInterval(this);
		}
		return counterPercentInterval;
	}
}
