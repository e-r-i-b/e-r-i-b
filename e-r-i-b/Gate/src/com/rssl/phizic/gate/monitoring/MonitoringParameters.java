package com.rssl.phizic.gate.monitoring;

import java.util.List;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * Параметры запуска сервисов
 */

public class MonitoringParameters
{
	private List<MonitoringStateParameters> allStateConfig; //Параметры для статусов
	private String serviceName;                             //имя сервиса

	public List<MonitoringStateParameters> getAllStateConfig()
	{
		return allStateConfig;
	}

	public void setAllStateConfig(List<MonitoringStateParameters> allStateConfig)
	{
		this.allStateConfig = allStateConfig;
	}

	public String getServiceName()
	{
		return serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}
}
