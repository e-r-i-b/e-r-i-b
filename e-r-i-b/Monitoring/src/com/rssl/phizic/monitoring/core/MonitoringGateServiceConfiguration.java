package com.rssl.phizic.monitoring.core;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.monitoring.*;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.monitoring.core.percent.MonitoringGateServiceStateConfigurationPercent;

/**
 * @author akrenev
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Класс настройки сервиса, состоит из настроек недоступности и настроек деградации
 */

public class MonitoringGateServiceConfiguration
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final Object SERVICE_INACCESSIBLE_LOADER_LOCKER = new Object();
	private volatile MonitoringGateServiceStateConfiguration inaccessibleConfig;

	private final Object SERVICE_DEGRADATION_LOADER_LOCKER = new Object();
	private volatile MonitoringGateServiceStateConfiguration degradationConfig;
	
	private final Object SERVICE_INACCESSIBLE_PERCENT_LOADER_LOCKER = new Object();
	private volatile MonitoringGateServiceStateConfigurationPercent inaccessibleConfigPercent;

	private final Object SERVICE_DEGRADATION_PERCENT_LOADER_LOCKER = new Object();
	private volatile MonitoringGateServiceStateConfigurationPercent degradationConfigPercent;

	private final String serviceName;

	/**
	 * конструктор
	 * @param serviceName сервис
	 */
	public MonitoringGateServiceConfiguration(String serviceName)
	{
		this.serviceName = serviceName;
	}

	MonitoringGateState getCurrentState() throws GateException
	{
		return getServiceConfig().getState();
	}

	private MonitoringGateServicesContext getMonitoringGateServicesContext()
	{
		return MonitoringGateServicesContext.getInstance();
	}

	private MonitoringServiceGateConfig getServiceConfig() throws GateException
	{
		return getMonitoringGateServicesContext().getConfig(serviceName);
	}

	private MonitoringGateServiceStateConfiguration getInaccessibleConfig(MonitoringServiceGateConfig serviceGateConfig)
	{
		if (inaccessibleConfig != null)
			return inaccessibleConfig;

		synchronized (SERVICE_INACCESSIBLE_LOADER_LOCKER)
		{
			if (inaccessibleConfig != null)
				return inaccessibleConfig;

			inaccessibleConfig = new MonitoringGateServiceStateConfiguration(this, MonitoringGateState.INACCESSIBLE, serviceGateConfig.getInaccessibleConfig());
		}
		return inaccessibleConfig;
	}

	private MonitoringGateServiceStateConfiguration getDegradationConfig(MonitoringServiceGateConfig serviceGateConfig)
	{
		if (degradationConfig != null)
			return degradationConfig;

		synchronized (SERVICE_DEGRADATION_LOADER_LOCKER)
		{
			if (degradationConfig != null)
				return degradationConfig;

			degradationConfig = new MonitoringGateServiceStateConfiguration(this, MonitoringGateState.DEGRADATION, serviceGateConfig.getDegradationConfig());
		}
		return degradationConfig;
	}
	
	private MonitoringGateServiceStateConfigurationPercent getInaccessibleConfigPercent(MonitoringServiceGateConfig serviceGateConfig)
	{
		if (inaccessibleConfigPercent != null)
			return inaccessibleConfigPercent;

		synchronized (SERVICE_INACCESSIBLE_PERCENT_LOADER_LOCKER)
		{
			if (inaccessibleConfigPercent != null)
				return inaccessibleConfigPercent;

			inaccessibleConfigPercent = new MonitoringGateServiceStateConfigurationPercent(this, MonitoringGateState.INACCESSIBLE, serviceGateConfig.getInaccessibleConfig());
		}
		return inaccessibleConfigPercent;
	}

	private MonitoringGateServiceStateConfigurationPercent getDegradationConfigPercent(MonitoringServiceGateConfig serviceGateConfig)
	{
		if (degradationConfigPercent != null)
			return degradationConfigPercent;

		synchronized (SERVICE_DEGRADATION_PERCENT_LOADER_LOCKER)
		{
			if (degradationConfigPercent != null)
				return degradationConfigPercent;

			degradationConfigPercent = new MonitoringGateServiceStateConfigurationPercent(this, MonitoringGateState.DEGRADATION, serviceGateConfig.getDegradationConfig());
		}
		return degradationConfigPercent;
	}

	/**
	 * @return настройки недоступности
	 */
	public MonitoringGateServiceStateConfiguration getInaccessibleConfig() throws GateException
	{
		return getInaccessibleConfig(getServiceConfig());
	}

	/**
	 * @return настройки деградации
	 */
	public MonitoringGateServiceStateConfiguration getDegradationConfig() throws GateException
	{
		return getDegradationConfig(getServiceConfig());
	}
	
	/**
	 * @return настройки недоступности для процентного алгоритма
	 */
	public MonitoringGateServiceStateConfiguration getInaccessibleConfigPercent() throws GateException
	{
		return getInaccessibleConfigPercent(getServiceConfig());
	}

	/**
	 * @return настройки деградации для процентного алгоритма
	 */
	public MonitoringGateServiceStateConfiguration getDegradationConfigPercent() throws GateException
	{
		return getDegradationConfigPercent(getServiceConfig());
	}

	/**
	 * Сервис внешней системы переходит в статус
	 * @param state статус
	 */
	void reachState(MonitoringGateState state)
	{
		BackRefMonitoringGateConfigService service = GateSingleton.getFactory().service(BackRefMonitoringGateConfigService.class);
		try
		{
			getServiceConfig().setState(state);
			service.setState(serviceName, state);
		}
		catch (Exception e)
		{
			log.error("Сервис " + serviceName + " находится в состоянии " + state + ", но основное приложение не оповещено.", e);
		}
	}

	/**
	 * обновить настройки мониторинга
	 * @param config новые настройки
	 */
	public void update(MonitoringServiceGateConfig config) throws GateException
	{
		getInaccessibleConfig(config).update(config.getState());
		getDegradationConfig(config).update(config.getState());
		getInaccessibleConfigPercent(config).update(config.getState());
		getDegradationConfigPercent(config).update(config.getState());
	}
}