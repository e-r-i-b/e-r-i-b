package com.rssl.phizic.business.dictionaries.city;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author lepihina
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CityService
{
	public static final String DEFAULT_CITY_CODE = "27"; //код ћосквы в справочнике городов

	/**
	 * ѕолучить город по коду
	 * @param code код
	 * @return город из справочника гордов
	 * @throws BusinessException
	 */
	public City getByCode(final String code) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<City>()
			{
				public City run(Session session)
				{
					Query query = session.getNamedQuery(City.class.getName() + ".getByCode");
					query.setString("code", code);
					return (City) query.uniqueResult();
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
