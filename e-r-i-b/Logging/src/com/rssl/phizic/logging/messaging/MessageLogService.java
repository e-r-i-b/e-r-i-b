package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Omeliyanchuk
 * @ created 29.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class MessageLogService
{
	private static volatile MessageLogWriter logWriter;

	public MessagingLogEntry findByRequestId(final String requestId) throws Exception
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<MessagingLogEntry>()
			{
				public MessagingLogEntry run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.logging.findDocumentByRequestId");
					query.setParameter("requestId", requestId);
					//noinspection unchecked
					return (MessagingLogEntry)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public static MessagingLogEntry createLogEntry()
	{
		return new MessagingLogEntry();
	}

	public static MessageLogWriter getMessageLogWriter()
	{
		if (logWriter == null)
		{
			synchronized (MessageLogService.class)
			{
				if (logWriter == null)
				{
					logWriter = new BackupSupportMessagesLogWriter();
				}
			}
		}
		return logWriter;
	}
}
