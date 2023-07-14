package com.rssl.phizicgate.wsgate.services;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.JAXRPCClientSideService;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.client.ClientTransportFactory;

import java.rmi.RemoteException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.xml.rpc.Stub;

/**
 * @author gladishev
 * @ created 11.10.2012
 * @ $Author$
 * @ $Revision$
 * Клиентская часть jaxrpc веб-сервиса
 */
public abstract class JAXRPCClientSideServiceBase<S extends com.sun.xml.rpc.client.StubBase> extends AbstractService implements JAXRPCClientSideService
{
	protected final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private S stub;

	protected JAXRPCClientSideServiceBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * инициализировать стаб
	 * @param stubInstance - инстанс стаба
	 * @param url - урл серверной части сервиса
	 */
	protected void initStub(S stubInstance, String url)
	{
		stub = stubInstance;
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
		GateConfig config = ConfigFactory.getConfig(GateConfig.class);
		stub._setTransportFactory(getClientTimeoutTransportFactory(config.getTimeout()));
	}

	/**
	 * Создает новую транспортную фабрику
	 * @param timeout таймаут
	 * @return транспортную фабрику
	 */
	protected ClientTransportFactory getClientTimeoutTransportFactory(final int timeout)
	{
		return new ClientTransportFactory(){
				 public ClientTransport create() {
					 return new TimeoutHttpTransport(timeout);
				}
		};
	}

	/**
	 * Обновить стаб
	 * @param timeout таймаут
	 */
	public void updateStub(int timeout)
	{
		rwl.writeLock().lock();
		try
		{
			stub._setTransportFactory(getClientTimeoutTransportFactory(timeout));
		}
		finally
		{
			rwl.writeLock().unlock();
		}
	}

	/**
	 * @return стаб
	 */
	protected S getStub()
	{
		rwl.readLock().lock();
		try
		{
			return stub;
		}
		finally
		{
			rwl.readLock().unlock();
		}
	}

	protected void checkTimeoutException(RemoteException ex) throws GateWrapperTimeOutException
	{
		if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
			throw new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex);
	}
}
