package com.rssl.phizic.rapida;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

/**
 * @author hudyakov
 * @ created 25.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class RapidaFactory
{
	static Map<Class<? extends RapidaService>, RapidaService> services = new HashMap<Class<? extends RapidaService>, RapidaService>();
	static Properties properties;

	static
	{
		try
		{
			properties = RapidaProperties.getProperties();
			registerService("com.rssl.phizic.rapida.RapidaService");
		}
		//будет поймано выше
		catch (GateException e)
		{
		}
	}

	private static void registerService(String serviceInterfaceName) throws GateException
	{
		String value = properties.getProperty(serviceInterfaceName);

		if (value != null)
		{
			RapidaService service = null;
			Class<? extends RapidaService> serviceInterface = null;
			try
			{
				serviceInterface = ClassHelper.loadClass(serviceInterfaceName);

				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				Class<?> serviceClass = classLoader.loadClass(value);
				service = (RapidaService) serviceClass.newInstance();
			}
			catch (ClassNotFoundException e)
			{
				throw new GateException("Не найден класс для сервиса " + serviceInterfaceName, e);
			}
			catch (IllegalAccessException e)
			{
				throw new GateException(e);
			}
			catch (InstantiationException e)
			{
				throw new GateException(e);
			}

			services.put(serviceInterface, service);
		}
	}

	public static RapidaService service ( Class serviceInterface )
	{
		Object service = services.get(serviceInterface);
		if (service == null)
		{
			throw new RuntimeException("Служба c интерфейсом "+serviceInterface.getName()+" не найдена");
		}

		return (RapidaService) service;
	}
}
