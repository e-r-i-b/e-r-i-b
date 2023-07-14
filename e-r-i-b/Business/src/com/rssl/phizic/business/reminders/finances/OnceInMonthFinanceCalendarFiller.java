package com.rssl.phizic.business.reminders.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ����������� �������� ������� � ����������� ������������
 */
public class OnceInMonthFinanceCalendarFiller extends FinanceCalendarFillerBase
{
	private Integer dayOfMonth;
	private Calendar createdDate;

	/**
	 * ctor
	 * @param dayOfMonth ���� ������
	 * @param createdDate ���� ��������
	 * @param amount �����
	 * @param reminderId ������������� �����������
	 */
	public OnceInMonthFinanceCalendarFiller(Integer dayOfMonth, Calendar createdDate, Money amount, Long reminderId)
	{
		super(amount, reminderId);

		if (dayOfMonth == null)
		{
			throw new IllegalArgumentException("���� ������ ����������� �� ����� ���� null");
		}
		if (createdDate == null)
		{
			throw new IllegalArgumentException("���� �������� ����������� �� ����� ���� null");
		}

		this.dayOfMonth = dayOfMonth;
		this.createdDate = createdDate;
	}

	protected Calendar getReminderDate()
	{
		Calendar reminderDate = (Calendar) getStartDate().clone();
		DateHelper.clearTime(reminderDate);
		DateHelper.setDayOfMonth(reminderDate, dayOfMonth);

		return reminderDate;
	}

	@Override
	protected boolean isFillWithDelayedAvailable(ReminderLink link)
	{
		return link.getDelayedDate() != null && link.getDelayedDate().compareTo(getStartDate()) >= 0;
	}

	@Override
	protected void fillReminder(Calendar reminderDate)
	{
		fillReminderBase(reminderDate, getStartDate(reminderDate), getEndDate(reminderDate));

		Calendar nextReminderDate = DateHelper.addMonths(reminderDate, 1);
		if (nextReminderDate.get(Calendar.MONTH) <= getEndDate().get(Calendar.MONTH))
		{
			fillReminder(nextReminderDate);
		}
	}

	private Calendar getStartDate(Calendar reminderDate)
	{
		int reminderMonth = reminderDate.get(Calendar.MONTH);
		if (reminderMonth == getStartDate().get(Calendar.MONTH))
		{
			return getStartDate();
		}

		if (reminderMonth == getEndDate().get(Calendar.MONTH))
		{
			return DateHelper.getFirstDayOfMonth(getEndDate());
		}

		return DateHelper.getFirstDayOfMonth(reminderDate);
	}

	private Calendar getEndDate(Calendar reminderDate)
	{
		int reminderMonth = reminderDate.get(Calendar.MONTH);
		if (reminderMonth == getStartDate().get(Calendar.MONTH))
		{
			return DateHelper.getLastDayOfMonth(getStartDate());
		}

		if (reminderMonth == getEndDate().get(Calendar.MONTH))
		{
			return getEndDate();
		}

		return DateHelper.getLastDayOfMonth(reminderDate);
	}

	private void fillReminderBase(Calendar reminderDate, Calendar startDate, Calendar endDate)
	{
		//���� ������� ���� ������� �� ����� �������, ������ �� ���������� � ���� �������
		if (getCurrentDate().after(endDate))
		{
			return;
		}

		//���� ������� ���� � ���� ����������� �� �������� � ������, ������ �� ����������
		if (getCurrentDate().before(startDate) && isOutOfPeriod(reminderDate, startDate, endDate))
		{
			return;
		}

		//������� ���� �� �������� � ��������� ������, ���������� ����������� � ������ ����
		if (getCurrentDate().before(startDate))
		{
			fillData(reminderDate);
			return;
		}

		//������� ���� ������ � ��������� ������, ���� ����������� ������ ���� ����� �������, ���������� � ������� ����
		if (reminderDate.compareTo(getCurrentDate()) < 1)
		{
			fillData();
			return;
		}

		//�������� ���� ����������� �����������
		Calendar previousRemindDate = DateHelper.addMonths(reminderDate, -1);
		//�� ������, ���� ���� ����������� �������� 31� � ������� ����� �������, ��� �� �� �������� ���
		DateHelper.setDayOfMonth(previousRemindDate, dayOfMonth);

		//������� ���� ������ � ��������� ������, ����������� ����� ����� �������� ���,
		//���� ����������� ����������� ���� ����� �������� �����������
		//��������� ���������� ����������� � ����������� ���, ������� ����������� � ������ ����
		if (previousRemindDate.compareTo(createdDate) > 0)
		{
			fillData();
		}

		//������� ���� ������ � ��������� ������, ����������� ����� ����� �������� ���, ��������� � ������ ����
		fillData(reminderDate);
	}

