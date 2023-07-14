package com.rssl.phizic.test.ermb.newclient.accepted;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.test.ErmbMockHibernateExecutor;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для редактирования принятых ОСС подключений
 */
public class ErmbAcceptedRegistrationService
{
	/**
	 * Добавить
	 * @param phone телефон
	 * @param connectedDate дата подключения
	 * @throws BusinessException
	 */
	public void add(final String phone, final Calendar connectedDate) throws BusinessException
	{
		try
		{
			ErmbMockHibernateExecutor.getInstance(true).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(new ErmbAcceptedRegistration(phone, connectedDate));
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
