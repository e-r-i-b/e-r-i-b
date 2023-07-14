package com.rssl.phizic.business.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * @author lepihina
 * @ created 25.09.14
 * $Author$
 * $Revision$
 *
 * ������ ��� ������ � ��������������� � ���
 */
public class BudgetService
{
	private static final String QUERY_PREFIX = BudgetService.class.getName() + ".";
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������� ��� ��������� ������
	 * @param budget ������
	 * @throws BusinessException
	 */
	public void addOrUpdateBudget(Budget budget) throws BusinessException
	{
		simpleService.addOrUpdate(budget);
	}

	/**
	 * ��������� ������ ��������
	 * @param budgets �������
	 * @throws BusinessException
	 */
	public void addBudgetsList(List<Budget> budgets) throws BusinessException
	{
		simpleService.addList(budgets);
	}

	/**
	 * ������� ������
	 * @param budget ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void removeBudget(Budget budget) throws BusinessException
	{
		simpleService.remove(budget);
	}

	/**
	 * ���������� ������ �� ������ � ���������
	 * @param login �����
	 * @param categoryId ������������� ���������
	 * @return ������
	 * @throws BusinessException
	 */
	public Budget findBudgetByLoginAndCategory(Login login, Long categoryId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Budget.class);
		criteria.add(Expression.eq("login", login));
		criteria.add(Expression.eq("categoryId", categoryId));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ���������� ������ �� ������
	 * @param login �����
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Budget> findBudgetByLogin(Login login) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Budget.class);
		criteria.add(Expression.eq("login", login));
		return simpleService.find(criteria);
	}

	/**
	 * ���������� ������� ��-��������� ��� ���������
	 * @param fromDate ���� ��
	 * @param toDate ���� ��
	 * @param maxLoadDate ������������ ���� �������� ��������
	 * @param cards ������ ����
	 * @param login �����
	 * @return ����������� �������(����� ��������) �� ����������
	 */
	public List getCategoriesDefaultBudgets(final Calendar fromDate, final Calendar toDate, final Calendar maxLoadDate,
	                                                final List<String> cards, final Login login) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List>()
			{
				public List run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getCategoriesDefaultBudgets");
					query.setCalendar("fromDate", fromDate);
					query.setCalendar("toDate", toDate);
					query.setCalendar("maxLoadDate", maxLoadDate);
					query.setParameter("loginId", login.getId());
					if (cards.isEmpty())
					{
						query.setBoolean("cardNumbersEmpty", true);
						query.setParameter("cardNumbers", "");
					}
					else
					{
						query.setBoolean("cardNumbersEmpty", false);
						query.setParameterList("cardNumbers", cards);
					}
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
