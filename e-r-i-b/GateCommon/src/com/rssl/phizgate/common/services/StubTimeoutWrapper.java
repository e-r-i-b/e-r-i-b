package com.rssl.phizgate.common.services;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.sun.xml.rpc.client.ClientTransport;
import com.sun.xml.rpc.client.ClientTransportFactory;
import com.sun.xml.rpc.client.StubBase;

/**
 * Обертка стаба для обновления таймаута
 * @author basharin
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class StubTimeoutWrapper<S extends java.rmi.Remote> extends StubWrapperBase<S>
{
	private volatile int currentTimeout = -1;

	protected StubTimeoutWrapper(S stub)
	{
		super(stub);
	}

	/**
	 * @return время ожидания ответа
	 */
	public Integer getTimeout()
	{
		if (getConfig().getConnectionTimeout() == null)
			return  null;
		return getConfig().getConnectionTimeout().intValue();
	}

	/**
	 * Создает новую транспортную фабрику
	 * @param timeout таймаут
	 * @return транспортную фабрику
	 */
	private ClientTransportFactory getClientTimeoutTransportFactory(final int timeout)
	{
		return new ClientTransportFactory(){
				 public ClientTransport create() {
					 return new TimeoutHttpTransport(timeout);
				}
		};
	}

	protected void updateStub()
	{
		Integer actualTimeout = getTimeout();
		if (currentTimeout != actualTimeout)
		{
			currentTimeout = actualTimeout;
			((StubBase)stub)._setTransportFactory(getClientTimeoutTransportFactory(currentTimeout));
		}
	}

	protected boolean isActualStub()
	{
		return getTimeout() == currentTimeout;
	}

	protected void resetParameters()
	{
		currentTimeout = -1;
	}

	protected GateConnectionConfig getConfig()
	{
		return ConfigFactory.getConfig(GateConnectionConfig.class);
	}
}
