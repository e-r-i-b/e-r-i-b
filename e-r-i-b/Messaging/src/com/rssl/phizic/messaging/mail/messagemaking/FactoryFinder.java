package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

/**
 * @author Evgrafov
 * @ created 05.07.2006
 * @ $Author: balovtsev $
 * @ $Revision: 17865 $
 */

public class FactoryFinder
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static Object newInstance(String className, ClassLoader cl) throws ConfigurationError
	{
		try
		{
			Class providerClass = cl.loadClass(className);
			Object instance = providerClass.newInstance();

			log.trace("created new instance of " + providerClass);

			return instance;
		}
		catch (ClassNotFoundException x)
		{
			throw new ConfigurationError("Provider " + className + " not found", x);
		}
		catch (Exception x)
		{
			throw new ConfigurationError("Provider " + className + " could not be instantiated: " + x, x);
		}
	}

	static Object find(String factoryId) throws ConfigurationError
	{

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		log.trace("find factoryId =" + factoryId);

		String systemProp = System.getProperty(factoryId);
		if (systemProp == null)
		{
			throw new ConfigurationError("Provider for " + factoryId + " cannot be found", null);
		}

		log.info("found system property, value=" + systemProp);
		return newInstance(systemProp, classLoader);

	}

	private static class ConfigurationError extends Error
	{
		private Exception exception;

		/** Construct a new instance with the specified detail string and exception. */
		ConfigurationError(String msg, Exception x)
		{
			super(msg);
			this.exception = x;
		}

		Exception getException()
		{
			return exception;
		}
	}


}