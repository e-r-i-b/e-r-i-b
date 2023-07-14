package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * Параметры запуска состояний сервисов
 */

public class MonitoringStateParameters
{
	private MonitoringGateState monitoringState;    //статус, к ктоторому относятся настройки
	private Integer timeout;                        //значение таймаута
	private Boolean useMonitoring;                  //использовать ли мониторинг

	public MonitoringGateState getMonitoringState()
	{
		return monitoringState;
	}

	public void setMonitoringState(MonitoringGateState monitoringState)
	{
		this.monitoringState = monitoringState;
	}

	public void setMonitoringState(String monitoringState)
	{
		if (StringHelper.isNotEmpty(monitoringState))
			this.monitoringState = MonitoringGateState.valueOf(monitoringState);
	}

	public Integer getTimeout()
	{
		return timeout;
	}

	public void setTimeout(Integer timeout)
	{
		this.timeout = timeout;
	}

	public Boolean getUseMonitoring()
	{
		return useMonitoring;
	}

	public void setUseMonitoring(Boolean useMonitoring)
	{
		this.useMonitoring = useMonitoring;
	}
}
