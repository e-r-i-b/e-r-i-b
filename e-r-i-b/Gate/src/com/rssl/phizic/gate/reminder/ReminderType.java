package com.rssl.phizic.gate.reminder;

import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.handlers.OnceInMonthReminderHandler;
import com.rssl.phizic.gate.reminder.handlers.OnceInQuarterReminderHelper;
import com.rssl.phizic.gate.reminder.handlers.OnceReminderHandler;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Тип напоминания
 */
public enum ReminderType
{
	ONCE("Однократно", new OnceReminderHandler()),
	ONCE_IN_MONTH("Ежемесячно", new OnceInMonthReminderHandler()),
	ONCE_IN_QUARTER("Ежеквартально", new OnceInQuarterReminderHelper());

	private String description;
	private ReminderTypeHandler handler;

	private ReminderType(String description, ReminderTypeHandler handler)
	{
		this.description = description;
		this.handler = handler;
	}

	/**
	 * @return описание типа напоминания
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Необходимо ли учитывать напоминание
	 * @param state состояние напоминания
	 * @param info информация о напоминании
	 * @param date дата, для которой проверяется необходтмость
	 * @return true - необходимо
	 */
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		return handler.isNeedRemind(state, info, date);
	}

	/**
	 * Получить дату напоминания относительно указанной даты
	 * @param info инормация о напоминании
	 * @param date дата
	 * @return дата напоминания
	 */
	public Calendar getReminderDate(ReminderInfo info, Calendar date)
	{
		return handler.getReminderDate(info, date);
	}

	/**
	 * Получить дату следующего напоминания относительно указанной даты
	 * @param info инормация о напоминании
	 * @param date дата
	 * @return дата следующего напоминания
	 */
	public Calendar getNextReminderDate(ReminderInfo info, Calendar date)
	{
		return handler.getNextReminderDate(info, date);
	}
}