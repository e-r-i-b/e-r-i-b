package com.rssl.phizic.business.mobileOperators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 04.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с мобильными операторами
 * 
 */

public class MobileOperatorService
{
	private final static SimpleService simpleService = new SimpleService();
	private final static int MAX_COUNT_MOBILE_OPERATORS = 101;

	/**
	 * Сохраняет или обновляет объект в БД
	 * @param operator - объект для сохранения/обновления
	 * @throws BusinessException
	 */
	public void save(MobileOperator operator) throws BusinessException
	{
		simpleService.addOrUpdate(operator, null);
	}

	/**
	 * Удаление объекта из БД
	 * @param operator - объект для удаления
	 * @throws BusinessException
	 */
	public void remove(MobileOperator operator) throws BusinessException
	{
		simpleService.remove(operator, null);
	}

	/**
	 * Удаление всех мобильных операторов из БД
	 * @throws BusinessException
	 */
	public void removeAll() throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.mobileoperators.MobileOperator.removeAll");
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
	 * Найти мобильного оператора по id  в БД
	 * @param id - идентификатор моб. оператора, по которому ищем
	 * @return найденный мобильный оператор или null
	 * @throws BusinessException
	 */
	public MobileOperator findById(Long id) throws BusinessException
	{
		return simpleService.findById(MobileOperator.class, id, null);
	}

	/**
	 * Получание всех мобильных операторов из БД, отсортированных по названию
	 * @throws BusinessException
	 */
	public List<MobileOperator> getAllSortByName() throws BusinessException
	{
		try
		{
			List<MobileOperator> mobileOperators =  HibernateExecutor.getInstance().execute(new HibernateAction<List<MobileOperator>>()
			{
				public List<MobileOperator> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.operations.dictionaries.mobileoperators.ShowMobileOperatorsOperation.sortByName");
					query.setMaxResults(MAX_COUNT_MOBILE_OPERATORS);
					return query.list();
				}
			});

			if (mobileOperators != null && mobileOperators.size() >= MAX_COUNT_MOBILE_OPERATORS)
				throw new BusinessException("Список мобильных операторов содержит более " + (MAX_COUNT_MOBILE_OPERATORS - 1) + " элементов");

			return mobileOperators;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
