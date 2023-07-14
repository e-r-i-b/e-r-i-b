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
 * ���������� ������������ �����������
 */
public class OnceReminderHandler extends ReminderTypeHandlerBase
{
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		Calendar reminderDate = info.getOnceDate();

		//�������� �� ����������� �� �����������
		if (state == null)
		{
			//���� ������� ���� ������ �������, �� ���������� ������ � ������������� ����
			if (date.after(currentDate))
			{
				return date.compareTo(reminderDate) == 0;
			}
			//���� ������� ������� ����, �� ��������� ����������� ���� ������������� ����� �����������
			return reminderDate.compareTo(currentDate) < 1;
		}

		// ���� ���� ���������� ����
		if(state.getResidualDate() != null)
		{
			return true;
		}

		//���� ����������� ���� ��������, �� �� � ��� ����������
		if (state.getProcessDate() != null)
		{
			return false;
		}

		//����������� ���� ��������
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
		return info.getOnceDate();
	}

	public Calendar getNextReminderDate(Calendar reminderDate)
	{
		return null;
	}
}
