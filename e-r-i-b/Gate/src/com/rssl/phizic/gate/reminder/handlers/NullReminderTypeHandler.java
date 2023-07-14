package com.rssl.phizic.gate.reminder.handlers;

import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderState;
import com.rssl.phizic.gate.reminder.ReminderTypeHandler;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 *  Обработчик выставленных напоминаний с удаленными настройками
 * @author niculichev
 * @ created 22.11.14
 * @ $Author$
 * @ $Revision$
 */
public class NullReminderTypeHandler implements ReminderTypeHandler
{
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		if (info != null)
		{
			throw new IllegalArgumentException("info должен быть null");
		}

		//действий по напоминанию не совершалось
		if (state == null)
		{
			return false;
		}

		//если напоминание было оплачено, то не о чем напоминать
		if (state.getProcessDate() != null)
		{
			return false;
		}

		Calendar residualDate = state.getResidualDate();
		if (residualDate != null)
		{
			return !DateHelper.clearTime((Calendar) residualDate.clone()).after(date);
		}

		Calendar currentDate = DateHelper.getCurrentDate();
		Calendar delayedDate = state.getDelayedDate();

		//если выбрана дата больше текущей, то напоминаем только в установленную дату
		if (date.after(currentDate))
		{
			return date.compareTo(delayedDate) == 0;
		}
		//если выбрана текущая дата, то учитываем сегодняшнее либо установленное ранее напоминание
		return delayedDate.compareTo(currentDate) < 1;
	}

	public Calendar getReminderDate(ReminderInfo info, Calendar date)
	{
		return null;
	}

	public Calendar getNextReminderDate(ReminderInfo info, Calendar date)
	{
		return null;
	}
}
