package com.rssl.phizic.business.commission;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;
import java.util.Collection;

/**
 * @author Evgrafov
 * @ created 11.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

public class CommissionService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Сохранить (новый или уже существующий)
	 * @param type
	 * @throws BusinessException
	 */
	public void saveType(CommissionType type) throws BusinessException
	{
		simpleService.add(type);
	}

	/**
	 * Обновить правила вычисления комисии для заданного типа
	 * @param type тип комиссии
	 * @param rules правила (только новые правила, обновление существующих не поддерживается!)
	 * @throws BusinessException
	 */
	public void updateRules(final CommissionType type, final Collection<? extends CommissionRule> rules) throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.removeByType");
			        query.setParameter("type", type).executeUpdate();

			        for (CommissionRule rule : rules)
			        {
				        rule.setType(type);
				        session.saveOrUpdate(rule);
			        }

		            return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Найти правило расчета комиссии для заданного типа комиссии и валюты
	 * @param type тип комисии
	 * @param currencyCode код валюты
	 * @return найденное правило или null
	 * @throws BusinessException
	 */
	public CommissionRule findRule(final CommissionType type, final String currencyCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CommissionRule>()
			{
				public CommissionRule run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.findRule");
					return (CommissionRule) query.setParameter("type", type)
							.setParameter("currencyCode", currencyCode)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти все правила расчета комиссии для заданного типа
	 * @param type тип комиссии
	 * @return список типов комиссии
	 */
	public List<? extends CommissionRule> findRules(final CommissionType type) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<? extends CommissionRule>>()
		    {
		        public List<? extends CommissionRule> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.findRules");
			        //noinspection unchecked
			        return (List<? extends CommissionRule>) query.setParameter("type", type)
					        .list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Найти тип комиссии по его ID
	 * @param commissionId ID комиссии
	 * @return тип комиссии
	 */
	public CommissionType findTypeById(final Long commissionId) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<CommissionType>()
		    {
		        public CommissionType run(Session session) throws Exception
		        {
		            return (CommissionType) session.get(CommissionType.class, commissionId);
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Найти тип комиссии по его ключу
	 * @param typeKey ключ
	 * @return найденный тип комиссии
	 */
	public CommissionType findTypeByKey(final String typeKey) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CommissionType>()
			{
				public CommissionType run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.commission.CommissionService.typeByKey");
					return (CommissionType) query.setParameter("key", typeKey).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}