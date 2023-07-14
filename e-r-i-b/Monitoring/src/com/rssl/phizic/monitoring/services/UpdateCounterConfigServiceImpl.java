package com.rssl.phizic.monitoring.services;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.monitoring.configuration.MonitoringGateServicesConfiguration;

/**
 * @author akrenev
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * —ервис обновлени€ настроек мониторинга
 * обновл€ет контекст
 * обновл€ет счетчик
 */

public class UpdateCounterConfigServiceImpl extends com.rssl.phizic.gate.monitoring.UpdateMonitoringGateConfigServiceImpl
{
	public UpdateCounterConfigServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void update(MonitoringServiceGateConfig config) throws GateLogicException, GateException
	{
		super.update(config);
		MonitoringGateServicesConfiguration.getInstance().getConfiguration(config.getServiceName()).update(config);
	}
}
