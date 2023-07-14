package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * резервный сервис запуска шлюзов
 */

public interface RunGateMonitoringService extends Service
{
	/**
	 * запустить мониторинг шлюза
	 * @param gateUrl урл шлюза
	 * @param monitoringParameters параметры запуска
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public void run(String gateUrl, MonitoringParameters monitoringParameters) throws GateLogicException, GateException;
}
