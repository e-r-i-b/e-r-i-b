package com.rssl.phizic.logging.system;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.utils.StringHelper;

import javax.jms.JMSException;

/**
 * @author krenev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class JMSSystemLogWriter extends SystemLogWriterBase
{
	public static final String DEFAULT_FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String DEFAULT_QUEUE_NAME = "jms/ERIBSystemLogQueue";
	private JMSQueueSender sender;

	/**
	 * Записать информацию для обновления статистики исключений
	 * @param logEntry информация об исключении
	 * @throws JMSException
	 */
	public void writeException(ExceptionSystemLogEntry logEntry) throws JMSException
	{
		doWrite(logEntry);
	}

	protected void doWrite(SystemLogEntry logEntry) throws JMSException
	{
		if(sender == null)
		{
			SystemLogConfig conf = ConfigFactory.getConfig(SystemLogConfig.class);
			sender = new JMSQueueSender(getJMSQueueFactoryName(conf), getJMSQueueName(conf));
		}
		sender.send(logEntry);
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

	public SystemLogEntry createEntry(LogModule source, String message, LogLevel level)
	{
		return createEntryBase(source, message, level);
	}
}
