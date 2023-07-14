package com.rssl.phizic.logging.settings;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.UserNotificationLogWriter;
import org.hibernate.Session;

/**
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer для логирования изменений настроек оповещений
 */
public class DatabaseUserNotificationLogWriter implements UserNotificationLogWriter
{
	public void write(final UserNotificationLogRecord logEntry) throws Exception
	{
		HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<UserNotificationLogRecord>()
		{
			public UserNotificationLogRecord run(Session session) throws Exception
			{
				session.save(logEntry);
				session.flush();
				return logEntry;
			}
		});
	}
}
