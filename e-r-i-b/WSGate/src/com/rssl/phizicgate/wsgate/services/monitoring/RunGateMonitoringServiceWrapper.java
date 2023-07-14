package com.rssl.phizicgate.wsgate.services.monitoring;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.MonitoringParameters;
import com.rssl.phizic.gate.monitoring.RunGateMonitoringService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.AbstractService;
import com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.monitoring.generated.RunGateMonitoringService_Stub;
import com.sun.xml.rpc.client.ClientTransportException;

import java.rmi.RemoteException;
import javax.xml.rpc.Stub;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class RunGateMonitoringServiceWrapper extends AbstractService implements RunGateMonitoringService
{
	private static final String SERVICE_SUFFIX = "/services/RunGateMonitoringServiceImpl";

	public RunGateMonitoringServiceWrapper(GateFactory factory)
	{
		super(factory);
	}

	private RunGateMonitoringService_Stub getStub(String gateUrl)
	{
		String url = gateUrl + SERVICE_SUFFIX;
		RunGateMonitoringServiceImpl_Impl service = new RunGateMonitoringServiceImpl_Impl();
		RunGateMonitoringService_Stub stub = (RunGateMonitoringService_Stub) service.getRunGateMonitoringServicePort();
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
		return stub;
	}

	public void run(String gateUrl, MonitoringParameters monitoringParameters) throws GateLogicException, GateException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.monitoring.generated.MonitoringParameters parametersGateInstance =
					new com.rssl.phizicgate.wsgate.services.monitoring.generated.MonitoringParameters();
			BeanHelper.copyPropertiesWithDifferentTypes(parametersGateInstance, monitoringParameters, TypesCorrelation.getTypes());

			getStub(gateUrl).run(parametersGateInstance);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
