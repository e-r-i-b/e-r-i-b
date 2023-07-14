package com.rssl.phizic.logging.system;

import com.rssl.phizic.config.*;
import com.rssl.phizic.logging.writers.SystemLogWriter;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author eMakarov
 * @ created 18.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SystemLogConfigImpl extends SystemLogConfig
{
	private Map<LogModule, Integer> levels = new HashMap<LogModule, Integer>();
	private Map<LogModule, Integer> extendedLevels = new HashMap<LogModule, Integer>(); //степени расширенного логирования
	private SystemLogWriter mainWriter;
	private SystemLogWriter backupWriter;
	//instancename для записи в БД, для DataBaseSystemLogWriter
	private String dbWriterInstanceName;
	private String jMSQueueName;
	private String jMSQueueFactoryName;

	public SystemLogConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public int getModuleLogLevel(LogModule moduleName)
	{
		return levels.get(moduleName);
	}

	public int getModuleExtendedLogLevel(LogModule moduleName)
	{
		return extendedLevels.get(moduleName);
	}

	public SystemLogWriter getMainWriter()
	{
		return mainWriter;
	}

	public SystemLogWriter getBackupWriter()
	{
		return backupWriter;
	}

	public String getDbWriterInstanceName()
	{
		return dbWriterInstanceName;
	}

	public String getJMSQueueName()
	{
		return jMSQueueName;
	}

	public String getJMSQueueFactoryName()
	{
		return jMSQueueFactoryName;
	}

	/**
	 * Обновить содержимое конфига
	 */
	protected void doRefresh() throws ConfigurationException
	{
		String className = getProperty(SystemLogConfig.MAIN_WRITER_CLASS_KEY);
		if (mainWriter == null || !mainWriter.getClass().getName().equals(className))
		{
			mainWriter = loadWriter(className);
		}
		className = getProperty(SystemLogConfig.BACKUP_WRITER_CLASS_KEY);
		if (backupWriter == null || !backupWriter.getClass().getName().equals(className))
		{
			backupWriter = loadWriter(className);
		}

		initLogLevels(levels, "");
		initLogLevels(extendedLevels, EXTENDED_PREFIX);

		dbWriterInstanceName = getProperty(DB_INSTANCE_NAME);
		jMSQueueFactoryName = getProperty(JMS_QUEUE_FACTORY_NAME);
		jMSQueueName = getProperty(JMS_QUEUE_NAME);
	}

	private void initLogLevels(Map<LogModule, Integer> levelsMap, String extendedPrefix)
	{
		levelsMap.clear();
		Properties properties = getProperties(LOG_PREFIX);
		String levelPrefix = LOG_PREFIX + extendedPrefix + LEVEL_PREFIX;
		for (Map.Entry entry : properties.entrySet())
		{
			String key = (String) entry.getKey();
			if (key.startsWith(levelPrefix))
			{
				Integer value = Integer.parseInt((String) entry.getValue());
				key = key.substring(levelPrefix.length());

				levelsMap.put(LogModule.fromValue(key), value);
			}
		}
	}

	private SystemLogWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return null;
		}
		try
		{
			return (SystemLogWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}
}
