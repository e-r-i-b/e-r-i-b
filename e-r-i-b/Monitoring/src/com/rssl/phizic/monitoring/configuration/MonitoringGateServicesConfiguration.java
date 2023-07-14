package com.rssl.phizic.monitoring.configuration;

import com.rssl.phizic.monitoring.core.MonitoringGateServiceConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author akrenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 * Класс получения настроек могиторинга сервисов гейта
 */

public class MonitoringGateServicesConfiguration
{
	private final Object locker = new Object();
	private final Map<String, MonitoringGateServiceConfiguration> configs = new ConcurrentHashMap<String, MonitoringGateServiceConfiguration>();
	private static final MonitoringGateServicesConfiguration instance = new  MonitoringGateServicesConfiguration();

	private MonitoringGateServicesConfiguration(){}

	/**
	 * конструктор
	 * @return настройки могиторинга сервисов гейта
	 */
	public static MonitoringGateServicesConfiguration getInstance()
	{
		return instance;
	}

	/**
	 * @param service сервис
	 * @return настройки могиторинга сервиса гейта
	 */
	public MonitoringGateServiceConfiguration getConfiguration(String service)
	{
		MonitoringGateServiceConfiguration configuration = configs.get(service);
		if (configuration != null)
			return configuration;

		synchronized (locker)
		{
			configuration = configs.get(service);
			if (configuration != null)
				return configuration;
			configuration = new MonitoringGateServiceConfiguration(service);
			configs.put(service, configuration);
		}
		return configuration;
	}
}