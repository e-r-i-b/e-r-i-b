package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.events.Event;

/**
 * @author akrenev
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� ���������� �������� �����������
 */

public class UpdateMonitoringConfigEvent implements Event
{
	private MonitoringServiceGateConfig config;

	/**
	 * �����������
	 * @param config �������� �����������
	 */
	public UpdateMonitoringConfigEvent(MonitoringServiceGateConfig config)
	{
		this.config = config;
	}

	/**
	 * @return �������� �����������
	 */
	public MonitoringServiceGateConfig getConfig()
	{
		return config;
	}

	/**
	 * ������ �������� �����������
	 * @param config �������� �����������
	 */
	public void setConfig(MonitoringServiceGateConfig config)
	{
		this.config = config;
	}

	public String getStringForLog()
	{
		StringBuilder stringForLog = new StringBuilder();
		return stringForLog.append("UpdateMonitoringConfigEvent, ����������� ������ ").append(config.getServiceName()).append(", ������ ����������� ").append(config.getState()).toString();  
	}
}
