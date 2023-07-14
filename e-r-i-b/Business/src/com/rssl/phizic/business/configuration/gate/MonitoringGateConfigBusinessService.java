package com.rssl.phizic.business.configuration.gate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.monitoring.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringGateConfigBusinessService
{
	private static final String ERROR_MESSAGE = "����������� ��������� ������� � �������� ����� ����, ��� ����� �������� ���������� �� ����������� �������. " +
			"����������, ���������� � �������������� ������� ��������� ������.";

	private static final Class<MonitoringServiceGateConfig> WORK_CLASS = MonitoringServiceGateConfig.class;
	private static final String ID_FILD_NAME = "serviceName";
	private static final SimpleService service = new SimpleService();

	/**
	 *
	 * @param serviceName ��� �������
	 * @return ��������� �� ��
	 * @throws BusinessException
	 */
	public MonitoringServiceGateConfig findConfig(String serviceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(WORK_CLASS);
		criteria.add(Expression.eq(ID_FILD_NAME, serviceName));
		return service.findSingle(criteria);
	}

	// ��������� �����
	private void sendChangesToGates(MonitoringServiceGateConfig config) throws BusinessException, BusinessLogicException
	{
		try
		{
			EventSender.getInstance().sendEvent(new UpdateMonitoringConfigEvent(config));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	// ��������� ���������� �����������
	private void sendChangesToMonitoring(MonitoringServiceGateConfig config) throws BusinessException, BusinessLogicException
	{
		try
		{
			GateSingleton.getFactory().service(UpdateMonitoringGateConfigService.class).update(config);
		}
		catch (TemporalGateException e)
		{
			throw new BusinessLogicException(ERROR_MESSAGE, e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(ERROR_MESSAGE, e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void sendChanges(MonitoringServiceGateConfig config) throws BusinessException, BusinessLogicException
	{
		sendChangesToGates(config);
		sendChangesToMonitoring(config);
	}

	/**
	 * ��������� ������ ����������� � ��������� �� ���������� ���������� ����������� � ���������� ������� UpdateMonitoringConfigEvent
	 * @param config ������
	 * @return ����������� ������
	 * @throws BusinessException
	 */
	public MonitoringServiceGateConfig addOrUpdate(MonitoringServiceGateConfig config) throws BusinessException, BusinessLogicException
	{
		MonitoringServiceGateConfig savedConfig = service.addOrUpdate(config);
		sendChanges(savedConfig);
		return savedConfig;
	}

	/**
	 * ������ ��������� �������
	 * ����� �������� ������ � ������� ������� ������������������: 	NORMAL -> DEGRADATION -> INACCESSIBLE
	 * � ��������� �� ���������� ���������� ������� UpdateMonitoringConfigEvent
	 * ���������� ����������� �� ���������, �.�. ��� � ������ ������������ ����� ����� ������
	 * @param serviceName ������
	 * @param state ���������
	 * @throws BusinessException
	 */
	public void setState(String serviceName, MonitoringGateState state) throws BusinessException
	{
		try
		{
			MonitoringServiceGateConfig serviceGateConfig = findConfig(serviceName);
			//���� ������ �� ������, �� ��������� ������
			if(serviceGateConfig == null)
				return;

			//���� ����� ������ �� ��������������, �� �� ��������� ������
			if(!serviceGateConfig.isAvailableState(state))
				return;

			//���� ������� ������ ������ ����������������, �� ������� �������
			if (serviceGateConfig.getState().compare(state) > 0)
				return;

			serviceGateConfig.setState(state);
			//�.�. ������� ������������������ ���������, �� ����������� ������� ����
			MonitoringServiceGateStateConfig stateConfig = serviceGateConfig.getStateConfig(state);
			stateConfig.setDeteriorationTime(Calendar.getInstance());

			service.update(serviceGateConfig);
			sendChangesToGates(serviceGateConfig);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
