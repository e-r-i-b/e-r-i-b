package com.rssl.phizic.logging.finances.recategorization;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.writers.RecategorizationRuleLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lepihina
 * @ created 02.04.14
 * $Author$
 * $Revision$
 */
public class ALFRecategorizationRuleLogConfig extends Config
{
	public static final String WRITER_CLASS_KEY = "com.rssl.phizic.logging.writers.RecategorizationRuleLogWriter";
	public static final String LOGGING_ON_KEY = "com.rssl.phizic.logging.recategorizationLog.on";

	private RecategorizationRuleLogWriter writer;
	private boolean loggingOn;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 * @param reader ридер.
	 */
	public ALFRecategorizationRuleLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		String className = getProperty(WRITER_CLASS_KEY);
		if (writer == null || !writer.getClass().getName().equals(className))
		{
			writer = loadWriter(className);
		}
		loggingOn = getBoolProperty(LOGGING_ON_KEY);
	}

	private RecategorizationRuleLogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return null;
		}

		try
		{
			return (RecategorizationRuleLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}

	/**
	 * @return writer
	 */
	public RecategorizationRuleLogWriter getWriter()
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
}
