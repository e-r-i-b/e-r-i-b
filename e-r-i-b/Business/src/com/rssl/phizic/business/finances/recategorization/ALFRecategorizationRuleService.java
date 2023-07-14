package com.rssl.phizic.business.finances.recategorization;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author lepihina
 * @ created 31.03.14
 * $Author$
 * $Revision$
 * Сервис для работы с правилами перекатегоризации
 */
public class ALFRecategorizationRuleService
{
	private static final String QUERY_PREFIX = ALFRecategorizationRuleService.class.getName() + ".";
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Добавление нового правила перекатегоризации
	 * @param recategorizationRule - правило перекатегоризации
	 * @throws BusinessException
	 */
	public void add(ALFRecategorizationRule recategorizationRule) throws BusinessException, BusinessLogicException
	{
		simpleService.addOrUpdateWithConstraintViolationException(recategorizationRule);
	}

	/**
	 * Удаление правила перекатегоризации
	 * @param recategorizationRule - правило перекатегоризации
	 * @throws BusinessException
	 */
	public void remove(ALFRecategorizationRule recategorizationRule) throws BusinessException, BusinessLogicException
	{
		simpleService.remove(recategorizationRule);
	}

	/**
	 * Поиск правила перекатегоризации по операции
	 * @param operation - операция
	 * @return правило перекатегоризации
	 * @throws BusinessException
	 */
	public ALFRecategorizationRule findRecategorizationRuleByOperation(final CardOperation operation) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ALFRecategorizationRule>()
			{
				public ALFRecategorizationRule run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findRecategorizationRuleByOperation");
					query.setLong("loginId", operation.getOwnerId());
					query.setParameter("mccCode", operation.getMccCode());
					query.setString("description", operation.getOriginalDescription());
					return (ALFRecategorizationRule) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление правила перекатегоризации для категории
	 * @param categoryId - идентификатор категории
	 * @throws BusinessException
	 */
	public void removeRecategorizationRuleByCategory(final Long categoryId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "removeRecategorizationRuleByCategory");
					query.setLong("categoryId", categoryId);
					query.executeUpdate();
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
	 * Возвращает список всех правил перекатегоризации по клиенту
	 * @param loginId - логин клиента
	 * @return список правил перекатегоризации
	 * @throws BusinessException
	 */
	public List<ALFRecategorizationRule> getAllClientRules(Long loginId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ALFRecategorizationRule.class);
		criteria.add(Expression.eq("loginId", loginId));
		return simpleService.find(criteria);
	}

	/**
	 * Проверяет наличие правил перекодировки операций, привязанных к категории
	 * @param categoryId идентификатор категории
	 * @return признак наличия правил перекатегоризации
	 */
	public boolean existRecategorisationRule(final Long categoryId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "existRecategorizationRule");
					query.setLong("categoryId", categoryId);
					return (Integer) query.uniqueResult() > 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
