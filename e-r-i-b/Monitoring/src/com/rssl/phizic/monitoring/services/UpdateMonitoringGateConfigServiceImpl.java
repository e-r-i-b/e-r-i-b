package com.rssl.phizic.monitoring.services;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.gate.monitoring.UpdateMonitoringGateConfigService;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.monitoring.services.generated.MonitoringServiceGateConfigImpl;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;

import java.rmi.RemoteException;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class UpdateMonitoringGateConfigServiceImpl implements com.rssl.phizic.monitoring.services.generated.UpdateMonitoringGateConfigService
{
	public void update(MonitoringServiceGateConfigImpl config_gate_instance) throws RemoteException
	{
		try
		{
			MonitoringServiceGateConfig config = new MonitoringServiceGateConfig();
			BeanHelper.copyPropertiesWithDifferentTypes(config, config_gate_instance, TypesCorrelation.getTypes());

			UpdateMonitoringGateConfigService service = GateSingleton.getFactory().service(UpdateMonitoringGateConfigService.class);
			service.update(config);
		}
		catch(GateTimeOutException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE).error(e);
			throw new RemoteException(Constants.TIMEOUT_MESSAGE_PREFIX + e.getMessage() + Constants.TIMEOUT_MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch(TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
