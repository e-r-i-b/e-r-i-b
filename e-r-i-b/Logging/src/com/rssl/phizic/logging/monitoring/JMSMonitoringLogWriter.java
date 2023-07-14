package com.rssl.phizic.logging.monitoring;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.jms.JMSQueueSender;

import javax.jms.JMSException;

/**
 * @author bogdanov
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 *
 * Писатель логов через JMS в ERIBMonitoringLogQueue.
 */
public class JMSMonitoringLogWriter implements BusinessOperationMonitoringWriter
{
	private final Object LOCK = new Object();
	private volatile JMSQueueSender sender;

	public void write(MonitoringEntry logEntry) throws LoggingException
	{
		MonitoringOperationConfig conf = ConfigFactory.getConfig(MonitoringOperationConfig.class);

		if (sender == null)
		{
			synchronized (LOCK)
			{
				if (sender == null)
				{
					sender = new JMSQueueSender(conf.getJMSQueueFactoryName(), conf.getJMSQueueName());
				}
			}
		}

		try
		{
			sender.send(logEntry);
		}
		catch (JMSException e)
		{
			throw new LoggingException("Не удалось произвести запись в лог через JMSLogWriter", e);
		}
	}
}
