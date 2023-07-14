package com.rssl.phizic.monitoring.services;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.monitoring.IncrementMode;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.monitoring.configuration.MonitoringGateServicesConfiguration;
import com.rssl.phizic.monitoring.core.MonitoringCounterIncrementer;
import com.rssl.phizic.monitoring.core.percent.MonitoringCounterIncrementerPercent;
import com.rssl.phizic.monitoring.services.generated.IncrementMonitoringCounterService;
import com.rssl.phizic.web.security.Constants;

import java.rmi.RemoteException;

/**
 * @author akrenev
 * @ created 14.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class IncrementMonitoringCounterServiceImpl implements IncrementMonitoringCounterService
{
	public void increment(java.lang.String monitoringService, java.lang.String incrementMode) throws java.rmi.RemoteException
	{
		try
		{
			MonitoringGateServicesConfiguration configuration = MonitoringGateServicesConfiguration.getInstance();
			MonitoringCounterIncrementer.doInc(configuration.getConfiguration(monitoringService), IncrementMode.valueOf(incrementMode));
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void incrementPercent(java.lang.String monitoringService, java.lang.String incrementMode, boolean isFail) throws java.rmi.RemoteException
	{
		try
		{
			MonitoringGateServicesConfiguration configuration = MonitoringGateServicesConfiguration.getInstance();
			MonitoringCounterIncrementerPercent.doInc(configuration.getConfiguration(monitoringService), IncrementMode.valueOf(incrementMode), isFail);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
