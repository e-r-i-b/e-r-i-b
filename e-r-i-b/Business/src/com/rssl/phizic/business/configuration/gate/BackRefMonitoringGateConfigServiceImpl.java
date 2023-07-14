package com.rssl.phizic.business.configuration.gate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.configuration.gate.locale.MultilocaleMonitoringGateConfigBusinessService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.monitoring.BackRefMonitoringGateConfigService;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;

/**
 * @author akrenev
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class BackRefMonitoringGateConfigServiceImpl extends AbstractService implements BackRefMonitoringGateConfigService
{
	private static final MonitoringGateConfigBusinessService businessService = new MonitoringGateConfigBusinessService();
	private static final MultilocaleMonitoringGateConfigBusinessService multiLocaleBusinessService = new MultilocaleMonitoringGateConfigBusinessService();

	public BackRefMonitoringGateConfigServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public MonitoringServiceGateConfig getMonitoringGateConfig(String service) throws GateLogicException, GateException
	{
		try
		{
			return multiLocaleBusinessService.findMultiLocaleConfig(service);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public void setState(String service, MonitoringGateState state) throws GateLogicException, GateException
	{
		try
		{
			businessService.setState(service, state);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
