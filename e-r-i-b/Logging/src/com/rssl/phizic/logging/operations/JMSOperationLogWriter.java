package com.rssl.phizic.logging.operations;

import com.rssl.phizic.logging.jms.JMSQueueSender;

/**
 * @author krenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class JMSOperationLogWriter extends OperationLogWriterBase
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBUserLogQueue";
	private JMSQueueSender sender;

	public JMSOperationLogWriter()
	{
		sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
	}

	protected void writeEntry(LogEntryBase logEntry) throws Exception
	{
		sender.send(logEntry);
	}
}