	@Override
	protected void fillWithProcessDate(Calendar reminderDate, Calendar processDate)
	{
		fillWithProcessDateBase(reminderDate, processDate, getStartDate(reminderDate), getEndDate(reminderDate));

		Calendar nextReminderDate = DateHelper.addMonths(reminderDate, 1);
		if (nextReminderDate.get(Calendar.MONTH) <= getEndDate().get(Calendar.MONTH))
		{
			fillWithProcessDate(nextReminderDate, processDate);
		}
	}

	private void fillWithProcessDateBase(Calendar reminderDate, Calendar processDate, Calendar startDate, Calendar endDate)
	{
		//���� ������� ���� ������� �� ����� �������, ������ �� ���������� � ���� �������
		if (getCurrentDate().after(endDate))
		{
			return;
		}

		//� ���� ������ ������ ��� ����, ����������� ���������� �� �����
		if (processDate.compareTo(reminderDate) >= 0)
		{
			return;
		}

		//���� ������� ���� � ���� ����������� �� �������� � ������, ������ �� ����������
		if (getCurrentDate().before(startDate) && isOutOfPeriod(reminderDate, startDate, endDate))
		{
			return;
		}

		//������� ���� �� ������ � ��������� �����, ������ � ����� �� ����, ��������� ����������� ������ � ������ ����
		if (getCurrentDate().before(startDate))
		{
			fillData(reminderDate);
			return;
		}

		//������� ���� ������ � ��������� ������, ������ � ������ �� ����, ����������� ������ ������������ ����������� ����
		if (reminderDate.compareTo(getCurrentDate()) < 1)
		{
			fillData();
			return;
		}

		//�������� ���� ����������� �����������
		Calendar previousReminderDate = DateHelper.addMonths(reminderDate, -1);

		//������� ���� ������ � ��������� ������, ������ � ������ �� ����, ���������� ����������� �� �������� � ������ ������������ ����������� ����
		//������� ����������� ������ ������������ � ��������� ����
		if (processDate.before(previousReminderDate))
		{
			fillData();
		}

		//������� ���� ������ � ��������� ������, ������ � ������ �� ����, ���������� ����������� �������� � �� ������ ������������ ����������� ����
		//������� ����������� ������ ������������ � ��������� ����
		fillData(reminderDate);
	}

	@Override
	protected void fillWithResidualDate(Calendar reminderDate, Calendar residualDate) throws BusinessException
	{
		// �� ����� ����
		if(residualDate.after(getCurrentDate()))
			return;

		// � ��������� ��������� ����� �� ��� � ��� ����������� �� ������� �����
		fillWithDelayDate(reminderDate, residualDate);
	}

	@Override
	protected void fillWithDelayDate(Calendar reminderDate, Calendar delayedDate)
	{
		fillWithDelayDateBase(reminderDate, delayedDate, getStartDate(reminderDate), getEndDate(reminderDate));

		Calendar nextReminderDate = DateHelper.addMonths(reminderDate, 1);
		if (nextReminderDate.get(Calendar.MONTH) <= getEndDate().get(Calendar.MONTH))
		{
			fillWithDelayDate(nextReminderDate, delayedDate);
		}
	}

	private void fillWithDelayDateBase(Calendar reminderDate, Calendar delayedDate, Calendar startDate, Calendar endDate)
	{
		//���� ������� ���� ������� �� ����� �������, ������ �� ���������� � ���� �������
		if (getCurrentDate().after(endDate))
		{
			return;
		}

		//���� ��� ���� �� ������ � ������, ������ �� ����������
		if (getCurrentDate().before(startDate) && isOutOfPeriod(reminderDate, startDate, endDate) && isOutOfPeriod(delayedDate, startDate, endDate))
		{
			return;
		}

		//���� ���� ����������� ����������� � ������������ ����������� ������ ��� ����� �������,
		//�� ��������� ������ ���� ����������� ������ � ������� ����
		if (getCurrentDate().compareTo(delayedDate) >=0 && getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//���� ���� ����������� ����������� ������ ���� ����� ���� ������������ ����������� � ������ �������
		//�� ��������� ������ ���������� �����������
		if (delayedDate.compareTo(reminderDate) >= 0)
		{
			if (delayedDate.compareTo(endDate) < 1)
			{
				fillData(delayedDate);
			}
			return;
		}

		//��� ���� ��������� ������� ����������� ����������� ���� ����������� �
		//���� ����������� �����������, ���� ��� ������ �������
		if (getCurrentDate().before(delayedDate))
		{
			if (!isOutOfPeriod(delayedDate, startDate, endDate))
			{
				fillData(delayedDate);
			}
		}
		//���� �������, � �������� ������
		else if (getCurrentDate().compareTo(startDate) >= 0)
		{
			fillData();
		}

		if (!isOutOfPeriod(reminderDate, startDate, endDate))
		{
			fillData(reminderDate);
		}
	}
}
