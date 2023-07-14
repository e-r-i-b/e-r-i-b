package com.rssl.phizic.logging.writers;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogEntry;
import com.rssl.phizic.logging.LoggingException;
import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.system.SystemLogConfig;
import com.rssl.phizic.utils.StringHelper;

import javax.jms.JMSException;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Писатель логов через JMS
 */
public class JMSLogWriter implements LogWriter
{
	public static final String DEFAULT_FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String DEFAULT_QUEUE_NAME = "jms/ERIBSystemLogQueue";
	private JMSQueueSender sender;

	public void write(LogEntry logEntry) throws LoggingException
	{
		if(sender == null)
		{
			SystemLogConfig conf = ConfigFactory.getConfig(SystemLogConfig.class);
			sender = new JMSQueueSender(getJMSQueueFactoryName(conf), getJMSQueueName(conf));
		}
		try
		{
			sender.send(logEntry);
		}
		catch (JMSException e)
		{
			throw new LoggingException("Не удалось произвести запись в лог через JMSLogWriter",e);
		}
	}

	private String getJMSQueueName(SystemLogConfig conf)
	{
		if(StringHelper.isEmpty(conf.getJMSQueueName()))
			return DEFAULT_QUEUE_NAME;
		return conf.getJMSQueueName();
	}

	private String getJMSQueueFactoryName(SystemLogConfig conf)
	{
		if(StringHelper.isEmpty(conf.getJMSQueueFactoryName()))
			return DEFAULT_FACTORY_NAME;
		return conf.getJMSQueueFactoryName();
	}
}
