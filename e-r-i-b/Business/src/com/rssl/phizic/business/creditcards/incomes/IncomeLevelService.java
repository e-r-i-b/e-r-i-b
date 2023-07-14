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
 * ������ ��� ������ � ����������� ������������ ������� ������� ��������� ��������� ������� (��������� ���������)
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class IncomeLevelService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * �������� ������������ ������ ��������� ��������� �������
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
	 * �������� ������������ ������ ��������� ��������� �������
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
	 * ������� ������������ ������ ��������� ��������� �������
	 * @param incomeLevel
	 */
	public void remove(final IncomeLevel incomeLevel) throws BusinessException
	{
		simpleService.remove(incomeLevel);
	}

	/**
	 * ����� �� id
	 * @param id id
	 * @return ������������ ������ ��������� ��������� �������
	 * @throws BusinessException
	 */
	public IncomeLevel findById ( final Long id ) throws BusinessException
	{
		IncomeLevel incomeLevel = simpleService.findById(IncomeLevel.class,id);
		if (incomeLevel == null)
			return null;
		if((incomeLevel.getMinIncome() != null && !incomeLevel.getMinIncome().getCurrency().getNumber().equals("643"))
				|| (incomeLevel.getMaxIncome() != null && !incomeLevel.getMaxIncome().getCurrency().getNumber().equals("643")))
			throw new BusinessException("����� ������ �� � ������.");

		for(IncomeCondition condition : incomeLevel.getConditions())
			if(!condition.getMinCreditLimit().getValue().getCurrency().getNumber().equals(condition.getCurrency().getNumber())
					|| !condition.getMaxCreditLimit().getValue().getCurrency().getNumber().equals(condition.getCurrency().getNumber()))
				throw new BusinessException("������ ������������ ��� ������������� ������ �� ������������� ������ �������.");
		
		return incomeLevel;
	}

	/**
	 * ������ ���� ������������ ������ ��������� ��������� �������
	 * @return ������ ���� ������������ ������ ��������� ��������� �������
	 * @throws BusinessException
	 */
	public List<IncomeLevel> getAll() throws BusinessException
	{
		return simpleService.getAll(IncomeLevel.class);
	}

	/**
	 * �������� ������������� ������� ������
	 * @return true ���� ������ ����; ����� - false
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
	 * ��������� ������������� ��������� ��������� �� ���������� ������
	 * @param incomeId - id ������
	 * @return - true - ����������, false - ���
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
	 * ������ ������� ������������ ������ ��������� ��������� �������
	 * @param incomeLevel
	 * @return ������ �������
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
