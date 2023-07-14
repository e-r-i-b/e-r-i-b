package com.rssl.phizic.business.creditcards.incomes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.creditcards.conditions.IncomeCondition;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Сервис для работы с настройками соответствия доходов клиента доступным кредитным лимитам (доступным продуктам)
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class IncomeLevelService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Добавить соответствие дохода доступным кредитным лимитам
	 * @param incomeLevel
	 */
	public void add(final IncomeLevel incomeLevel) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdate(incomeLevel);
					for(IncomeCondition condition : incomeLevel.getConditions())
						simpleService.add(condition);
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
	 * Обновить соответствие дохода доступным кредитным лимитам
	 * @param incomeLevel
	 */
	public void update(final IncomeLevel incomeLevel) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdate(incomeLevel);

					List<IncomeCondition> oldConditions = getConditionsByProduct(incomeLevel);
					oldConditions.removeAll(incomeLevel.getConditions());
					for(IncomeCondition deletedCondition : oldConditions)
						simpleService.remove(deletedCondition);
					for(IncomeCondition condition : incomeLevel.getConditions())
						simpleService.addOrUpdate(condition);
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
	 * Удалить соответствие дохода доступным кредитным лимитам
	 * @param incomeLevel
	 */
	public void remove(final IncomeLevel incomeLevel) throws BusinessException
	{
		simpleService.remove(incomeLevel);
	}

	/**
	 * Поиск по id
	 * @param id id
	 * @return соответствие дохода доступным кредитным лимитам
	 * @throws BusinessException
	 */
	public IncomeLevel findById ( final Long id ) throws BusinessException
	{
		IncomeLevel incomeLevel = simpleService.findById(IncomeLevel.class,id);
		if (incomeLevel == null)
			return null;
		if((incomeLevel.getMinIncome() != null && !incomeLevel.getMinIncome().getCurrency().getNumber().equals("643"))
				|| (incomeLevel.getMaxIncome() != null && !incomeLevel.getMaxIncome().getCurrency().getNumber().equals("643")))
			throw new BusinessException("Доход указан не в рублях.");

		for(IncomeCondition condition : incomeLevel.getConditions())
			if(!condition.getMinCreditLimit().getValue().getCurrency().getNumber().equals(condition.getCurrency().getNumber())
					|| !condition.getMaxCreditLimit().getValue().getCurrency().getNumber().equals(condition.getCurrency().getNumber()))
				throw new BusinessException("Валюта минимального или максимального лимита не соответствует валюте условия.");
		
		return incomeLevel;
	}

	/**
	 * Список всех соответствий дохода доступным кредитным лимитам
	 * @return Список всех соответствий дохода доступным кредитным лимитам
	 * @throws BusinessException
	 */
	public List<IncomeLevel> getAll() throws BusinessException
	{
		return simpleService.getAll(IncomeLevel.class);
	}

	/**
	 * Проверка существования уровней дохода
	 * @return true если записи есть; иначе - false
	 * @throws BusinessException
	 */
	public Boolean isIncomeLevelsExists() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(IncomeLevel.class.getName() + ".isIncomeLevelsExists");
					return !query.uniqueResult().equals(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверяет существование кредитных продуктов по выбранному доходу
	 * @param incomeId - id дохода
	 * @return - true - существуют, false - нет
	 * @throws Exception
	 */
	public boolean productByIncomeExists(final Long incomeId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.operations.loans.offer.LoanOfferListOperation.findCountProductByIncome");
					query.setParameter("incomeId", incomeId);

					return ((Long) query.uniqueResult()) > 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Список условий соответствия дохода доступным кредитным лимитам
	 * @param incomeLevel
	 * @return Список условий
	 * @throws BusinessException
	 */
	private List<IncomeCondition> getConditionsByProduct(final IncomeLevel incomeLevel) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<IncomeCondition>>()
		{
			public List<IncomeCondition> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery(IncomeLevel.class.getName() + ".getConditionsByProduct");
				query.setParameter("incomeId", incomeLevel.getId());
				return query.list();
			}
		});
	}
}
