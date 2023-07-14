package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * @author akrenev
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис обновления настроек мониторинга
 * обновляет контекст
 */

public class UpdateMonitoringGateConfigServiceImpl extends AbstractService implements UpdateMonitoringGateConfigService
{
	public UpdateMonitoringGateConfigServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void update(MonitoringServiceGateConfig config) throws GateLogicException, GateException
	{
		MonitoringGateServicesContext.getInstance().updateConfig(config);
	}
}
