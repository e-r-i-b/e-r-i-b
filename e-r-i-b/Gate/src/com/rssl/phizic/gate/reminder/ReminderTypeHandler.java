package com.rssl.phizic.gate.reminder;

import com.rssl.phizic.gate.payments.template.ReminderInfo;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ќбработчик напоминаний, в зависимости от типа
 */
public interface ReminderTypeHandler
{
	/**
	 * Ќеобходимо ли учитывать напоминание
	 * @param state состо€ние напоминани€
	 * @param info информаци€ о напоминании
	 * @param date дата, дл€ которой провер€етс€ необходтмость
	 * @return true - необходимо
	 */
	boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date);

	/**
	 * ѕолучить дату напоминани€ относительно указанной даты
	 * @param info инормаци€ о напоминании
	 * @param date дата
	 * @return дата напоминани€
	 */
	Calendar getReminderDate(ReminderInfo info, Calendar date);

	/**
	 * ѕолучить дату следующего напоминани€ относительно указанной даты
	 * @param info инормаци€ о напоминании
	 * @param date дата
	 * @return дата следующего напоминани€
	 */
	Calendar getNextReminderDate(ReminderInfo info, Calendar date);
}
