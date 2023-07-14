package com.rssl.phizic.logging.settings;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.offerNotification.NotificationLogEntry;
import com.rssl.phizic.logging.writers.UserMessageLogWriter;

import javax.jms.JMSException;

/**
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer для асинхронного логирования отправки оповещений клиенту
 */
public class JMSUserMessageLogWriter implements UserMessageLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBUserMessageLogQueue";
	private JMSQueueSender sender;

	public void write(UserMessageLogRecord entry) throws Exception
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}
