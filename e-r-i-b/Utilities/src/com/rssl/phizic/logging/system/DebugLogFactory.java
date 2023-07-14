package com.rssl.phizic.logging.system;

import org.apache.commons.logging.LogConfigurationException;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * ��� ������� �����-���� ��������
 *
 * @ author: Gololobov
 * @ created: 08.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class DebugLogFactory
{
	private static final String LOG_CONSTRUCTOR_IMPL_CLASSNAME = "com.rssl.phizic.logging.system.DebugLogConstructorImpl";
	private static final DebugLogFactory factory = new DebugLogFactory();
	private final Map<LogModule, Log> instances = new HashMap<LogModule, Log>();
	private LogConstructor logConstructor = null;
	private final Object lock = new Object();

	protected DebugLogFactory()
	{
		super();
	}

	public static Log getLog(LogModule module) throws LogConfigurationException
	{
        return factory.getInstance(module);
    }

	private Log getInstance(LogModule name) throws LogConfigurationException
	{
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
			// ������ ����������� �����, ������� ���� � ������ Logging
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
			throw new LogConfigurationException("�� ������� ������� DebugLog", e);
		}
	}
}
