package com.rssl.phizic.logging.offerNotification;

import com.rssl.phizic.dataaccess.hibernate.HibernateActionStateless;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.OfferNotificationLogWriter;
import org.hibernate.StatelessSession;


/**
 * @author lukina
 * @ created 08.02.2014
 * @ $Author$
 * @ $Revision$
 * Writer для логирования статистики по уведомлениям о предодобренных предложениях
 */
public class DatabaseNotificationLogWriter implements OfferNotificationLogWriter
{
	public void write(final NotificationLogEntry entry) throws Exception
	{
		HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateActionStateless<Void>()
		{
			public Void run(StatelessSession session) throws Exception
			{
				session.insert(entry);
				return null;
			}
		});
	}
}
