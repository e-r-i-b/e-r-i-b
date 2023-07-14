package com.rssl.phizic.logging.confirm;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author lukina
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class OperationConfirmLogConfig extends Config
{
	private static final String LOG_WRITER_KEY = "com.rssl.phizic.logging.confirm.OperationConfirmLogWriter";
	private OperationConfirmLogWriter writer;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public OperationConfirmLogConfig(PropertyReader reader)
	{
		super(reader);
	}

	public OperationConfirmLogWriter getWriter()
	{
		return writer;
	}

	protected void doRefresh() throws ConfigurationException
	{
		writer = loadWriter(getProperty(LOG_WRITER_KEY));
	}

	private OperationConfirmLogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return NullOperationConfirmLogWriter.INSTANCE;
		}

		try
		{
			return (OperationConfirmLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}
}
