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
 * Обработчик регулярных напоминаний
 */
public abstract class RegularReminderTypeHandlerBase extends ReminderTypeHandlerBase
{
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		Calendar reminderDate = getReminderDate(info, date);
		Calendar previousReminderDate = getPreviousReminderDate(reminderDate);
		Calendar currentDate = DateHelper.getCurrentDate();

		//действий по напоминаний не совершалось
		if (state == null)
		{
			//если дата больше текущей, то учиываем напоминание только в установленный день
			if (date.after(currentDate))
			{
				return date.compareTo(reminderDate) == 0;
			}

			//дата равна текущей - если дата напоминания меньше либо равна текущей, то учтываем
			if (reminderDate.compareTo(currentDate) < 1)
			{
				return true;
			}
			//иначе выводим предыдущее напоминание, если оно могло существовать
			return previousReminderDate.after(info.getCreatedDate());
		}

		Calendar residualDate = state.getResidualDate();
		// если есть оставшееся напоминание
		if(residualDate != null)
		{
			return true;
		}

		Calendar processDate = state.getProcessDate();

		//если было оплачено какое-нибудь напоминание
		if (processDate != null)
		{
			//если дата больше текущей, то учитываем напоминание только в установленный день
			if (date.after(currentDate))
			{
				return date.compareTo(reminderDate) == 0;
			}

			//дата равна текущей

			//если дата напоминания еще не наступила, значит учитываем предыдущее, если оно не было оплачено
			if (reminderDate.after(info.getCreatedDate()))
			{
				return previousReminderDate.after(processDate);
			}

			//если дата напоминания уже наступила, учитываем, если оно не было оплачено
			return reminderDate.after(processDate);
		}

		//напоминание было отложено
		Calendar delayedDate = state.getDelayedDate();

		//если это дата, на которую было отложено напоминание, то учитываем
		if (delayedDate.compareTo(date) == 0)
		{
			return true;
		}

		//если дата больше отложенной, то учитываем только если выбран текущий день, либо день напоминания
		if (date.after(delayedDate))
		{
			return date.compareTo(reminderDate) == 0 || date.compareTo(currentDate) == 0;
		}

		//иначе ничего не учитываем
		return false;
	}

	protected Calendar getNextReminderDate(Calendar reminderDate)
	{
		return DateHelper.addMonths(reminderDate, getMonthCount());
	}

	private Calendar getPreviousReminderDate(Calendar reminderDate)
	{
		return DateHelper.addMonths(reminderDate, -getMonthCount());
	}

	protected abstract int getMonthCount();
}
