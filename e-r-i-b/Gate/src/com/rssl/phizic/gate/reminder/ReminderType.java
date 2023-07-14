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
 * ��� �����������
 */
public enum ReminderType
{
	ONCE("����������", new OnceReminderHandler()),
	ONCE_IN_MONTH("����������", new OnceInMonthReminderHandler()),
	ONCE_IN_QUARTER("�������������", new OnceInQuarterReminderHelper());

	private String description;
	private ReminderTypeHandler handler;

	private ReminderType(String description, ReminderTypeHandler handler)
	{
		this.description = description;
		this.handler = handler;
	}

	/**
	 * @return �������� ���� �����������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ���������� �� ��������� �����������
	 * @param state ��������� �����������
	 * @param info ���������� � �����������
	 * @param date ����, ��� ������� ����������� �������������
	 * @return true - ����������
	 */
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		return handler.isNeedRemind(state, info, date);
	}

	/**
	 * �������� ���� ����������� ������������ ��������� ����
	 * @param info ��������� � �����������
	 * @param date ����
	 * @return ���� �����������
	 */
	public Calendar getReminderDate(ReminderInfo info, Calendar date)
	{
		return handler.getReminderDate(info, date);
	}

	/**
	 * �������� ���� ���������� ����������� ������������ ��������� ����
	 * @param info ��������� � �����������
	 * @param date ����
	 * @return ���� ���������� �����������
	 */
	public Calendar getNextReminderDate(ReminderInfo info, Calendar date)
	{
		return handler.getNextReminderDate(info, date);
	}
}