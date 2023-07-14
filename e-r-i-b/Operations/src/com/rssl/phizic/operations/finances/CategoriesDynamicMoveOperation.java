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
	 * Список категорий по параметрам фильтра
	 * @param filter - данные фильтра
	 * @return - List<CardOperationCategory> - список категорий
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
					//Тип операции (true = доходные операции, false = расходные)
					query.setBoolean("income",filter.isIncome());
					//Признак наличная/безналичная операция
					query.setBoolean("cash", filter.isCash());
					//ИД пользователя
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
	 * Список данных для построения графика движения средств по категории
	 * @param filterData - данные фильтра
	 * @param categoryId - ИД категории
	 * @return - список данных для графика "Динамика движения средств в разрезе категории"
	 * @throws BusinessException
	 */
	public List<CategoriesDynamicMoveDescription> getDynamicMoveByCategory(FinanceFilterData filterData, long categoryId) throws BusinessException
	{
		try
		{
			List<String> cards = filterData.isOnlyOwnCards() ? getPersonOwnCards() : getPersonCards();

			//Загрузка графика движения средств по категории
			Query query = createQuery("list");

			//Дата начала периода (c 00:00:00)
			Calendar fromDate = DateUtils.truncate(filterData.getFromDate(), Calendar.DATE);
			query.setParameter("fromDate",fromDate);
			
			//Дата окончания периода (до 23:59:59)
			Calendar toDate = filterData.getToDate();
			toDate.setTime(DateUtils.addDays(toDate.getTime(),1));
			toDate.setTime(DateUtils.truncate(toDate.getTime(),Calendar.DATE));
			toDate.setTime(DateUtils.addSeconds(toDate.getTime(),-1));
			query.setParameter("toDate", toDate);

			//Период (кол-во дней) до которого включительно график строится за каждый день, сверх этого кол-ва
			//график группирует данные по "group_day_count" дней
			query.setParameter("everyDayLimit",EVERY_DAY_LIMIT);
			query.setParameter("groupDayCount",GROUP_DAY_COUNT);
			//Ид выбранной категории или NULL
			query.setParameter("categoryId", categoryId);
			//Признак показывать ли операции с наличными (null-показывать все, 0-показывать только безнал)
			query.setParameter("cash", filterData.isCash() ? null : 0);
			//Номера карт по которым нужно показывать операции
			query.setParameter("cardsNums", cards);
			//ИД пользователя
			query.setParameter("loginId", getLogin().getId());
			List<CategoriesDynamicMoveDescription> cardOperationsList = query.executeList();

			return cardOperationsList;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Ошибка при загрузке динамики движения средств по категории: ",e);
		}
	}

	/**
	 * Установка дефолтного значения выбранной категории.
	 * Если ИД выбранной категории отлична от "0" то ничего не меняем
	 * @param categoriesList - список доступных категорий
	 * @param currentCategoryId - текущая выбранная категория
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

