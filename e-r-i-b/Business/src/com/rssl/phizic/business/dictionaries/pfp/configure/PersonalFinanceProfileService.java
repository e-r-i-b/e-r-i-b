package com.rssl.phizic.business.dictionaries.pfp.configure;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author akrenev
 * @ created 30.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * сервис для работы с модулем
 */

public class PersonalFinanceProfileService
{
	/**
	 * @param owner логин пользователя
	 * @return имеет ли клиент планирования
	 */
	public boolean havePlaning(final Login owner) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.pfp.configure.planingCount");
					query.setParameter("loginId", owner.getId());
					return ((Integer) query.uniqueResult()) > 0;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
