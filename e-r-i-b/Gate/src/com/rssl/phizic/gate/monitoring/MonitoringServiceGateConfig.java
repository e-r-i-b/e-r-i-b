package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Настройки мониторинга сервиса
 */

public class MonitoringServiceGateConfig implements Serializable, DictionaryRecord
{
	private MonitoringServiceGateStateConfig degradationConfig;
	private MonitoringServiceGateStateConfig inaccessibleConfig;
	private MonitoringGateState state;
	private String serviceName;

	/**
	 * @return настройки мониторинга деградации
	 */
	public MonitoringServiceGateStateConfig getDegradationConfig()
	{
	    return degradationConfig;
	}

	/**
	 * задать настройки мониторинга деградации
	 * @param degradationConfig настройки мониторинга деградации
	 */
	public void setDegradationConfig(MonitoringServiceGateStateConfig degradationConfig)
	{
	    this.degradationConfig = degradationConfig;
	}

	/**
	 * @return настройки мониторинга недоступности
	 */
	public MonitoringServiceGateStateConfig getInaccessibleConfig()
	{
	    return inaccessibleConfig;
	}

	/**
	 * задать настройки мониторинга недоступности
	 * @param inaccessibleConfig настройки мониторинга недоступности
	 */
	public void setInaccessibleConfig(MonitoringServiceGateStateConfig inaccessibleConfig)
	{
	    this.inaccessibleConfig = inaccessibleConfig;
	}

	/**
	 * @return состояние мониторинга
	 */
	public MonitoringGateState getState()
	{
	    return state;
	}

	/**
	 * задать состояние мониторинга
	 * @param state состояние мониторинга
	 */
	public void setState(MonitoringGateState state)
	{
		this.state = state;
	}

	/**
	 * задать состояние мониторинга
	 * @param state состояние мониторинга
	 */
	public void setState(String state)
	{
	    this.state = MonitoringGateState.valueOf(state);
	}

	/**
	 * @return имя сервиса
	 */
	public String getServiceName()
	{
		return serviceName;
	}

	/**
	 * задать имя сервиса
	 * @param serviceName имя сервиса
	 */
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	/**
	 * обновить конфиг
	 * полное копирование параметров из источника в текущую сущность
	 * @param sourceConfig источник обновления
	 */
	public void update(MonitoringServiceGateConfig sourceConfig)
	{
		setServiceName(sourceConfig.getServiceName());
		setState(sourceConfig.getState());
		getDegradationConfig().update(sourceConfig.getDegradationConfig());
		getInaccessibleConfig().update(sourceConfig.getInaccessibleConfig());
	}

	/**
	 * доступен ли статус сервису
	 * @param state проверяемый статус
	 * @return true - доступен, false - недоступен
	 */
	public boolean isAvailableState(MonitoringGateState state)
	{
		MonitoringServiceGateStateConfig stateConfig = getStateConfig(state);
		//Если нет настроек статуса, то в него нельзя переходить.
		return stateConfig != null && stateConfig.isAvailable();
	}

	public Comparable getSynchKey()
	{
		return serviceName;
	}

	/**
	 * обновить конфиг
	 * 1. параметры копируются только из инстансов текущего класса
	 * 2. копируем только настройки состояний методом
	 *      com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig#updateFrom(com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig)
	 *      (со своей логикой, тупое копирование не приемлемо)
	 * @param sourceConfig источник обновления
	 */
	public void updateFrom(DictionaryRecord sourceConfig)
	{
		if (!(sourceConfig instanceof MonitoringServiceGateConfig))
			return;

		MonitoringServiceGateConfig configSource = (MonitoringServiceGateConfig) sourceConfig;
		getDegradationConfig().updateFrom(configSource.getDegradationConfig());
		getInaccessibleConfig().updateFrom(configSource.getInaccessibleConfig());
	}

	/**
	 * Возвращаем настройки мониторинга статуса сервиса
	 * @param state статус
	 * @return настройки мониторинга
	 */
	public MonitoringServiceGateStateConfig getStateConfig(MonitoringGateState state)
	{
		switch (state)
		{
			case DEGRADATION: return getDegradationConfig();
			case INACCESSIBLE: return getInaccessibleConfig();
			default: return null;
		}
	}
}
