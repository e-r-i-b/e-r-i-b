package com.rssl.phizic.logging.settings;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.UserMessageLogWriter;
import org.hibernate.Session;

/**
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer дл€ логировани€ отправки оповещений клиенту
 */
public class DatabaseUserMessageLogWriter implements UserMessageLogWriter
{

	public void write(final UserMessageLogRecord entry) throws Exception
	{
		HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<UserMessageLogRecord>()
		{
			public UserMessageLogRecord run(Session session) throws Exception
			{
				session.save(entry);
				session.flush();
				return entry;
			}
		});
	}
}
