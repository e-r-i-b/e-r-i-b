package com.rssl.phizic.logging.ermb;

import com.rssl.phizic.logging.jms.JMSQueueSender;

/**
 * Writer для записи логов о превышении лимита запросов в ЕРМБ/МБК  по номеру телефона
 * @author lukina
 * @ created 04.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class JMSRequestCardByPhoneLodWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBUnionLogQueue";
	private JMSQueueSender sender;

	public void write(RequestCardByPhoneLogEntry entry) throws Exception
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}
