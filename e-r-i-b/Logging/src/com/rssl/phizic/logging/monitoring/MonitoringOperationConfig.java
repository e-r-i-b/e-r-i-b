package com.rssl.phizic.logging.monitoring;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * Настройки мониторинга операций.
 *
 * @author bogdanov
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringOperationConfig extends Config
{
	public static final String DEFAULT_FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String DEFAULT_QUEUE_NAME = "jms/ERIBMonitoringLogQueue";

	public static final String QUEUE_NAME_KEY = "com.rssl.phizic.logging.MonitoringOperation.queuename";
	public static final String FACTORY_NAME_KEY = "com.rssl.phizic.logging.MonitoringOperation.queuefactoryname";
	public static final String ACTIVE_MONITORING_KEY = "com.rssl.phizic.logging.MonitoringOperation.on";
	public static final String ACTIVE_LOGIN_MONITORING_KEY = "com.rssl.phizic.logging.MonitoringLogin.on";

	private static final String MONITORING_WRITER_KEY = "com.rssl.phizic.logging.monitoring.BusinessOperationMonitoringWriter";
	private BusinessOperationMonitoringWriter writer;

	private String jmsQueueName;
	private String jmsFactoryName;
	private boolean active;
	private boolean activeLoginMonitoring;

	public MonitoringOperationConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		active = getBoolProperty(ACTIVE_MONITORING_KEY, true);
		activeLoginMonitoring = getBoolProperty(ACTIVE_LOGIN_MONITORING_KEY, true);
		jmsQueueName = getProperty(QUEUE_NAME_KEY, DEFAULT_QUEUE_NAME);
		jmsFactoryName = getProperty(FACTORY_NAME_KEY, DEFAULT_FACTORY_NAME);
		if (writer == null || !writer.getClass().getName().equals(getProperty(MONITORING_WRITER_KEY)))
		{
			writer = loadWriter(getProperty(MONITORING_WRITER_KEY));
		}
	}

	private BusinessOperationMonitoringWriter loadWriter(String className)
	{
		if (StringHelper.isEmpty(className))
		{
			return null;
		}

		try
		{
			return (BusinessOperationMonitoringWriter) ClassHelper.loadClass(className).newInstance();
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка загрузки " + className, e);
		}
	}

	/**
	 * Записывает в лог запись мониторинга, если лог работает.
	 *
	 * @param entry запись лога.
	 */
	public void writeLog(MonitoringLogEntry entry) throws LoggingException
	{
		if (!active)
		    return;

		if (writer == null)
			return;

		writer.write(entry);
	}

	/**
	 * Записывает в лог запись мониторинга, если лог работает.
	 *
	 * @param entry запись лога.
	 */
	public void writeLog(MonitoringUserLoginEntry entry) throws LoggingException
	{
		if (!activeLoginMonitoring)
		    return;

		if (writer == null)
			return;

		writer.write(entry);
	}

	/**
	 * @return name очереди для JMSWriter
	 */
	public String getJMSQueueName()
	{
		return jmsQueueName;
	}
	/**
	 * @return name фабрики очередей для асинхронного логирования
	 */
	public String getJMSQueueFactoryName()
	{
	   return jmsFactoryName;
	}

	/**
	 * @return работает ли запись в лог.
	 */
	public boolean isActive()
	{
		return active;
	}

	/**
	 * @return включен ли мониторинг входов.
	 */
	public boolean isActiveLoginMonitoring()
	{
		return activeLoginMonitoring;
	}
}
