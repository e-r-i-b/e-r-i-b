package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.StatelessSession;

/**
 * @author Roshka
 * @ created 23.10.2006
 * @ $Author: bogdanov $
 * @ $Revision: 78876 $
 */

public class DatabaseMessageLogWriter extends MessageLogWriterBase
{
	protected void doWrite(final MessagingLogEntryBase logEntry) throws Exception
	{
		HibernateExecutor.getInstance(MessageTranslateHelper.getInstanceName()).execute(new HibernateActionStateless<Void>()
		{
			public Void run(StatelessSession session) throws Exception
			{
				if (logEntry instanceof MessagingLogEntry && MessageTranslateHelper.isFinancial(logEntry.getMessageType()))
					session.insert(new FinancialMessagingLogEntry((MessagingLogEntry) logEntry));
				else
					session.insert(logEntry);
				return null;
			}
		});
	}
}