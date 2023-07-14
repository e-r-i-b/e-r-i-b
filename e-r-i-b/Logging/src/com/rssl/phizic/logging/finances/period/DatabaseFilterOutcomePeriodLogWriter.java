package com.rssl.phizic.logging.finances.period;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.FilterOutcomePeriodLogWriter;
import org.hibernate.Session;


/**
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer для логирования записей о выборе периода фильтрации расходов.
 */
public class DatabaseFilterOutcomePeriodLogWriter implements FilterOutcomePeriodLogWriter
{
	public void write(final FilterOutcomePeriodLogRecord logEntry) throws Exception
	{
		try
		{
			HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<FilterOutcomePeriodLogRecord>()
			{
				public FilterOutcomePeriodLogRecord run(Session session) throws Exception
				{
					session.save(logEntry);
					session.flush();
					return logEntry;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}
}
