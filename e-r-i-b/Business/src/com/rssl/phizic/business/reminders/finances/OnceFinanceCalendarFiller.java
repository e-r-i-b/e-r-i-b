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
 * Заполнитель финансового календаря данными единовременного напоминания
 */
public class OnceFinanceCalendarFiller extends FinanceCalendarFillerBase
{
	private Calendar onceDate;

	/**
	 * ctor
	 * @param onceDate дата единовременного напоминания
	 * @param reminderId идентификатор напоминания
	 * @param amount сумма напоминания
	 */
	public OnceFinanceCalendarFiller(Calendar onceDate, Long reminderId, Money amount)
	{
		super(amount, reminderId);

		if (onceDate == null)
		{
			throw new IllegalArgumentException("Дата однократного напоминания не может быть null");
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
		//если напоминание оплачено, ничего отображать не надо
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

		// если дата в будущем и входи в промежуток - заполняем
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
