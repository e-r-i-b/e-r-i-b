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
 * ���������� ���������� �����������
 */
public abstract class RegularReminderTypeHandlerBase extends ReminderTypeHandlerBase
{
	public boolean isNeedRemind(ReminderState state, ReminderInfo info, Calendar date)
	{
		Calendar reminderDate = getReminderDate(info, date);
		Calendar previousReminderDate = getPreviousReminderDate(reminderDate);
		Calendar currentDate = DateHelper.getCurrentDate();

		//�������� �� ����������� �� �����������
		if (state == null)
		{
			//���� ���� ������ �������, �� �������� ����������� ������ � ������������� ����
			if (date.after(currentDate))
			{
				return date.compareTo(reminderDate) == 0;
			}

			//���� ����� ������� - ���� ���� ����������� ������ ���� ����� �������, �� ��������
			if (reminderDate.compareTo(currentDate) < 1)
			{
				return true;
			}
			//����� ������� ���������� �����������, ���� ��� ����� ������������
			return previousReminderDate.after(info.getCreatedDate());
		}

		Calendar residualDate = state.getResidualDate();
		// ���� ���� ���������� �����������
		if(residualDate != null)
		{
			return true;
		}

		Calendar processDate = state.getProcessDate();

		//���� ���� �������� �����-������ �����������
		if (processDate != null)
		{
			//���� ���� ������ �������, �� ��������� ����������� ������ � ������������� ����
			if (date.after(currentDate))
			{
				return date.compareTo(reminderDate) == 0;
			}

			//���� ����� �������

			//���� ���� ����������� ��� �� ���������, ������ ��������� ����������, ���� ��� �� ���� ��������
			if (reminderDate.after(info.getCreatedDate()))
			{
				return previousReminderDate.after(processDate);
			}

			//���� ���� ����������� ��� ���������, ���������, ���� ��� �� ���� ��������
			return reminderDate.after(processDate);
		}

		//����������� ���� ��������
		Calendar delayedDate = state.getDelayedDate();

		//���� ��� ����, �� ������� ���� �������� �����������, �� ���������
		if (delayedDate.compareTo(date) == 0)
		{
			return true;
		}

		//���� ���� ������ ����������, �� ��������� ������ ���� ������ ������� ����, ���� ���� �����������
		if (date.after(delayedDate))
		{
			return date.compareTo(reminderDate) == 0 || date.compareTo(currentDate) == 0;
		}

		//����� ������ �� ���������
		return false;
	}

	protected Calendar getNextReminderDate(Calendar reminderDate)
	{
		return DateHelper.addMonths(reminderDate, getMonthCount());
	}

	private Calendar getPreviousReminderDate(Calendar reminderDate)
	{
		return DateHelper.addMonths(reminderDate, -getMonthCount());
	}

	protected abstract int getMonthCount();
}
