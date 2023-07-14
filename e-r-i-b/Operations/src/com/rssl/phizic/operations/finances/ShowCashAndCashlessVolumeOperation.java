package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gololobov
 * @ created 17.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowCashAndCashlessVolumeOperation extends FinancesOperationBase
{
	/**
	 * Данные для постороения графика "Доля наличных операций в структуре финансовых потоков"
	 * @param filter - данные фильтра
	 * @return List - данные для постоения графига "Доля наличных операций в структуре финансовых потоков"
	 * @throws BusinessException
	 */
	public List<CashAndCashlessVolumeDescription> getCashOperationsToFinancialStream(FinanceFilterData filter) throws BusinessException
	{
		try
		{
			//Карты, по которым получать данные
			List<String> cards = filter.isOnlyOwnCards() ? getPersonOwnCards() : getPersonCards();

			//Загрузка графика движения средств по категории
			Query query = createQuery("list");

			//Дата начала периода (c 00:00:00)
			Calendar fromDate = DateUtils.truncate(filter.getFromDate(), Calendar.DATE);
			query.setParameter("fromDate",fromDate);

			//Дата окончания периода (до 23:59:59)
			Calendar toDate = filter.getToDate();
			toDate.setTime(DateUtils.addDays(toDate.getTime(),1));
			toDate.setTime(DateUtils.truncate(toDate.getTime(),Calendar.DATE));
			toDate.setTime(DateUtils.addSeconds(toDate.getTime(),-1));
			query.setParameter("toDate", toDate);

			//true - доход, false - расход
			query.setParameter("income",filter.isIncome() ? 1:0);
			//Период (кол-во дней) до которого включительно график строится за каждый день, сверх этого кол-ва
			//график группирует данные по "group_day_count" дней
			query.setParameter("everyDayLimit",EVERY_DAY_LIMIT);
			query.setParameter("groupDayCount",GROUP_DAY_COUNT);
			//Номера карт по которым нужно показывать операции
			query.setParameter("cardsNums", cards);
		    //ИД пользователя
			query.setParameter("loginId", getLogin().getId());

			List<CashAndCashlessVolumeDescription> cardOperationsList = query.executeList();

			return cardOperationsList;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Ошибка при загрузке объема наличных и безналичных операций: ",e);
		}
	}
}
