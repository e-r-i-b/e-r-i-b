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
 * Настройки мониторинга сервиса внешней системы
 */

public class MonitoringGateServiceStateConfiguration
{
	protected final Object COUNTER_LOCKER = new Object();
	private final MonitoringGateServiceConfiguration serviceConfig;     //конфиг всего сервиса
	protected final MonitoringGateState monitoringState;                  //состояние которое мониторим
	protected final MonitoringServiceGateStateConfig serviceStateConfig;  //конфиг статуса сервиса
	private volatile Counter counter;

	/**
	 * конструктор
	 * @param serviceConfig конфиг всего сервиса
	 * @param monitoringState состояние
	 * @param serviceStateConfig конфиг мониторинга
	 */
	public MonitoringGateServiceStateConfiguration(MonitoringGateServiceConfiguration serviceConfig, MonitoringGateState monitoringState, MonitoringServiceGateStateConfig serviceStateConfig)
	{
		this.serviceConfig = serviceConfig;
		this.monitoringState = monitoringState;
		this.serviceStateConfig = serviceStateConfig;
	}

	/**
	 * @return таймаут сервиса для данного статуса
	 */
	public long getTimeout()
	{
		return serviceStateConfig.getTimeout();
	}

	/**
	 * @return максимальное увеличение счетчика сервиса для данного статуса
	 */
	public int getMaxCount()
	{
		return serviceStateConfig.getMonitoringCount();
	}

	/**
	 * @return максимальное увеличение счетчика сервиса для данного статуса
	 */
	public int getMaxPercent()
	{
		return serviceStateConfig.getMonitoringErrorPercent();
	}

	/**
	 * @return время, в рамках которого производится определение статуса сервиса
	 */
	public long getTime()
	{
		return serviceStateConfig.getMonitoringTime();
	}

	/**
	 * Сервис внешней системы переходит в статус, заданный в конструкторе
	 */
	public void reachState()
	{
		serviceConfig.reachState(monitoringState);
	}

	/**
	 * обновить конфигурацию мониторинга сервиса
	 * @param newState новый статус
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
	 * @return состояние которое мониторится
	 */
	public MonitoringGateState getMonitoringState()
	{
		return monitoringState;
	}

	/**
	 * @return работает ли еще мониторинг
	 * @throws GateException
	 */
	public boolean isUse() throws GateException
	{
		return serviceStateConfig.isUseMonitoring() && serviceConfig.getCurrentState().compare(monitoringState) < 0;
	}

	/**
	 * @return счетчик
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