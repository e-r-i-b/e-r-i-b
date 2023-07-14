package com.rssl.phizic.logging.finances.category;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.CardOperationCategoryChangingLogWriter;

import javax.jms.JMSException;

/**
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer для асинхронного логирования записей о изменении клиентом категорий
 */
public class JMSCardOperationCategoryChangingLogWriter implements CardOperationCategoryChangingLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBCardOperationCategoryChangingLogQueue";
	private JMSQueueSender sender;


	public void write(CardOperationCategoryChangingLogEntry logEntry) throws JMSException
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(logEntry);
	}
}
