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
 * ќбработчик ежемес€чных напоминаний
 */
public class OnceInMonthReminderHandler extends RegularReminderTypeHandlerBase
{
	private static final int MONTH_COUNT = 1;

	public Calendar getReminderDate(ReminderInfo info, Calendar date)
	{
		Calendar reminderDate = (Calendar) date.clone();
		DateHelper.clearTime(reminderDate);
		DateHelper.setDayOfMonth(reminderDate, info.getDayOfMonth());

		return reminderDate;
	}

	@Override
	protected int getMonthCount()
	{
		return MONTH_COUNT;
	}
}
