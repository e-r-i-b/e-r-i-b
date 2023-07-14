package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;

/**
 * @author akrenev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс обратного сервиса для работы с настройками мониторинга
 */

public interface BackRefMonitoringGateConfigService extends Service
{
	/**
	 * Получение настроек мониторинга
	 * @param service имя сервиса
	 * @return настройки мониторинга
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public MonitoringServiceGateConfig getMonitoringGateConfig(String service) throws GateLogicException, GateException;

	/**
	 * Задать статус для сервиса
	 * @param service имя сервиса
	 * @param state новый статус
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void setState(String service, MonitoringGateState state) throws GateLogicException, GateException;
}
