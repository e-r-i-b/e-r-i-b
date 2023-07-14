package com.rssl.phizic.logging.operations;

import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import org.hibernate.StatelessSession;

/**
 * @author krenev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class DatabaseOperationLogWriter extends OperationLogWriterBase
{
	protected void writeEntry(final LogEntryBase logEntry) throws Exception
	{
		HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateActionStateless<Void>()
		{
			public Void run(StatelessSession session) throws Exception
			{
				String parameters = logEntry.getParameters();
				// todo Временное решение, для корректной записи в таблицу GUEST_USERLOG
				if (logEntry instanceof GuestLogEntry && parameters.length() > 4000)
				{
					parameters = parameters.substring(0, 4000);
					logEntry.setParameters(parameters);
				}
				session.insert(logEntry);
				return null;
			}
		});
	}
}
