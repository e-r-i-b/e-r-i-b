package com.rssl.phizic.monitoring.client.services;

import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.monitoring.MonitoringServicesConfiguration;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.monitoring.IncrementMode;
import com.rssl.phizic.gate.monitoring.IncrementMonitoringCounterService;
import com.rssl.phizic.monitoring.client.services.exception.WSMointoringClientException;
import com.rssl.phizic.monitoring.client.services.exception.WSMointoringClientLogicException;
import com.rssl.phizic.monitoring.client.services.exception.WSMonitoringClientTemporalException;
import com.rssl.phizic.monitoring.client.services.generated.IncrementMonitoringCounterServiceImpl_Impl;
import com.rssl.phizic.monitoring.client.services.generated.IncrementMonitoringCounterService_Stub;
import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.client.ClientTransportException;
import com.sun.xml.rpc.client.ClientTransportFactory;

import java.rmi.RemoteException;
import javax.xml.rpc.Stub;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * Враппер увеличения счетчика мониторинга
 */

public class IncrementMonitoringCounterServiceWrapper extends AbstractService implements IncrementMonitoringCounterService
{
	private static final int TIMEOUT = 500;

	private final Object locker = new Object();
	private volatile IncrementMonitoringCounterService_Stub stub = null;

	/**
	 * конструктор
	 * @param factory фабрика
	 */
	public IncrementMonitoringCounterServiceWrapper(GateFactory factory)
	{
		super(factory);
	}

	private IncrementMonitoringCounterService_Stub createStub()
	{
		String url = ConfigFactory.getConfig(MonitoringServicesConfiguration.class).getMonitoringAppUrl() + "/IncrementMonitoringCounterServiceImpl";
		IncrementMonitoringCounterServiceImpl_Impl service = new IncrementMonitoringCounterServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		IncrementMonitoringCounterService_Stub newStub = (IncrementMonitoringCounterService_Stub) service.getIncrementMonitoringCounterServicePort();
		newStub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
		//делаем асинхронность
		newStub._setTransportFactory(new ClientTransportFactory(){
				 public ClientTransport create() {
					 return new TimeoutHttpTransport(TIMEOUT);
				}});
		return newStub;
	}

	private IncrementMonitoringCounterService_Stub getStub()
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

	public void increment(String service, IncrementMode incrementMode) throws GateLogicException, GateException
	{
		try
		{
			getStub().increment(service, incrementMode.name());
		}
		catch (RemoteException ex)
		{
			if (ex.detail instanceof ClientTransportException)
			{
				//таймаут сами инициировали, что то вроде асинхронности
				return;
			}
			if (ex.getMessage().contains(WSMonitoringClientTemporalException.MESSAGE_PREFIX))
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

	public void incrementPercent(String service, IncrementMode incrementMode, boolean isFail) throws GateLogicException, GateException
	{
		try
		{
			getStub().incrementPercent(service, incrementMode.name(), isFail);
		}
		catch (RemoteException ex)
		{
			if (ex.detail instanceof ClientTransportException)
			{
				//таймаут сами инициировали, что то вроде асинхронности
				return;
			}
			if (ex.getMessage().contains(WSMonitoringClientTemporalException.MESSAGE_PREFIX))
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
