package com.rssl.phizic.gate.reminder.handlers;

import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderState;
import com.rssl.phizic.gate.reminder.ReminderTypeHandler;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 *  ���������� ������������ ����������� � ���������� �����������
 * @author niculichev
 * @ created 22.11.14
 * @ $Author$
 * @ $Revision$
 */
public class NullReminderTypeHandler implements ReminderTypeHandler
{
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		if (info != null)
		{
			throw new IllegalArgumentException("info ������ ���� null");
		}

		//�������� �� ����������� �� �����������
		if (state == null)
		{
			return false;
		}

		//���� ����������� ���� ��������, �� �� � ��� ����������
		if (state.getProcessDate() != null)
		{
			return false;
		}

		Calendar residualDate = state.getResidualDate();
		if (residualDate != null)
		{
			return !DateHelper.clearTime((Calendar) residualDate.clone()).after(date);
		}

		Calendar currentDate = DateHelper.getCurrentDate();
		Calendar delayedDate = state.getDelayedDate();

		//���� ������� ���� ������ �������, �� ���������� ������ � ������������� ����
		if (date.after(currentDate))
		{
			return date.compareTo(delayedDate) == 0;
		}
		//���� ������� ������� ����, �� ��������� ����������� ���� ������������� ����� �����������
		return delayedDate.compareTo(currentDate) < 1;
	}

	public Calendar getReminderDate(ReminderInfo info, Calendar date)
	{
		return null;
	}

	public Calendar getNextReminderDate(ReminderInfo info, Calendar date)
	{
		return null;
	}
}
