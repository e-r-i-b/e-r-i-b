package com.rssl.phizic.logging.logon;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.logon.LogonLogEntry;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.CalendarType;

import java.util.Calendar;

/**
 * @author komarov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoggingJournalService
{

	/**
	 * Создает запись журнала регистрации входов
	 * @param entry запись журнала регистрации входов
	 * @return сохраненная запись журнала регистрации входов
	 * @throws Exception
	 */
	public LogonLogEntry add( final LogonLogEntry entry) throws LogicException, SystemException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<LogonLogEntry>()
				{
					public LogonLogEntry run(Session session) throws Exception
					{
						session.save(entry);
						session.flush();
						return entry;
					}
				}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw new LogicException(e);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}
}
