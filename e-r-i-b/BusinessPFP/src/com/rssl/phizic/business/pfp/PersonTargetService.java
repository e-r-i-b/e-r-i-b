package com.rssl.phizic.business.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * Сервис для работы с целями клиента
 * 
 * @author lepihina
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonTargetService
{
	private static final String PREFIX_QUERY = PersonTargetService.class.getName() + ".";

	/**
	 * Может ли добавить клиент еще одну цель заданного типа
	 * @param id - идентификатор
	 * @param pfpId - идентификатор ПФП
	 * @param currentId - идентификатор текущей (редактируемой) цели
	 * @return true - цель данного типа можно добавить
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public boolean canAddMoreOneTarget(final Long id, final Long pfpId, final Long currentId) throws BusinessException
	{
        try
		{
			return HibernateExecutor.getInstance(PFPConstants.INSTANCE_NAME).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PREFIX_QUERY + "canAddMoreOneTarget");
					query.setParameter("id", id);
					query.setParameter("pfpId", pfpId);
					query.setParameter("currentId", currentId, Hibernate.LONG);
					return Boolean.parseBoolean((String)query.uniqueResult());
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
    }

	/**
	 * Поиск у клиента цели с признаком "дата достижения цели позже остальных"
	 * @param pfpId - идентификатор ПФП
	 * @param currentId - идентификатор текущей (редактируемой) цели
	 * @return цель с признаком "дата достижения цели позже остальных"
	 * @throws BusinessException
	 */
	public PersonTarget findLaterAllPersonTarget(final Long pfpId, final Long currentId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(PFPConstants.INSTANCE_NAME).execute(new HibernateAction<PersonTarget>()
			{
				public PersonTarget run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PREFIX_QUERY + "findLaterAllPersonTarget");
					query.setParameter("pfpId", pfpId);
					query.setParameter("currentId", currentId, Hibernate.LONG);
					return (PersonTarget) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение максимальной даты достижения цели клиента среди всех его целей
	 * @param pfpId - идентификатор ПФП
	 * @param currentId - идентификатор текущей (редактируемой) цели
	 * @return максимальная дата достижения цели
	 * @throws BusinessException
	 */
	public Calendar getCurrentMaxDate(final Long pfpId, final Long currentId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(PFPConstants.INSTANCE_NAME).execute(new HibernateAction<Calendar>()
			{
				public Calendar run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PREFIX_QUERY + "getCurrentMaxDate");
					query.setParameter("pfpId", pfpId);
					query.setParameter("currentId", currentId, Hibernate.LONG);
					return (Calendar) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
