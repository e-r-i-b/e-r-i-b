package com.rssl.phizic.gate.reminder;

import com.rssl.phizic.gate.payments.template.ReminderInfo;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �����������, � ����������� �� ����
 */
public interface ReminderTypeHandler
{
	/**
	 * ���������� �� ��������� �����������
	 * @param state ��������� �����������
	 * @param info ���������� � �����������
	 * @param date ����, ��� ������� ����������� �������������
	 * @return true - ����������
	 */
	boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date);

	/**
	 * �������� ���� ����������� ������������ ��������� ����
	 * @param info ��������� � �����������
	 * @param date ����
	 * @return ���� �����������
	 */
	Calendar getReminderDate(ReminderInfo info, Calendar date);

	/**
	 * �������� ���� ���������� ����������� ������������ ��������� ����
	 * @param info ��������� � �����������
	 * @param date ����
	 * @return ���� ���������� �����������
	 */
	Calendar getNextReminderDate(ReminderInfo info, Calendar date);
}
