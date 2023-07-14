package com.rssl.phizic.logging.finances.recategorization;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.RecategorizationRuleLogWriter;

import javax.jms.JMSException;

/**
 * @author lepihina
 * @ created 01.04.14
 * $Author$
 * $Revision$
 * Writer для асинхронного логирования записей о добавлении/применении правила перекатегоризации
 */
public class JMSRecategorizationRuleLogWriter implements RecategorizationRuleLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBUserLogQueue";
	private JMSQueueSender sender;

	public void write(ALFRecategorizationRuleEntry entry) throws JMSException
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}
