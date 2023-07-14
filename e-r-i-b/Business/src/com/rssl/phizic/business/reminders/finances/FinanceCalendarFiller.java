package com.rssl.phizic.business.reminders.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;

import java.util.List;

/**
 * @author osminin
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 */
public interface FinanceCalendarFiller
{
	/**
	 * «аполнить календарь
	 * @param calendarData данные календар€
	 */
	void fill(List<CalendarDataDescription> calendarData) throws BusinessException;
}
