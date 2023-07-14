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
 * ���������� � �����������
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
	 * @param type ��� �����������
	 */
	public void setType(ReminderType type)
	{
		this.type = type;
	}

	/**
	 * @param type ��� �����������
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
	 * @param onceDate ���� �����������
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
	 * @param dayOfMonth ���� ����������� � ������
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
	 * @param monthOfQuarter ����� ����������� � ��������
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
	 * @param createdDate ���� ��������
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}
}
