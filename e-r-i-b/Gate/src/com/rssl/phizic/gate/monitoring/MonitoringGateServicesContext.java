package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author akrenev
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� (���������) �����������
 */

public final class MonitoringGateServicesContext
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final MonitoringGateServicesContext instance = new MonitoringGateServicesContext();

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	private final Map<String, ValueHolder> configs = new HashMap<String, ValueHolder>();

	private MonitoringGateServicesContext(){}

	/**
	 * @return �������
	 */
	public static MonitoringGateServicesContext getInstance()
	{
		return instance;
	}

	private MonitoringServiceGateConfig loadConfig(String serviceName) throws GateException
	{
		BackRefMonitoringGateConfigService service = GateSingleton.getFactory().service(BackRefMonitoringGateConfigService.class);
		try
		{
			return service.getMonitoringGateConfig(serviceName);
		}
		catch (Exception e)
		{
			log.error("������ ��������� �������� ����������� ��� ������� " + serviceName, e);
			throw new GateException(e);
		}
	}

	/**
	 * @param serviceName �������� �������
	 * @return ������� ��������� ��� ������� serviceName
	 * @throws GateException
	 */
	public MonitoringServiceGateConfig getConfig(String serviceName) throws GateException
	{
		readLock.lock();
		try
		{
			ValueHolder holder = configs.get(serviceName);
			if (holder != null)
				return holder.getValue();
		}
		finally
		{
			readLock.unlock();
		}
		writeLock.lock();
		try
		{
			ValueHolder holder = configs.get(serviceName);
			if (holder != null)
				return holder.getValue();

			//noinspection ReuseOfLocalVariable
			MonitoringServiceGateConfig config = loadConfig(serviceName);
			configs.put(serviceName, new ValueHolder(config));
			return config;
		}
		finally
		{
			writeLock.unlock();
		}
	}

	/**
	 * �������� ������
	 * @param config ������-��������
	 */
	public void updateConfig(MonitoringServiceGateConfig config)
	{
		writeLock.lock();
		try
		{
			ValueHolder holder = configs.get(config.getServiceName());
			if (holder == null)
				configs.put(config.getServiceName(), new ValueHolder(config));
			else
				holder.update(config);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	
	/**
	 * ������������ ������ � com.rssl.phizic.web.gate.services.monitoring.RunGateMonitoringServiceImpl
	 * ��������� ���������� �������
	 * @param parameters ������-��������
	 */
	public void updateConfig(MonitoringParameters parameters)
	{
		writeLock.lock();
		try
		{
			// ���� ������ �������� �������, �� �������� ���
			if (StringHelper.isNotEmpty(parameters.getServiceName()))
				updateServriceConfig(parameters.getServiceName(), parameters.getAllStateConfig());
			else
				updateAllConfigs(parameters);
		}
		finally
		{
			writeLock.unlock();
		}
	}

	private void updateAllConfigs(MonitoringParameters parameters)
	{
		// ���� �� ������ �������� �������, �������� ��� ���������
		for (String serviceName : configs.keySet())
			updateServriceConfig(serviceName, parameters.getAllStateConfig());
	}

	// ������������ ������ � updateConfig(MonitoringParameters parameters)
	private void updateServriceConfig(String serviceName, List<MonitoringStateParameters> statesParameters)
	{
		ValueHolder holder = configs.get(serviceName);
		//������ �� ��� ��������: �� ������, ������ ���� ��� (������� ����������� ������ �� �����) -- ����������
		if (holder == null)
			return;

		MonitoringServiceGateConfig config = holder.getValue();
		//������ ��� ��������, �� ������, ������ ���� ��� (������� ����������� ������ �� �����) -- �������
		if (config == null)
		{
			configs.remove(serviceName);
			return;
		}

		config.setState(MonitoringGateState.NORMAL);
		//����� �� ������ ��������
		for (MonitoringStateParameters parameters : statesParameters)
		{
			MonitoringGateState state = parameters.getMonitoringState();
			//���� ������ �� ������, ������ ��� ����
			//���� ������ ������, ������ ��������� ������ ���
			if (state == null || state == MonitoringGateState.DEGRADATION)
				updateServriceStateConfig(config.getDegradationConfig(), parameters);
			//���� ������ �� ������, ������ ��� ����
			//���� ������ ������, ������ ��������� ������ ���
			if (state == null || state == MonitoringGateState.INACCESSIBLE)
				updateServriceStateConfig(config.getInaccessibleConfig(), parameters);
		}
	}

	// ������������ ������ � updateServriceConfig(MonitoringServiceGateConfig config, List<MonitoringStateParameters> statesParameters)
	private void updateServriceStateConfig(MonitoringServiceGateStateConfig config, MonitoringStateParameters parameters)
	{
		// ��������� ������ ��������� ������
		if (parameters.getUseMonitoring() != null)
			config.setUseMonitoring(parameters.getUseMonitoring());
		if (parameters.getTimeout() != null)
			config.setTimeout(parameters.getTimeout());
	}

	/**
	 * �����-������� ��� �������� �����������
	 * ����� ��� ���������� ������ � ������
	 */
	private class ValueHolder
	{
		private MonitoringServiceGateConfig value;

		private ValueHolder(MonitoringServiceGateConfig value)
		{
			this.value = value;
		}

		public MonitoringServiceGateConfig getValue()
		{
			return value;
		}

		public void update(MonitoringServiceGateConfig config)
		{
			if (value == null)
				value = config;
			else
				value.update(config);

		}
	}
}
