package com.rssl.phizic.logging.logon;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class LogonLogConfig extends Config
{
	private static final String LOG_WRITER_KEY = "com.rssl.phizic.logging.logon.LogonLogWriter";
	private LogonLogWriter writer;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public LogonLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override protected void doRefresh() throws ConfigurationException
	{
		writer = loadWriter(getProperty(LOG_WRITER_KEY));
	}

	private LogonLogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return NullLogonLogWriter.INSTANCE;
		}

		try
		{
			return (LogonLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}

	public LogonLogWriter getWriter()
	{
		return writer;
	}
}
