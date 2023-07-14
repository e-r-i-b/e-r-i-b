package com.rssl.phizic.monitoring.client.services;

import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.monitoring.MonitoringServicesConfiguration;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.gate.monitoring.UpdateMonitoringGateConfigService;
import com.rssl.phizic.monitoring.client.services.exception.WSMointoringClientException;
import com.rssl.phizic.monitoring.client.services.exception.WSMointoringClientLogicException;
import com.rssl.phizic.monitoring.client.services.exception.WSMonitoringClientTemporalException;
import com.rssl.phizic.monitoring.client.services.generated.MonitoringServiceGateConfigImpl;
import com.rssl.phizic.monitoring.client.services.generated.UpdateMonitoringGateConfigServiceImpl_Impl;
import com.rssl.phizic.monitoring.client.services.generated.UpdateMonitoringGateConfigService_Stub;
import com.rssl.phizic.utils.BeanHelper;
import com.sun.xml.rpc.client.ClientTransportException;

import java.rmi.RemoteException;
import javax.xml.rpc.Stub;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * клиентская часть сервиса обновления настроек мониторинга
 */

public class UpdateMonitoringGateConfigServiceWrapper extends AbstractService implements UpdateMonitoringGateConfigService
{
	private final Object locker = new Object();
	private volatile UpdateMonitoringGateConfigService_Stub stub = null;

	/**
	 * конструктор
	 * @param factory фабрика, в рамках которой собирается сервис
	 */
	public UpdateMonitoringGateConfigServiceWrapper(GateFactory factory)
	{
		super(factory);
	}

	private UpdateMonitoringGateConfigService_Stub createStub()
	{
		String url = ConfigFactory.getConfig(MonitoringServicesConfiguration.class).getMonitoringAppUrl() + "/UpdateMonitoringGateConfigServiceImpl";
		UpdateMonitoringGateConfigServiceImpl_Impl service = new UpdateMonitoringGateConfigServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		UpdateMonitoringGateConfigService_Stub newStub = (UpdateMonitoringGateConfigService_Stub) service.getUpdateMonitoringGateConfigServicePort();
		newStub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
		return newStub;
	}

	private UpdateMonitoringGateConfigService_Stub getStub()
	{
		if (stub == null)
		{
			synchronized (locker)
			{
				if (stub == null)
					stub = createStub();
			}
		}
		return stub;
	}

	public void update(MonitoringServiceGateConfig config) throws GateLogicException, GateException
	{
		try
		{
			MonitoringServiceGateConfigImpl config_gate_instance = new MonitoringServiceGateConfigImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(config_gate_instance, config, TypesCorrelation.getTypes());
			getStub().update(config_gate_instance);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSMonitoringClientTemporalException.MESSAGE_PREFIX))
			{
				throw new WSMonitoringClientTemporalException(ex);
			}
			if (ex.getMessage().contains(WSMointoringClientLogicException.MESSAGE_PREFIX))
			{
				throw new WSMointoringClientLogicException(ex);
			}
			if (ex.getMessage().contains(WSMointoringClientException.MESSAGE_PREFIX))
			{
				throw new WSMointoringClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
