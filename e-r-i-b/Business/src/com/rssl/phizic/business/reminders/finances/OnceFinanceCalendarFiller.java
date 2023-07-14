package com.rssl.phizic.business.reminders.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ����������� ��������� ������� ��������������� �����������
 */
public class OnceFinanceCalendarFiller extends FinanceCalendarFillerBase
{
	private Calendar onceDate;

	/**
	 * ctor
	 * @param onceDate ���� ��������������� �����������
	 * @param reminderId ������������� �����������
	 * @param amount ����� �����������
	 */
	public OnceFinanceCalendarFiller(Calendar onceDate, Long reminderId, Money amount)
	{
		super(amount, reminderId);

		if (onceDate == null)
		{
			throw new IllegalArgumentException("���� ������������ ����������� �� ����� ���� null");
		}

		this.onceDate = onceDate;
	}

	@Override
	protected Calendar getReminderDate()
	{
		return onceDate;
	}

	@Override
	protected void fillWithProcessDate(Calendar reminderDate, Calendar processDate) throws BusinessException
	{
		//���� ����������� ��������, ������ ���������� �� ����
	}

	@Override
	protected void fillWithDelayDate(Calendar reminderDate, Calendar delayedDate) throws BusinessException
	{
		fillReminder(delayedDate);
	}

	protected void fillWithResidualDate(Calendar reminderDate, Calendar residualDate) throws BusinessException
	{
		fillReminder(residualDate);
	}

	@Override
	protected void fillReminder(Calendar remindDate)
	{
		Calendar currentDate = getCurrentDate();

		// ���� ���� � ������� � ����� � ���������� - ���������
		if(remindDate.after(currentDate) && !isOutOfPeriod(remindDate, getStartDate(), getEndDate()))
		{
			fillData(remindDate);
		}
		else if(!remindDate.after(currentDate) && !isOutOfPeriod(currentDate, getStartDate(), getEndDate()))
		{
			fillData();
		}
	}
}
