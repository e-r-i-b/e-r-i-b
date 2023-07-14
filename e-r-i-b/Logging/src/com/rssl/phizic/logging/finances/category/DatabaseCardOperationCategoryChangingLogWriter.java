package com.rssl.phizic.logging.finances.category;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.CardOperationCategoryChangingLogWriter;
import org.hibernate.Session;

/**
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer для асинхронного логирования записей о изменении клиентом категорий
 */
public class DatabaseCardOperationCategoryChangingLogWriter  implements CardOperationCategoryChangingLogWriter
{
	public void write(final CardOperationCategoryChangingLogEntry logEntry) throws Exception
	{
		try
		{
			HibernateExecutor.getInstance(Constants.DB_INSTANCE_NAME).execute(new HibernateAction<CardOperationCategoryChangingLogEntry>()
			{
				public CardOperationCategoryChangingLogEntry run(Session session) throws Exception
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
