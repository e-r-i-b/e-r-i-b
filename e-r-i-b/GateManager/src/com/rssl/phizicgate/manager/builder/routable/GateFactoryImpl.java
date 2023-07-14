package com.rssl.phizicgate.manager.builder.routable;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.ServiceCreator;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizicgate.manager.config.GateDescription;
import com.rssl.phizicgate.manager.config.GatesConfig;
import com.rssl.phizicgate.manager.config.ServiceDescriptor;
import com.rssl.phizicgate.manager.routing.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateFactoryImpl implements GateFactory
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private static final GatesConfig gatesConfig = new GatesConfig();
	private Map<Class<? extends Service>, Service> services;
	private Node node;

	private ServiceCreator serviceCreator;

	public GateFactoryImpl(Node node)
	{
		this.node = node;
	}

	public GateFactoryImpl(Node node, ServiceCreator serviceCreator)
	{
		this(node);
		this.serviceCreator =serviceCreator;

	}

	public void initialize() throws GateException
	{
		try
		{
			this.services = new HashMap<Class<? extends Service>, Service>();
			GateDescription gateDescription = gatesConfig.getGateDescription();
			if (gateDescription == null)
			{
				throw new GateException("Не найдено описание гейта для внешней системы " + node.getName());
			}
			for (ServiceDescriptor serviceDescriptor : gateDescription.getServices())
			{
				registerService(serviceDescriptor);
			}
		}
		catch (RuntimeException ex)
		{
			log.error("Ошибка при инициализации гейта", ex);
			throw ex;
		}
		catch (GateException ex)
		{
			log.error("Ошибка при инициализации гейта", ex);
			throw ex;
		}
	}

	private void registerService(ServiceDescriptor descriptor) throws GateException
	{
		Class<? extends Service> serviceInterface = null;
		try
		{
			serviceInterface = ClassHelper.loadClass(descriptor.getInterfaceClass());
		}
		catch (ClassNotFoundException e)
		{
			throw new GateException("Не найден класс для сервиса " + descriptor.getInterfaceClass(), e);
		}
		Service service = serviceCreator.createService(descriptor.getRealisationClass(), this);
		services.put(serviceInterface, service);
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
		return (C) node;
	}

	public Collection<? extends Service> services()
	{
		return services.values();
	}
}
