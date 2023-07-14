package com.rssl.phizic.business.reminders.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ����������� ��������� ������� ��������������� �����������
 */
public class OnceInQuarterFinanceCalendarFiller extends FinanceCalendarFillerBase
{
	private Integer dayOfMonth;
	private Integer monthOfQuarter;
	private Calendar createdDate;

	/**
	 * ctor
	 * @param dayOfMonth ���� ������ �����������
	 * @param monthOfQuarter ����� ������ � �������� �����������
	 * @param createdDate ���� �������� �����������
	 * @param amount ����� �����������
	 * @param reminderId ������������� �����������
	 */
	public OnceInQuarterFinanceCalendarFiller(Integer dayOfMonth, Integer monthOfQuarter, Calendar createdDate, Money amount, Long reminderId)
	{
		super(amount, reminderId);

		if (monthOfQuarter == null)
		{
			throw new IllegalArgumentException("����� ������ � �������� �� ����� ���� null");
		}
		if (dayOfMonth == null)
		{
			throw new IllegalArgumentException("���� ������ ����������� �� ����� ���� null");
		}
		if (createdDate == null)
		{
			throw new IllegalArgumentException("���� �������� ����������� �� ����� ���� null");
		}

		this.monthOfQuarter = monthOfQuarter;
		this.dayOfMonth = dayOfMonth;
		this.createdDate = createdDate;
	}

