package com.rssl.phizic.gate.reminder.handlers;

import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderTypeHandler;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовый обработчик напоминаний в зависимости от типа
 */
public abstract class ReminderTypeHandlerBase implements ReminderTypeHandler
{
	public Calendar getNextReminderDate(ReminderInfo info, Calendar date)
	{
		Calendar reminderDate = getReminderDate(info, date);

		if (date.after(reminderDate))
		{
			return getNextReminderDate(reminderDate);
		}

		return reminderDate;
	}

	protected abstract Calendar getNextReminderDate(Calendar reminderDate);
}
