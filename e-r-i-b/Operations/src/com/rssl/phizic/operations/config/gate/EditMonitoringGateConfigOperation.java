package com.rssl.phizic.operations.config.gate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.configuration.gate.MonitoringGateConfigBusinessService;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author akrenev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� �������� ����������� ������
 */

public class EditMonitoringGateConfigOperation extends OperationBase implements EditEntityOperation
{
	private static final MonitoringGateConfigBusinessService service = new MonitoringGateConfigBusinessService();
	private MonitoringServiceGateConfig config;
	private MonitoringGateState editingState;

	/**
	 * ������������� ��������
	 * @param serviceName ��� �������
	 * @param state ������������� ������
	 * @throws BusinessException
	 */
	public void initialize(String serviceName, MonitoringGateState state) throws BusinessException, BusinessLogicException
	{
		config = service.findConfig(serviceName);
		if (config == null)
			throw new ResourceNotFoundBusinessException("������ " + serviceName + " �� �������������� � �������.", MonitoringServiceGateConfig.class);

		if (!config.isAvailableState(state))
			throw new BusinessLogicException("��� ������� " + serviceName + " ��������� " + state + " �� ��������������.");

		editingState = state;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(config);
	}

	public MonitoringServiceGateStateConfig getEntity()
	{
		//� ������ ������, ��������� �������� �� null �������������� � ������ initialize: (config.isAvailableState(state)).		 
		return config.getStateConfig(editingState);
	}

	/**
	 * @return ������� ��������� �����������
	 */
	public MonitoringGateState getCurrentState()
	{
		return config.getState();
	}
}
