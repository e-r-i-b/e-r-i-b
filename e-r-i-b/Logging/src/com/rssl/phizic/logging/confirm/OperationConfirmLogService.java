package com.rssl.phizic.logging.confirm;

import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author lukina
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class OperationConfirmLogService
{
	/**
	 * Добавляет в лог запрос на ввод пароля
	 * @param entry  запрос на ввод пароля
	 * @return сохраненная запись
	 * @throws Exception
	 */
	public OperationConfirmLogEntry add( final OperationConfirmLogEntry entry) throws LogicException, SystemException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<OperationConfirmLogEntry>()
				{
					public OperationConfirmLogEntry run(Session session) throws Exception
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
