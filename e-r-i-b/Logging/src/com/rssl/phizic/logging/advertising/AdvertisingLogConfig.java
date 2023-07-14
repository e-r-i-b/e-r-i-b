package com.rssl.phizic.logging.advertising;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.writers.AdvertisingLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingLogConfig   extends Config
{
	public static final String WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.AdvertisingLogWriter";
	public static final String LOGGING_ON_KEY = "com.rssl.phizic.logging.advertising.AdvertisingLog.on";

	private AdvertisingLogWriter writer;
	private boolean loggingOn;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 * @param reader ридер.
	 */
	public AdvertisingLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return writer
	 */
	public AdvertisingLogWriter getWriter()
	{
		return writer;
	}

	/**
	 * @return true - логгирование включено
	 */
	public boolean isLoggingOn()
	{
		return loggingOn;
	}

	protected void doRefresh() throws ConfigurationException
	{
		String className = getProperty(WRITER_CLASS_KEY);
		if (writer == null || !writer.getClass().getName().equals(className))
		{
			writer = loadWriter(className);
		}
		loggingOn = getBoolProperty(LOGGING_ON_KEY);
	}

	private AdvertisingLogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return null;
		}

		try
		{
			return (AdvertisingLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}
}
