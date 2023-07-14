package com.rssl.phizic.logging.logon;

import com.rssl.phizic.logging.jms.JMSQueueSender;

/**
 * @author krenev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class JMSLogonLogWriter extends LogonLogWriterBase
{
	public static final String FACTORY_NAME = "jms/node/LoggingQCF";
	public static final String QUEUE_NAME = "jms/node/LogonLogQueue";
	private static final Object LOCK = new Object();
	private volatile JMSQueueSender sender;

	protected void doSave(LogonLogEntry entry) throws Exception
	{
		if (sender == null)
		{
			synchronized (LOCK)
			{
				if (sender == null)
				{
					sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
				}
			}
		}
		sender.send(entry);
	}
}
