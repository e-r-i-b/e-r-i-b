package com.rssl.phizic.gate.config.monitoring;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Параметры для доступа к приложению мониторинга
 */

public class MonitoringServicesConfiguration extends MonitoringServicesConfigurationMBean
{
	private static final String DATA_KEY = "com.rssl.phizic.gate.monitoring.listener.url";

	private String monitoringAppUrl;

	/**
	 * конструктор.
	 * инициализируем поля
	 */
	public MonitoringServicesConfiguration(PropertyReader reader)
	{
		super(reader);
	}

	public String getMonitoringAppUrl()
	{
		return monitoringAppUrl;
	}

	public void doRefresh() throws ConfigurationException
	{
		monitoringAppUrl = getProperty(DATA_KEY);
	}

	public void printStartMessage()
	{
		System.out.println("JMXBean "+ MonitoringServicesConfiguration.class.getCanonicalName() + " успешно запущен");
	}
}
