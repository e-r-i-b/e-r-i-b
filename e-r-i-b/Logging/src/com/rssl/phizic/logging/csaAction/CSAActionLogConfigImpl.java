package com.rssl.phizic.logging.csaAction;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.writers.CSAActionLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author vagin
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 * Имплементация конфига для журнализирования действий пользователя в ЦСА(Журнал входов)
 */
public class CSAActionLogConfigImpl extends CSAActionLogConfig
{
	private CSAActionLogWriter mainWriter;
	//instancename для записи в БД, для DBCSAActionLogWriter
	private String dbWriterInstanceName;

	public CSAActionLogConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		String className = getProperty(CSAActionLogConfig.MAIN_WRITER_CLASS_KEY);
		if (mainWriter == null || !mainWriter.getClass().getName().equals(className))
		{
			mainWriter = loadWriter(className);
		}

		dbWriterInstanceName = getProperty(DB_INSTANCE_NAME);
	}

	public CSAActionLogWriter getWriter()
	{
		return mainWriter;
	}

	public String getDbWriterInstanceName()
	{
		return dbWriterInstanceName;
	}

	private CSAActionLogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
			return null;
		try
		{
			return (CSAActionLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}
}
