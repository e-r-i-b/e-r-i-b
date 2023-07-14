package com.rssl.phizic.logging.advertising;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.AdvertisingLogWriter;

import javax.jms.JMSException;

/**
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class JMSAdvertisingLogWriter  implements AdvertisingLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBAdvertisingLogQueue";
	private JMSQueueSender sender;

	public void write(AdvertisingLogEntry entry) throws JMSException
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}

