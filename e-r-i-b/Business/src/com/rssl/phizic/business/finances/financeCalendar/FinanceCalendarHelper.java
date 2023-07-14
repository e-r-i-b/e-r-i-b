package com.rssl.phizic.business.finances.financeCalendar;

import com.rssl.phizic.utils.DateHelper;

import java.util.List;

/**
 * @author lepihina
 * @ created 07.05.14
 * $Author$
 * $Revision$
 */
public class FinanceCalendarHelper
{
	/**
	 * Проверяет пуст ли календарь (месяц из прошлого и ни в одной из ячеек нет данных)
	 * @param calendarData - календарь
	 * @return true - календарь пуст
	 */
	public static boolean isEmptyCalendar(List<CalendarDataDescription> calendarData)
	{
		if (DateHelper.getCurrentDate().before(calendarData.get(0).getDate()))
			return false;

		for(CalendarDataDescription description : calendarData)
		{
			if (!description.getIsEmpty() || description.getDateType() == CalendarDateType.TODAY)
				return false;
		}
		return true;
	}
}
