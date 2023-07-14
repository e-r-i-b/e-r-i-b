package com.rssl.phizic.business.externalsystem;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author Pankin
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class AutoStopSystemService extends SimpleService
{
	/**
	 * Получить список идентификаторов внешних систем и количество ошибок недоступности, зафиксированных со
	 * времени fromTime
	 * @param fromTime время, начиная с которого ищем ошибки недоступности ВС
	 * @return список
	 */
	public List<Object[]> getExternalSystemOfflineErrors(final Calendar fromTime) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{

				public List<Object[]> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(AutoStopSystemService.class.getName() + ".getExternalSystemOfflineErrors");
					query.setParameter("fromTime", fromTime);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
