package com.rssl.phizic.operations.finances.financeCalendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDateType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.finances.FilterCardFinanceOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.operations.finances.FinanceHelper.CASH_PAYMENTS_ID;

/**
 * @author lepihina
 * @ created 26.03.14
 * $Author$
 * $Revision$
 * ѕросмотр финансового календар€.
 */
public class ShowFinanceCalendarOperation extends FilterCardFinanceOperation
{
	//  урс валют берем дл€ такого же “Ѕ, как и на странице "ƒоступные средства"
	private static final String REGION_ID = "99";

	private static final CardOperationService cardOperationService = new CardOperationService();
	private static final DepartmentService departmentService = new DepartmentService();

	private List<CalendarDataDescription> calendarData;
	private List<CardLink> selectedCards;

	/**
	 * ‘ормирует список из данных дл€ финансового календар€ (данные отсортированы по дате)
	 * @param filterData - данные фильтрации
	 * @param cardIds - идентификаторы карт, по которым получаютс€ данные
	 */
	public void calculateCalendarData(FinanceFilterData filterData, List<Long> cardIds) throws BusinessException
	{
		selectedCards = getSelectedCards(cardIds);
		boolean showCashPayments = filterData.getShowCashPayments() != null ? filterData.getShowCashPayments() : cardIds.contains(CASH_PAYMENTS_ID);

		calendarData = new ArrayList<CalendarDataDescription>();
		List<CalendarDataDescription> daysWithNotNullData = cardOperationService.getCalendarData(filterData.getFromDate(), filterData.getToDate(), getLogin().getId(),
				getCardNumbersList(selectedCards), showCashPayments, filterData.isCash(), filterData.getExcludeCategories(), filterData.getOperationCountry());

		Calendar date = (Calendar) filterData.getFromDate().clone();
		Calendar currentDate = DateHelper.getCurrentDate();
		int index = 0;
		while(date.before(filterData.getToDate()))
		{
			Calendar dataDate = (Calendar) date.clone();
			CalendarDateType dateType = CalendarDateType.TODAY;
			if (dataDate.before(currentDate))
			{
				dateType = CalendarDateType.PAST;
			}
			else if (dataDate.after(currentDate))
			{
				dateType = CalendarDateType.FUTURE;
			}

			CalendarDataDescription dataDescription = null;
			if (index < daysWithNotNullData.size() && daysWithNotNullData.get(index).getDate().equals(date))
			{
				dataDescription = daysWithNotNullData.get(index);
				dataDescription.setDateType(dateType);
				index++;
			}
			else
			{
				dataDescription = new CalendarDataDescription(dateType);
				dataDescription.setDate(dataDate);
			}

			calendarData.add(dataDescription);
			date.add(Calendar.DATE, 1);
		}
	}

	private BigDecimal calculateCurrentBalance(List<Long> cardIds) throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(cardIds))
			return BigDecimal.ZERO;

		Office office = departmentService.findByCode(new ExtendedCodeImpl(REGION_ID, null, null));
		Currency nationalCurrency = MoneyUtil.getNationalCurrency();

		List<CardLink> cardLinks = getPersonCardLinks();
		BigDecimal cardsBalance = BigDecimal.ZERO;
		for(Long id : cardIds)
		{
			for(CardLink cardLink : cardLinks)
			{
				if (cardLink.getId().equals(id))
				{
					Money amount = MoneyUtil.getBalanceInNationalCurrency(cardLink.getCard().getAvailableLimit(), nationalCurrency, office, CurrencyRateType.CB, TariffPlanHelper.getUnknownTariffPlanCode());
					cardsBalance = cardsBalance.add(amount.getDecimal());
					break;
				}
			}
		}

		return cardsBalance;
	}

	/**
	 * @return список из данных дл€ финансового календар€ (данные отсортированы по дате)
	 */
	public List<CalendarDataDescription> getCalendarData()
	{
		return calendarData;
	}

	/**
	 * —читает текущий баланс по картам
	 * @param filterData данные фильтрации
	 * @param cardIds идентификаторы карт, по которым получаютс€ данные
	 * @return текущий баланс по картам
	 */
	public BigDecimal getCardsBalance(FinanceFilterData filterData, List<Long> cardIds) throws BusinessException
	{
		try
		{
			if (Calendar.getInstance().after(filterData.getFromDate()) &&  Calendar.getInstance().before(filterData.getToDate()))
				return calculateCurrentBalance(cardIds);

			return null;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return список карт, по которым строитс€ финансовый календарь
	 */
	public List<CardLink> getSelectedCards()
	{
		return selectedCards;
	}
}
