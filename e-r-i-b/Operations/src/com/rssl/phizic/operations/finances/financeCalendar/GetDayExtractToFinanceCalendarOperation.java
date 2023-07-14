package com.rssl.phizic.operations.finances.financeCalendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDateType;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDayExtractByOperationDescription;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinanceHelper;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rssl.phizic.operations.finances.FinanceHelper.CARD_IDS_SEPARATOR;
import static com.rssl.phizic.operations.finances.FinanceHelper.CASH_PAYMENTS_ID;

/**
 * @author lepihina
 * @ created 24.04.14
 * $Author$
 * $Revision$
 */
public class GetDayExtractToFinanceCalendarOperation extends FinancesOperationBase
{
	private static final CardOperationService cardOperationService = new CardOperationService();

	private List<CalendarDayExtractByOperationDescription> dayExtract;
	private CalendarDateType dateType = CalendarDateType.TODAY;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.simpleInitialize();
		dayExtract = new ArrayList<CalendarDayExtractByOperationDescription>();
	}

	/**
	 * Возвращает выписку за день для финансового календаря
	 * @param filterData - данные фильтрации
	 * @throws BusinessException
	 */
	public void calculateDayExtract(FinanceFilterData filterData) throws BusinessException, BusinessLogicException
	{
		if (filterData.getFromDate().after(DateHelper.getCurrentDate()))
		{
			dateType = CalendarDateType.FUTURE;
			return;
		}

		if (filterData.getFromDate().before(DateHelper.getCurrentDate()))
		{
			dateType = CalendarDateType.PAST;
		}

		String selectedCardIds = filterData.getSelectedCardIds();
		boolean showCashPayments = Arrays.asList(selectedCardIds.split(CARD_IDS_SEPARATOR)).contains(CASH_PAYMENTS_ID.toString());

		List<String> cardsNumbers = FinanceHelper.getCardNumbersList(getPersonCardLinks(), selectedCardIds);
		dayExtract = cardOperationService.getDayExtractToFinanceCalendar(filterData.getFromDate(), filterData.getToDate(), filterData.getMaxOperationLoadDate(), getLogin().getId(), cardsNumbers, showCashPayments);
	}

	/**
	 * @return выписка за день
	 */
	public List<CalendarDayExtractByOperationDescription> getDayExtract()
	{
		return dayExtract;
	}

	/**
	 * @return тип даты в календаре
	 */
	public CalendarDateType getDateType()
	{
		return dateType;
	}
}
