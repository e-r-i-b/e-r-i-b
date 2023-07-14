package com.rssl.phizicgate.wsgateclient.services.monitoring;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.monitoring.generated.BackRefMonitoringGateConfigService;
import com.rssl.phizicgate.wsgateclient.services.monitoring.generated.BackRefMonitoringGateConfigServiceImpl_Impl;
import com.rssl.phizicgate.wsgateclient.services.monitoring.generated.MonitoringServiceGateConfigImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author akrenev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Обратный сервис для работы с настройками мониторинга
 */

public class BackRefMonitoringGateConfigServiceWrapper extends AbstractService implements com.rssl.phizic.gate.monitoring.BackRefMonitoringGateConfigService
{
	private StubUrlBackServiceWrapper<BackRefMonitoringGateConfigService> wrapper;

	/**
	 * конструктор сервиса
	 * @param factory гейт-фактори
	 */
	public BackRefMonitoringGateConfigServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<BackRefMonitoringGateConfigService>("BackRefMonitoringGateConfigServiceImpl"){
			protected void createStub()
			{
				BackRefMonitoringGateConfigServiceImpl_Impl service = new BackRefMonitoringGateConfigServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getBackRefMonitoringGateConfigServicePort());
			}
		};
	}

	public MonitoringServiceGateConfig getMonitoringGateConfig(String service) throws GateLogicException, GateException
	{
		try
		{
			MonitoringServiceGateConfigImpl serviceGateConfig = wrapper.getStub().getMonitoringGateConfig(service);
			return BeanHelper.copyObject(serviceGateConfig, TypesCorrelation.getTypes());
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void setState(String service, MonitoringGateState state) throws GateLogicException, GateException
	{
		try
		{
			wrapper.getStub().setState(service, state.name());
		}
		catch (RemoteException ex)
		{
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
