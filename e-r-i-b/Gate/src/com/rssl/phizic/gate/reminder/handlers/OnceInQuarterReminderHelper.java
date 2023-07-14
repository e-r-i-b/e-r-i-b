package com.rssl.phizic.gate.reminder.handlers;

import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик ежеквартальных напоминаний
 */
public class OnceInQuarterReminderHelper extends RegularReminderTypeHandlerBase
{
	private static final int MONTH_COUNT = 3;

	public Calendar getReminderDate(ReminderInfo info, Calendar date)
	{
		Calendar reminderDate = (Calendar) date.clone();
		DateHelper.clearTime(reminderDate);
		DateHelper.setDayOfMonth(reminderDate, info.getDayOfMonth());

		int monthOfQuarter = info.getMonthOfQuarter();
		int dateMontInQuarter = DateHelper.getMonthOfQuarter(date);

		if (dateMontInQuarter == monthOfQuarter)
		{
			return reminderDate;
		}

		return DateHelper.addMonths(reminderDate, monthOfQuarter - dateMontInQuarter);
	}

	@Override
	protected int getMonthCount()
	{
		return MONTH_COUNT;
	}
}
