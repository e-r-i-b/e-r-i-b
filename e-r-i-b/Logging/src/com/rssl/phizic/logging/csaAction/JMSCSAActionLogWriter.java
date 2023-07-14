package com.rssl.phizic.logging.csaAction;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.CSAActionLogWriter;

import javax.jms.JMSException;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 *  Writer для асинхронного логирования записей в журнал входов ЦСА.
 */
public class JMSCSAActionLogWriter implements CSAActionLogWriter
{
	public static final String FACTORY_NAME = "jms/CSALoggingQueueFactory";
	public static final String QUEUE_NAME = "jms/CSAActionLogQueue";
	private JMSQueueSender sender;

	public void write(CSAActionLogEntryBase entry) throws JMSException
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}
