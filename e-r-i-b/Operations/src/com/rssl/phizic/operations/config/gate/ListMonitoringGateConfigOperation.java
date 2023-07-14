package com.rssl.phizic.operations.config.gate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.configuration.gate.MonitoringGateConfigBusinessService;
import com.rssl.phizic.business.marker.JobExecutionMarkerService;
import com.rssl.phizic.business.payments.job.SendOfflineDelayedPaymentsJob;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * операция списка настроек мониторинга сервисов шлюзов
 */

public class ListMonitoringGateConfigOperation extends OperationBase implements ListEntitiesOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final MonitoringGateConfigBusinessService service = new MonitoringGateConfigBusinessService();
	private static final JobExecutionMarkerService jobExecutionMarkerService = new JobExecutionMarkerService();

	/**
	 * инициализация операции
	 * @param serviceName имя сервиса
	 * @param newState новое состояние сервиса
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private MonitoringServiceGateConfig initialize(String serviceName,  MonitoringGateState newState) throws BusinessLogicException, BusinessException
	{
		MonitoringServiceGateConfig config = service.findConfig(serviceName);

		if (newState == config.getState())
			throw new BusinessLogicException("Невозможно перевести сервис в статус \"" + newState.getDescription() + "\"");

		config.setState(newState);
		config.getDegradationConfig().setDeteriorationTime(null);
		config.getInaccessibleConfig().setDeteriorationTime(null);

		switch (newState)
		{
			case NORMAL:       break;
			case DEGRADATION:  config.getDegradationConfig().setDeteriorationTime(Calendar.getInstance());  break;
			case INACCESSIBLE: config.getInaccessibleConfig().setDeteriorationTime(Calendar.getInstance()); break;
		}
		return config;
	}

	/**
	 * изменить состояние сервиса
	 * @param serviceName имя сервиса
	 * @param newState новое состояние сервиса
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void changeServiceState(String serviceName,  MonitoringGateState newState) throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(initialize(serviceName, newState));
		try
		{
			jobExecutionMarkerService.createForJob(SendOfflineDelayedPaymentsJob.class.getName());
		}
		catch (Exception e)
		{
			log.error("Ошибка запуска процесса проводки отложенных платежей.", e);
		}
	}
}
