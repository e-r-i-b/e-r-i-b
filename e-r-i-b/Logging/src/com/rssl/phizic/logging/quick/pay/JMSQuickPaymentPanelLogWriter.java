package com.rssl.phizic.logging.quick.pay;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.QuickPaymentPanelLogWriter;

import javax.jms.JMSException;

/**
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class JMSQuickPaymentPanelLogWriter   implements QuickPaymentPanelLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBQuickPaymentPanelLogQueue";
	private JMSQueueSender sender;

	public void write(QuickPaymentPanelLogEntry entry) throws JMSException
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}

