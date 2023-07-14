package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author gladishev
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */

class SessionComposerQueryHelper
{
	/**
	 * Возвращает идентификаторы логинов владельцев ресурсов (счетов, карт, кредитов)
	 * @param resourceClass - класс ресурса
	 * @param number - номер счета
	 * @return список идентфикаторов пользователей
	 */
	static List<Long> getLoginIdsOfOwners(final Class resourceClass, final String number) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(resourceClass.getCanonicalName() + ".getOwnersIds");
					query.setParameter("number", number);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка при получении списка иденификаторов пользователей для ресурса "
					+ resourceClass.getCanonicalName() + " с номером " + number, e);
		}
	}
}
