package com.rssl.phizic.logging.settings;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.UserNotificationLogWriter;

/**
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer для асинхронного логирования изменений настроек оповещений
 */
public class JMSUserNotificationLogWriter implements UserNotificationLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBUserNotificationLogQueue";
	private JMSQueueSender sender;

	public void write(UserNotificationLogRecord entry) throws Exception
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}
