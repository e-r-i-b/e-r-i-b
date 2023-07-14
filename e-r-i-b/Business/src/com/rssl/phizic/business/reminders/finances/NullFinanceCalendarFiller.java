package com.rssl.phizic.business.reminders.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * ���������� ��������� ����������� ������������� �������������
 * @author niculichev
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */
public class NullFinanceCalendarFiller extends FinanceCalendarFillerBase
{
	/**
	 * ctor
	 * @param amount ����� �����������
	 * @param reminderId ������������� �����������
	 */
	public NullFinanceCalendarFiller(Money amount, Long reminderId)
	{
		super(amount, reminderId);
	}

	@Override
	protected void fillWithResidualDate(Calendar reminderDate, Calendar residualDate) throws BusinessException
	{
		fillReminder(residualDate);
	}

	@Override
	protected void fillWithDelayDate(Calendar reminderDate, Calendar delayedDate) throws BusinessException
	{
		fillReminder(delayedDate);
	}

	@Override
	protected void fillWithProcessDate(Calendar reminderDate, Calendar processDate) throws BusinessException
	{
	}

	@Override
	protected void fillReminder(Calendar reminderDate) throws BusinessException
	{
		if(reminderDate == null)
			return;

		Calendar currentDate = getCurrentDate();

		// ���� ���� � ������� � ����� � ���������� - ���������
		if(reminderDate.after(currentDate) && !isOutOfPeriod(reminderDate, getStartDate(), getEndDate()))
		{
			fillData(reminderDate);
		}
		else if(!reminderDate.after(currentDate) && !isOutOfPeriod(currentDate, getStartDate(), getEndDate()))
		{
			fillData();
		}
	}

	@Override
	protected Calendar getReminderDate()
	{
		return null;
	}
}
