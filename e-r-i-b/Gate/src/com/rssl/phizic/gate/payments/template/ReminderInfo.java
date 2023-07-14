package com.rssl.phizic.gate.payments.template;

import com.rssl.phizic.gate.reminder.ReminderType;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Информация о напоминании
 */
public interface ReminderInfo extends Serializable
{
	/**
	 * @return тип напоминания
	 */
	ReminderType getType();

	/**
	 * @return день напоминания в месяце
	 */
	Integer getDayOfMonth();

	/**
	 * @return номер месяца напоминания в квартале
	 */
	Integer getMonthOfQuarter();

	/**
	 * @return дата однократного напоминания
	 */
	Calendar getOnceDate();

	/**
	 * @return дата создания напоминания
	 */
	Calendar getCreatedDate();
}
