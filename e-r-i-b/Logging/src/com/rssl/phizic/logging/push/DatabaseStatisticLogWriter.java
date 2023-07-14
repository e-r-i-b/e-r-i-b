package com.rssl.phizic.logging.push;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.writers.StatisticLogWriter;
import org.hibernate.Session;

/**
 * Класс для записи логируемых данных в бд
 * @author basharin
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class DatabaseStatisticLogWriter implements StatisticLogWriter
{
	public void write(final StatisticLogEntry logEntry) throws Exception
	{
		HibernateExecutor.getInstance(com.rssl.phizic.logging.Constants.DB_INSTANCE_NAME).execute(new HibernateAction<StatisticLogEntry>()
		{
			public StatisticLogEntry run(Session session) throws Exception
			{
				session.save(logEntry);
				session.flush();
				return logEntry;
			}
		});
	}
}
