package com.rssl.phizic.business.descriptions;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.business.BusinessException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

/**
 * —ервис дл€ раширенных описаний данных
 * @author niculichev
 * @ created 09.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedDescriptionDataService
{
	/**
	 * получить данные по имени
	 * @param name им€
	 * @return расширенное описание данных
	 * @throws Exception
	 */
	public ExtendedDescriptionData getDataByName(final String name) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ExtendedDescriptionData>()
			{
				public ExtendedDescriptionData run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(ExtendedDescriptionData.class);
					criteria.add(Expression.eq("name", name));

					return (ExtendedDescriptionData) criteria.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}
}
