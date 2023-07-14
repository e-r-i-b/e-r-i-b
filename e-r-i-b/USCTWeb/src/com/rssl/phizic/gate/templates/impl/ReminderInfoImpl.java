package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.gate.reminder.ReminderType;
import com.rssl.phizic.gate.payments.template.ReminderInfo;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Информация о напоминании
 */
public class ReminderInfoImpl implements ReminderInfo
{
	private ReminderType type;
	private Calendar onceDate;
	private Integer dayOfMonth;
	private Integer monthOfQuarter;
	private Calendar createdDate;

	public ReminderType getType()
	{
		return type;
	}

	/**
	 * @param type тип напоминания
	 */
	public void setType(ReminderType type)
	{
		this.type = type;
	}

	/**
	 * @param type тип напоминания
	 */
	public void setType(String type)
	{
		if (type == null || type.trim().length() == 0)
		{
			return;
		}
		this.type = Enum.valueOf(ReminderType.class, type);
	}

	public Calendar getOnceDate()
	{
		return onceDate;
	}

	/**
	 * @param onceDate дата напоминания
	 */
	public void setOnceDate(Calendar onceDate)
	{
		this.onceDate = onceDate;
	}

	public Integer getDayOfMonth()
	{
		return dayOfMonth;
	}

	/**
	 * @param dayOfMonth день напоминания в месяце
	 */
	public void setDayOfMonth(Integer dayOfMonth)
	{
		this.dayOfMonth = dayOfMonth;
	}

	public Integer getMonthOfQuarter()
	{
		return monthOfQuarter;
	}

	/**
	 * @param monthOfQuarter месяц напоминания в квартале
	 */
	public void setMonthOfQuarter(Integer monthOfQuarter)
	{
		this.monthOfQuarter = monthOfQuarter;
	}

	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate дата создания
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}
}
