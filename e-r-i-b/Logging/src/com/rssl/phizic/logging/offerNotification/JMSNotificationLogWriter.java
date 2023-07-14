package com.rssl.phizic.logging.offerNotification;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.OfferNotificationLogWriter;

import javax.jms.JMSException;

/**
 * @author lukina
 * @ created 31.07.2014
 * @ $Author$
 * @ $Revision$
 * Writer для асинхронного логирования статистики по уведомлениям о предодобренных предложениях
 */
public class JMSNotificationLogWriter implements OfferNotificationLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBOfferNotificationLogQueue";
	private JMSQueueSender sender;

	public void write(NotificationLogEntry entry) throws JMSException
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}
