package com.rssl.phizic.node.manager;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.GateProperties;
import com.rssl.phizic.gate.impl.ServiceCreator;
import com.rssl.phizic.gate.impl.SimpleServiceCreator;
import com.rssl.phizic.gate.node.NodeFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author osminin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */
public class NodeFactoryImpl implements NodeFactory
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final String NODE_KEY = ".delegate.node";

	private String listenerHost;

	private Map<Class<? extends Service>, Service> services;
	private Properties properties;
	private ServiceCreator serviceCreator;

	/**
	 * ctor
	 * @param listenerHost хост листенера вэб-сервиса блока
	 */
	public NodeFactoryImpl(String listenerHost)
	{
		this.listenerHost = listenerHost;
		serviceCreator = new SimpleServiceCreator();
	}

	public String getListenerHost()
	{
		return listenerHost;
	}

	public void initialize() throws GateException
	{
		try
		{
			properties = GateProperties.getProperties();
			services = new HashMap<Class<? extends Service>, Service>(properties.size());

			//регистрируем сервисы
			registerService("com.rssl.phizic.gate.fund.FundMultiNodeService");
		}
		catch (GateException e)
		{
			log.error("Ошибка при инициализации фабрики блока.", e);
			throw e;
		}
	}

	private void registerService(String serviceInterfaceName) throws GateException
	{
		try
		{
			String value = properties.getProperty(serviceInterfaceName + NODE_KEY);
			if (StringHelper.isNotEmpty(value))
			{
				Class<? extends Service> serviceInterface = ClassHelper.loadClass(serviceInterfaceName);
				Service service = serviceCreator.createService(value, this);
				services.put(serviceInterface, service);
			}
		}
		catch (ClassNotFoundException e)
		{
			log.error("Не найден класс для сервиса " + serviceInterfaceName, e);
			throw new GateException(e);
		}
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
		return services.values();
	}
}
