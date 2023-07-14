package com.rssl.phizic.logging.system;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import org.apache.commons.logging.LogConfigurationException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eMakarov
 * @ created 16.03.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Точка доступа к логу
 * Поскольку LogImpl живёт в модуле Logging, а логи бывают нужны ниже этого модуля (по схеме зависимостей),
 * логи создаются через отложенную загрузку классов.
 * Таким образом PhizICLogFactory получает LogImpl из Logging 
 */
@ThreadSafe
public class PhizICLogFactory
{
	private static final String LOG_CONSTRUCTOR_IMPL_CLASSNAME = "com.rssl.phizic.logging.system.LogConstructorImpl";

	private static final PhizICLogFactory factory = new PhizICLogFactory();

	private final Map<LogModule, Log> instances = new HashMap<LogModule, Log>();

	private LogConstructor logConstructor = null;

	private final Object lock = new Object();

	///////////////////////////////////////////////////////////////////////////

	private PhizICLogFactory() {}

	public static Log getLog(LogModule module) throws LogConfigurationException
	{
		// TODO сделать информативное исключение, как в apache

        return factory.getInstance(module);
    }

	private Log getInstance(LogModule name) throws LogConfigurationException
	{
		// TODO сделать информативное исключение, как в apache
        Log instance = instances.get(name);
        if (instance == null)
        {
	        synchronized (lock)
	        {
		        if (instance == null)
		        {
			        instance = newInstance(name);
			        instances.put(name, instance);
		        }
	        }
        }
        return instance;
    }

	private Log newInstance(LogModule name) throws LogConfigurationException
	{
		try
		{
			// Грузим конструктор логов, который живёт в модуле Logging
			if (logConstructor == null)
			{
				synchronized (lock)
				{
					if (logConstructor == null)
					{
						Class<?> instanceClass = Class.forName(LOG_CONSTRUCTOR_IMPL_CLASSNAME);
						logConstructor = (LogConstructor) instanceClass.newInstance();
					}
				}
			}

			return logConstructor.newInstance(name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new LogConfigurationException("Не удалось создать Log", e);
		}
	}
}
