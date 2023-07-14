package com.rssl.phizic.web.struts;

import org.apache.struts.util.PropertyMessageResources;
import org.apache.struts.util.MessageResourcesFactory;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Загружает строки сообщений из списка *.properties разделенных ";"
 * @author Evgrafov
 * @ created 23.03.2006
 * @ $Author: balovtsev $
 * @ $Revision: 38017 $
 */
public class MultiPropertyMessageResources extends PropertyMessageResources
{
	private static final String SUBCONFIGS_SEPARATOR = ";";

	public MultiPropertyMessageResources(MessageResourcesFactory factory, String config)
	{
		super(factory, config);
	}

	public MultiPropertyMessageResources(MessageResourcesFactory factory, String config, boolean returnNull)
	{
		super(factory, config, returnNull);
	}

	/**
	 * Load the messages associated with the specified Locale key.  For this implementation, the
	 * <code>resourceConfig</code> property should contain a fully qualified package and resource name, separated by
	 * periods, of a series of property resources to be loaded from the class loader that created this
	 * PropertyMessageResources instance.  This is exactly the same name format you would use when utilizing the
	 * <code>java.util.PropertyResourceBundle</code> class.
	 *
	 * @param localeKey Locale key for the messages to be retrieved
	 */
	protected synchronized void loadLocale(String localeKey)
	{
		if (log.isTraceEnabled())
		{
			log.trace("loadLocale(" + localeKey + ")");
		}

		// Have we already attempted to load messages for this locale?
		if (locales.get(localeKey) != null)
		{
			return;
		}

		locales.put(localeKey, localeKey);

		String[] subConfigs = config.split(SUBCONFIGS_SEPARATOR);
		Properties props = new Properties();

		for (int i = 0; i < subConfigs.length; i++)
		{
			String subSonfig = subConfigs[i];
			String name = subSonfig.replace('.', '/').trim();
			// Set up to load the property resource for this locale key, if we can
			if (localeKey.length() > 0)
			{
				name += "_" + localeKey;
			}
			loadResources(name + ".properties", props);

		}

		if (log.isTraceEnabled())
		{
			log.trace("  Loading resource completed");
		}

		// Copy the corresponding values into our cache
		if (props.size() < 1)
		{
			return;
		}

		synchronized (messages)
		{
			for (Object o : props.keySet())
			{
				String key = (String) o;
				if (log.isTraceEnabled())
				{
					log.trace("  Saving message key '" + messageKey(localeKey, key));
				}
				messages.put(messageKey(localeKey, key), props.getProperty(key));
			}
		}
	}

	private Properties loadResources(String name, Properties props)
	{
		// Load the specified property resource
		if (log.isTraceEnabled())
		{
			log.trace("  Loading resource '" + name + "'");
		}

		InputStream is = new Native2ASCIIStream( getInputStreamFromClassPath(name) ).getInputStream();

		if (is != null)
		{
			try
			{
				props.load(is);
			}
			catch (IOException e)
			{
				log.error("loadLocale()", e);
			}
			finally
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					log.error("loadLocale()", e);
				}
			}
		}
		return props;
	}

	private InputStream getInputStreamFromClassPath(String name)
	{
		InputStream is = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = this.getClass().getClassLoader();
		}
		is = classLoader.getResourceAsStream(name);
		return is;
	}
}