package com.rssl.phizic.monitoring.core;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;

/**
 * @author akrenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� ������� ������� �������
 */

public class MonitoringGateServiceStateConfiguration
{
	protected final Object COUNTER_LOCKER = new Object();
	private final MonitoringGateServiceConfiguration serviceConfig;     //������ ����� �������
	protected final MonitoringGateState monitoringState;                  //��������� ������� ���������
	protected final MonitoringServiceGateStateConfig serviceStateConfig;  //������ ������� �������
	private volatile Counter counter;

	/**
	 * �����������
	 * @param serviceConfig ������ ����� �������
	 * @param monitoringState ���������
	 * @param serviceStateConfig ������ �����������
	 */
	public MonitoringGateServiceStateConfiguration(MonitoringGateServiceConfiguration serviceConfig, MonitoringGateState monitoringState, MonitoringServiceGateStateConfig serviceStateConfig)
	{
		this.serviceConfig = serviceConfig;
		this.monitoringState = monitoringState;
		this.serviceStateConfig = serviceStateConfig;
	}

	/**
	 * @return ������� ������� ��� ������� �������
	 */
	public long getTimeout()
	{
		return serviceStateConfig.getTimeout();
	}

	/**
	 * @return ������������ ���������� �������� ������� ��� ������� �������
	 */
	public int getMaxCount()
	{
		return serviceStateConfig.getMonitoringCount();
	}

	/**
	 * @return ������������ ���������� �������� ������� ��� ������� �������
	 */
	public int getMaxPercent()
	{
		return serviceStateConfig.getMonitoringErrorPercent();
	}

	/**
	 * @return �����, � ������ �������� ������������ ����������� ������� �������
	 */
	public long getTime()
	{
		return serviceStateConfig.getMonitoringTime();
	}

	/**
	 * ������ ������� ������� ��������� � ������, �������� � ������������
	 */
	public void reachState()
	{
		serviceConfig.reachState(monitoringState);
	}

	/**
	 * �������� ������������ ����������� �������
	 * @param newState ����� ������
	 */
	void update(MonitoringGateState newState) throws GateException
	{
		Counter localCounter = getCounter();
		localCounter.refresh();

		if (!serviceStateConfig.isUseMonitoring() || newState.compare(monitoringState) >= 0)
			localCounter.stopCounter();
		else
			localCounter.runCounter();
	}

	/**
	 * @return ��������� ������� �����������
	 */
	public MonitoringGateState getMonitoringState()
	{
		return monitoringState;
	}

	/**
	 * @return �������� �� ��� ����������
	 * @throws GateException
	 */
	public boolean isUse() throws GateException
	{
		return serviceStateConfig.isUseMonitoring() && serviceConfig.getCurrentState().compare(monitoringState) < 0;
	}

	/**
	 * @return �������
	 */
	public Counter getCounter() throws GateException
	{
		if (counter != null)
			return counter;

		synchronized (COUNTER_LOCKER)
		{
			if (counter != null)
				return counter;

			counter = new Counter(this);
		}
		return counter;
	}
}