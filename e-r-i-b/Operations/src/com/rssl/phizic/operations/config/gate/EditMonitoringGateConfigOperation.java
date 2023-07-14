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
 * ќпераци€ редактировани€ настроек мониторинга шлюзов
 */

public class EditMonitoringGateConfigOperation extends OperationBase implements EditEntityOperation
{
	private static final MonitoringGateConfigBusinessService service = new MonitoringGateConfigBusinessService();
	private MonitoringServiceGateConfig config;
	private MonitoringGateState editingState;

	/**
	 * инициализаци€ операции
	 * @param serviceName им€ сервиса
	 * @param state редактируемый статус
	 * @throws BusinessException
	 */
	public void initialize(String serviceName, MonitoringGateState state) throws BusinessException, BusinessLogicException
	{
		config = service.findConfig(serviceName);
		if (config == null)
			throw new ResourceNotFoundBusinessException("—ервис " + serviceName + " не поддерживаетс€ в системе.", MonitoringServiceGateConfig.class);

		if (!config.isAvailableState(state))
			throw new BusinessLogicException("ƒл€ сервиса " + serviceName + " состо€ние " + state + " не поддерживаетс€.");

		editingState = state;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(config);
	}

	public MonitoringServiceGateStateConfig getEntity()
	{
		//¬ данном случае, косвенна€ проверка на null осуществл€етс€ в методе initialize: (config.isAvailableState(state)).		 
		return config.getStateConfig(editingState);
	}

	/**
	 * @return текущее состо€ние мониторинга
	 */
	public MonitoringGateState getCurrentState()
	{
		return config.getState();
	}
}
