package com.rssl.phizic.logging.confirm;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.jms.JMSQueueSender;

/**
 * @author Krenev
 * @ created 22.06.2015
 * @ $Author$
 * @ $Revision$
 */

public class JMSOperationConfirmLogWriter extends OperationConfirmLogWriterBase
{
	private final Object LOCK = new Object();
	public static final String FACTORY_NAME = "jms/node/LoggingQCF";
	public static final String QUEUE_NAME = "jms/node/OperationConfirmLogQueue";
	private volatile JMSQueueSender sender;

	protected void doSave(OperationConfirmLogEntry entry) throws Exception
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