	@Override
	protected void fillWithProcessDate(Calendar reminderDate, Calendar processDate) throws BusinessException
	{
		//�� ���� ���� �� �������� � ��������� ������ - ������ �� ����������
		if (getCurrentDate().before(getStartDate()) && isOutOfFinanceCalendar(processDate) && isOutOfFinanceCalendar(reminderDate))
		{
			return;
		}

		//����������� ��������, ������ �� ����������
		if (processDate.compareTo(reminderDate) >= 0)
		{
			return;
		}

		//���� ����������� �����������
		Calendar previousReminderDate = getPreviousReminderDate(reminderDate);

		//���� ����������� ������� ���� ��������� ���������� ������� ���������
		if (reminderDate.after(getEndDate()))
		{
			//���������� ����������� ��������, ������ �� ����������
			if (processDate.compareTo(previousReminderDate) >= 0)
			{
				return;
			}

			//���������� ����������� �� ��������, ���������� ��� ������� ����
			fillData();
			return;
		}

		//���� ������� ���� ������ ���� ����� ���� �����������, ���������� ����������� ������� ����
		if (getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//��� ���������� ������� ���� �������� ����������� �� ��������, ���������� ��� ������� ����
		if (previousReminderDate.after(processDate))
		{
			fillData();
		}

		//����������� ���������� � ������������� ����
		fillData(reminderDate);
	}

	@Override
	protected void fillWithDelayDate(Calendar reminderDate, Calendar delayedDate) throws BusinessException
	{
		//�� ���� ���� �� �������� � ��������� ������ - ������ �� ����������
		if (getCurrentDate().before(getStartDate()) && isOutOfFinanceCalendar(delayedDate) && isOutOfFinanceCalendar(reminderDate))
		{
			return;
		}

		//���� ����, �� ������� ���� �������� �����������, ������ ��� ���� ��������� ������� �������, �� ������ �� ����������
		if (delayedDate.after(getEndDate()))
		{
			return;
		}

		//���� ��� ���� ��������� ������ � ��������� ������, ������������ ��������
		if (!isOutOfFinanceCalendar(getCurrentDate()) && !isOutOfFinanceCalendar(delayedDate) && !isOutOfFinanceCalendar(reminderDate))
		{
			fillWithDelayDateOnlyInFinanceCalendar(reminderDate, delayedDate);
			return;
		}

		//���� ����, �� ������� ���� �������� �����������, � ���� ���������� ����������� ������ ���� ����� �������,
		//�� ���������� ���� ����������� ������� ����
		if (getCurrentDate().compareTo(delayedDate) >= 0 && getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//���� ���� ������������ ����������� ������� �� ������ ��������� ��������� � ������� ���� ������ ���� ����� ����,
		//�� ������� ���� �������� �����������, ���������� �� ������� ����
		if (reminderDate.after(getEndDate()) && getCurrentDate().compareTo(delayedDate) >= 0)
		{
			fillData();
			return;
		}

		//���� ���� ������������ ����������� ������� �� ������ ��������� ��������� � ����, �� ������� ���� ��������
		//����������� ���� ����� �������� ���, �� ���������� ��
		if (reminderDate.after(getEndDate()) && delayedDate.after(getCurrentDate()))
		{
			fillData(delayedDate);
			return;
		}

		//� ���������� ������� ���� ���� ����������� ������ ������� ���� � ����, �� ������� ���� �������� �����������, ��
		if (reminderDate.after(getCurrentDate()) && reminderDate.after(delayedDate))
		{
			//���� � ������ ��� �� ������ ���������� ����, �� ���������� ��
			if (!isOutOfFinanceCalendar(delayedDate))
			{
				fillData(delayedDate);
			}
			//����� ���� � ������ ��� �� ������ ������� ����, �� ���������� ��
			else if (!isOutOfFinanceCalendar(getCurrentDate()))
			{
				fillData();
			}

			//���������� � ��������������� ����� �����������
			fillData(reminderDate);
			return;
		}

		//� ��������� ������� ���� ����, �� ������� ���� �������� �����������, ������� ���� ����� ������ �����, ���������� ��
		if (delayedDate.compareTo(getCurrentDate()) >= 0 && delayedDate.compareTo(reminderDate) >= 0)
		{
			fillData(delayedDate);
		}
	}

	@Override
	protected void fillWithResidualDate(Calendar reminderDate, Calendar residualDate) throws BusinessException
	{
		if(residualDate.after(getCurrentDate()))
			return;

		// � ��������� ��������� ����� �� ��� � ��� ����������� �� ������� �����
		fillWithDelayDate(reminderDate, residualDate);
	}

	@Override
	protected void fillReminder(Calendar reminderDate) throws BusinessException
	{
		//�� ���� ���� �� �������� � ��������� ������ - ������ �� ����������
		if (getCurrentDate().before(getStartDate()) && isOutOfFinanceCalendar(reminderDate))
		{
			return;
		}

		//���� ������� ���� ������ ���� ����� ���� �����������, ���������� ����������� ������� ����
		if (getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//���� ����������� �����������
		Calendar previousReminderDate = getPreviousReminderDate(reminderDate);

		//���� ���� ������������� ����������� ����������� ������� �� ���� ��������, �� � ������� ���� ���������� ������
		if (previousReminderDate.compareTo(createdDate) < 1)
		{
			//���� ���� ����������� �� �������� �������, �� ������ �� ����������
			if (reminderDate.after(getEndDate()))
			{
				return;
			}
		}
		//���� ������������ ���������� ����������� ����������, ���������� ��� ������� ����
		else if (getCurrentDate().compareTo(getStartDate()) >= 0)
		{
			fillData();
		}

		//���� ����������� ������ � ��������� ������ ���������, ���������� ��� � ������������� ����
		if (reminderDate.compareTo(getEndDate()) < 1)
		{
			fillData(reminderDate);
		}
	}

	@Override
	protected Calendar getReminderDate()
	{
		Calendar reminderDate = getReminderBaseDate();
		DateHelper.clearTime(reminderDate);
		DateHelper.setDayOfMonth(reminderDate, dayOfMonth);

		return reminderDate;
	}

	private void fillWithDelayDateOnlyInFinanceCalendar(Calendar reminderDate, Calendar delayedDate)
	{
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
			fillData(delayedDate);
			return;
		}

		//��� ���� ��������� ������� ����������� ����������� ���� ����������� �
		//���� ����������� �����������, ���� ��� ������ �������
		if (getCurrentDate().before(delayedDate))
		{
			fillData(delayedDate);
		}
		//���� �������, � �������� ������
		else
		{
			fillData();
		}
		fillData(reminderDate);
	}

	private Calendar getPreviousReminderDate(Calendar reminderDate)
	{
		return DateHelper.addMonths(reminderDate, -3);
	}

	private Calendar getReminderBaseDate()
	{
		int startMonthInQuarter = DateHelper.getMonthOfQuarter(getStartDate());

		//���� ����� ������ ��������� ���� � �������� ��������� � ������� ������ �����������
		if (monthOfQuarter == startMonthInQuarter)
		{
			return (Calendar) getStartDate().clone();
		}

		int endMonthInQuarter = DateHelper.getMonthOfQuarter(getEndDate());

		//���� ����� ������ �������� ���� � �������� ��������� � ������� ������ �����������
		if (monthOfQuarter == endMonthInQuarter)
		{
			return (Calendar) getEndDate().clone();
		}

		int diff = endMonthInQuarter - startMonthInQuarter;
		//���� ��������� ������ ��������� ���������� 3 ������ � ��������� � �������� �� �������, ����� ������� (1-2-3, 2-3-1, 3-1-2)
		if (diff == 2 || diff == -1)
		{
			return DateHelper.addMonths(getStartDate(), 1);
		}

		//���� ����������� �� �������� � ��������� ������ (1-2(3),2-3(1),3-1(2))
		return DateHelper.addMonths(getEndDate(), 1);
	}
}
