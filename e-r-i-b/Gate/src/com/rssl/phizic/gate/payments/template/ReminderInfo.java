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
 * ���������� � �����������
 */
public interface ReminderInfo extends Serializable
{
	/**
	 * @return ��� �����������
	 */
	ReminderType getType();

	/**
	 * @return ���� ����������� � ������
	 */
	Integer getDayOfMonth();

	/**
	 * @return ����� ������ ����������� � ��������
	 */
	Integer getMonthOfQuarter();

	/**
	 * @return ���� ������������ �����������
	 */
	Calendar getOnceDate();

	/**
	 * @return ���� �������� �����������
	 */
	Calendar getCreatedDate();
}
