package com.rssl.phizic.gate.reminder.handlers;

import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderState;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик одноразового напоминания
 */
public class OnceReminderHandler extends ReminderTypeHandlerBase
{
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		Calendar reminderDate = info.getOnceDate();

		//действий по напоминаний не совершалось
		if (state == null)
		{
			//если выбрана дата больше текущей, то напоминаем только в установленную дату
			if (date.after(currentDate))
			{
				return date.compareTo(reminderDate) == 0;
			}
			//если выбрана текущая дата, то учитываем сегодняшнее либо установленное ранее напоминание
			return reminderDate.compareTo(currentDate) < 1;
		}

		// если есть оставшийся счет
		if(state.getResidualDate() != null)
		{
			return true;
		}

		//если напоминание было оплачено, то не о чем напоминать
		if (state.getProcessDate() != null)
		{
			return false;
		}

		//напоминание было отложено
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
		return info.getOnceDate();
	}

	public Calendar getNextReminderDate(Calendar reminderDate)
	{
		return null;
	}
}
