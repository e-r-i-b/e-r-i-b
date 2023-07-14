package com.rssl.phizic.web.gate.services.monitoring;

import com.rssl.phizic.gate.monitoring.MonitoringGateServicesContext;
import com.rssl.phizic.gate.monitoring.MonitoringParameters;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringStateParameters;
import com.rssl.phizic.web.gate.services.monitoring.generated.RunGateMonitoringService;

import java.rmi.RemoteException;

/**
 * @author akrenev
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class RunGateMonitoringServiceImpl implements RunGateMonitoringService
{
	public void run(com.rssl.phizic.web.gate.services.monitoring.generated.MonitoringParameters parameters) throws RemoteException
	{
		try
		{
			MonitoringParameters config = new MonitoringParameters();
			BeanHelper.copyPropertiesWithDifferentTypes(config, parameters, TypesCorrelation.getTypes());
			MonitoringGateServicesContext.getInstance().updateConfig(config);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public MonitoringStateParameters __forGenerateMonitoringStateParameters() throws RemoteException
	{
		return null;
	}
}
