package com.rssl.auth.csa.front.business.blockingrules;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.business.BusinessException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Calendar;

/**
 * Сервис для проверки правил блокировки
 * @author niculichev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class CheckBlockingRuleService
{
	private static final String CSA_INSTANCE_NAME = "CSA2";
	private static final String PREFIX_QUERY = CheckBlockingRuleService.class.getName() + ".";

	/**
	 * Включена ли глобальная блокировка входа
	 * @return true - включена
	 * @throws SystemException
	 */
	public boolean isGlobalBloking() throws SystemException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PREFIX_QUERY + "isGlobalBloking");
					return Boolean.parseBoolean((String)query.uniqueResult());
				}
			});
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * Возвращает список активных уведомлений об ограничении входа
	 * @return список уведомлений об ограничении входа
	 * @throws BusinessException
	 */
	public List<BlockingRules> getRegionBlokingRules() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<List<BlockingRules>>()
			{
				public List<BlockingRules> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PREFIX_QUERY + "regionBlokingList");
					query.setParameter("publishDate", Calendar.getInstance());
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
