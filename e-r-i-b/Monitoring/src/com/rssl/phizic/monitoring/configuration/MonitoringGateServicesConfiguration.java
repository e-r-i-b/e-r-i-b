package com.rssl.phizic.monitoring.configuration;

import com.rssl.phizic.monitoring.core.MonitoringGateServiceConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author akrenev
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 * ����� ��������� �������� ����������� �������� �����
 */

public class MonitoringGateServicesConfiguration
{
	private final Object locker = new Object();
	private final Map<String, MonitoringGateServiceConfiguration> configs = new ConcurrentHashMap<String, MonitoringGateServiceConfiguration>();
	private static final MonitoringGateServicesConfiguration instance = new  MonitoringGateServicesConfiguration();

	private MonitoringGateServicesConfiguration(){}

	/**
	 * �����������
	 * @return ��������� ����������� �������� �����
	 */
	public static MonitoringGateServicesConfiguration getInstance()
	{
		return instance;
	}

	/**
	 * @param service ������
	 * @return ��������� ����������� ������� �����
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