package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.Query;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gololobov
 * @ created 10.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CategoriesDynamicMoveOperation extends FinancesOperationBase
{
	/**
	 * ������ ��������� �� ���������� �������
	 * @param filter - ������ �������
	 * @return - List<CardOperationCategory> - ������ ���������
	 */
	public List<CardOperationCategory> getCardOperationCategories(final FinanceFilterData filter) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperationCategory>>()
			{
				public List<CardOperationCategory> run(Session session) throws Exception
				{
					org.hibernate.Query query = session.getNamedQuery("com.rssl.phizic.operations.finances.CategoriesDynamicMoveOperation.getOperationCategoriesByParams");
					//��� �������� (true = �������� ��������, false = ���������)
					query.setBoolean("income",filter.isIncome());
					//������� ��������/����������� ��������
					query.setBoolean("cash", filter.isCash());
					//�� ������������
					query.setParameter("login", getLogin());

					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������ ������ ��� ���������� ������� �������� ������� �� ���������
	 * @param filterData - ������ �������
	 * @param categoryId - �� ���������
	 * @return - ������ ������ ��� ������� "�������� �������� ������� � ������� ���������"
	 * @throws BusinessException
	 */
	public List<CategoriesDynamicMoveDescription> getDynamicMoveByCategory(FinanceFilterData filterData, long categoryId) throws BusinessException
	{
		try
		{
			List<String> cards = filterData.isOnlyOwnCards() ? getPersonOwnCards() : getPersonCards();

			//�������� ������� �������� ������� �� ���������
			Query query = createQuery("list");

			//���� ������ ������� (c 00:00:00)
			Calendar fromDate = DateUtils.truncate(filterData.getFromDate(), Calendar.DATE);
			query.setParameter("fromDate",fromDate);
			
			//���� ��������� ������� (�� 23:59:59)
			Calendar toDate = filterData.getToDate();
			toDate.setTime(DateUtils.addDays(toDate.getTime(),1));
			toDate.setTime(DateUtils.truncate(toDate.getTime(),Calendar.DATE));
			toDate.setTime(DateUtils.addSeconds(toDate.getTime(),-1));
			query.setParameter("toDate", toDate);

			//������ (���-�� ����) �� �������� ������������ ������ �������� �� ������ ����, ����� ����� ���-��
			//������ ���������� ������ �� "group_day_count" ����
			query.setParameter("everyDayLimit",EVERY_DAY_LIMIT);
			query.setParameter("groupDayCount",GROUP_DAY_COUNT);
			//�� ��������� ��������� ��� NULL
			query.setParameter("categoryId", categoryId);
			//������� ���������� �� �������� � ��������� (null-���������� ���, 0-���������� ������ ������)
			query.setParameter("cash", filterData.isCash() ? null : 0);
			//������ ���� �� ������� ����� ���������� ��������
			query.setParameter("cardsNums", cards);
			//�� ������������
			query.setParameter("loginId", getLogin().getId());
			List<CategoriesDynamicMoveDescription> cardOperationsList = query.executeList();

			return cardOperationsList;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("������ ��� �������� �������� �������� ������� �� ���������: ",e);
		}
	}

	/**
	 * ��������� ���������� �������� ��������� ���������.
	 * ���� �� ��������� ��������� ������� �� "0" �� ������ �� ������
	 * @param categoriesList - ������ ��������� ���������
	 * @param currentCategoryId - ������� ��������� ���������
	 * @return
	 */
	public long getDefaultCurrentCategoryId(List<CardOperationCategory> categoriesList, long currentCategoryId)
	{
		if (CollectionUtils.isNotEmpty(categoriesList))
		{
			for (CardOperationCategory cat : categoriesList)
			{
				if (cat.getId() == currentCategoryId)
					return currentCategoryId;
			}
			return categoriesList.iterator().next().getId();
		}
		return currentCategoryId;
	}
}

