package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� �������
 */

public class MonitoringServiceGateConfig implements Serializable, DictionaryRecord
{
	private MonitoringServiceGateStateConfig degradationConfig;
	private MonitoringServiceGateStateConfig inaccessibleConfig;
	private MonitoringGateState state;
	private String serviceName;

	/**
	 * @return ��������� ����������� ����������
	 */
	public MonitoringServiceGateStateConfig getDegradationConfig()
	{
	    return degradationConfig;
	}

	/**
	 * ������ ��������� ����������� ����������
	 * @param degradationConfig ��������� ����������� ����������
	 */
	public void setDegradationConfig(MonitoringServiceGateStateConfig degradationConfig)
	{
	    this.degradationConfig = degradationConfig;
	}

	/**
	 * @return ��������� ����������� �������������
	 */
	public MonitoringServiceGateStateConfig getInaccessibleConfig()
	{
	    return inaccessibleConfig;
	}

	/**
	 * ������ ��������� ����������� �������������
	 * @param inaccessibleConfig ��������� ����������� �������������
	 */
	public void setInaccessibleConfig(MonitoringServiceGateStateConfig inaccessibleConfig)
	{
	    this.inaccessibleConfig = inaccessibleConfig;
	}

	/**
	 * @return ��������� �����������
	 */
	public MonitoringGateState getState()
	{
	    return state;
	}

	/**
	 * ������ ��������� �����������
	 * @param state ��������� �����������
	 */
	public void setState(MonitoringGateState state)
	{
		this.state = state;
	}

	/**
	 * ������ ��������� �����������
	 * @param state ��������� �����������
	 */
	public void setState(String state)
	{
	    this.state = MonitoringGateState.valueOf(state);
	}

	/**
	 * @return ��� �������
	 */
	public String getServiceName()
	{
		return serviceName;
	}

	/**
	 * ������ ��� �������
	 * @param serviceName ��� �������
	 */
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	/**
	 * �������� ������
	 * ������ ����������� ���������� �� ��������� � ������� ��������
	 * @param sourceConfig �������� ����������
	 */
	public void update(MonitoringServiceGateConfig sourceConfig)
	{
		setServiceName(sourceConfig.getServiceName());
		setState(sourceConfig.getState());
		getDegradationConfig().update(sourceConfig.getDegradationConfig());
		getInaccessibleConfig().update(sourceConfig.getInaccessibleConfig());
	}

	/**
	 * �������� �� ������ �������
	 * @param state ����������� ������
	 * @return true - ��������, false - ����������
	 */
	public boolean isAvailableState(MonitoringGateState state)
	{
		MonitoringServiceGateStateConfig stateConfig = getStateConfig(state);
		//���� ��� �������� �������, �� � ���� ������ ����������.
		return stateConfig != null && stateConfig.isAvailable();
	}

	public Comparable getSynchKey()
	{
		return serviceName;
	}

	/**
	 * �������� ������
	 * 1. ��������� ���������� ������ �� ��������� �������� ������
	 * 2. �������� ������ ��������� ��������� �������
	 *      com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig#updateFrom(com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig)
	 *      (�� ����� �������, ����� ����������� �� ���������)
	 * @param sourceConfig �������� ����������
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
	 * ���������� ��������� ����������� ������� �������
	 * @param state ������
	 * @return ��������� �����������
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
