package com.rssl.phizicgate.manager.builder.persistent;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * @author Krenev
 * @ created 19.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class SimpleGateFactory implements GateFactory
{
	private Map<Class<? extends Service>, Service> services;

	public void initialize() throws GateException
	{
		services = new HashMap<Class<? extends Service>, Service>();
	}

	public <S extends Service> S service(Class<S> serviceInterface)
	{
		Object service = services.get(serviceInterface);
		if (service == null)
		{
			throw new RuntimeException("Служба c интерфейсом " + serviceInterface.getName() + " не найдена");
		}
		//noinspection unchecked
		return (S) service;
	}

	public <C extends GateConfig> C config(Class<C> configInterface)
	{
		throw new UnsupportedOperationException();
	}

	public Collection<? extends Service> services()
	{
		throw new UnsupportedOperationException();
	}

	public Service registerService(Class<? extends Service> serviceInterface, Service service)
	{
		return services.put(serviceInterface, service);
	}
}
