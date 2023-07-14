package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class JMSMessageLogWriter  extends MessageLogWriterBase
{
	public static final String DEFAULT_FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String DEFAULT_QUEUE_NAME = "jms/ERIBMessageLogQueue";
	private JMSQueueSender sender;

	protected void doWrite(MessagingLogEntryBase logEntry) throws Exception
	{
		if(sender == null)
		{
			MessageLogConfig conf = ConfigFactory.getConfig(MessageLogConfig.class);
			sender = new JMSQueueSender(getJMSQueueFactoryName(conf), getJMSQueueName(conf));
		}
		sender.send(logEntry);
	}

	private String getJMSQueueName(MessageLogConfig conf)
	{
		if(StringHelper.isEmpty(conf.getJMSQueueName()))
			return DEFAULT_QUEUE_NAME;
		return conf.getJMSQueueName();
	}

	private String getJMSQueueFactoryName(MessageLogConfig conf)
	{
		if(StringHelper.isEmpty(conf.getJMSQueueFactoryName()))
			return DEFAULT_FACTORY_NAME;
		return conf.getJMSQueueFactoryName();
	}
}
