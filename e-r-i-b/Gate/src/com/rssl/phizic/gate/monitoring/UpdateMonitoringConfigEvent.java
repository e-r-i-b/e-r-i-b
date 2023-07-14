package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.events.Event;

/**
 * @author akrenev
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * Событие обновления настроек мониторинга
 */

public class UpdateMonitoringConfigEvent implements Event
{
	private MonitoringServiceGateConfig config;

	/**
	 * конструктор
	 * @param config настрйки мониторинга
	 */
	public UpdateMonitoringConfigEvent(MonitoringServiceGateConfig config)
	{
		this.config = config;
	}

	/**
	 * @return настрйки мониторинга
	 */
	public MonitoringServiceGateConfig getConfig()
	{
		return config;
	}

	/**
	 * задать настрйки мониторинга
	 * @param config настрйки мониторинга
	 */
	public void setConfig(MonitoringServiceGateConfig config)
	{
		this.config = config;
	}

	public String getStringForLog()
	{
		StringBuilder stringForLog = new StringBuilder();
		return stringForLog.append("UpdateMonitoringConfigEvent, обновляемый сервис ").append(config.getServiceName()).append(", статус мониторинга ").append(config.getState()).toString();  
	}
}
