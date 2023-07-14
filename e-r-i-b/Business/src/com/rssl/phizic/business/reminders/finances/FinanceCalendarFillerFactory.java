package com.rssl.phizic.business.reminders.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderType;

/**
 * @author osminin
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ‘абрика наполнителей напоминани€ми данных финансового каледар€
 */
public class FinanceCalendarFillerFactory
{
	private static final FinanceCalendarFillerFactory INSTANCE = new FinanceCalendarFillerFactory();

	private FinanceCalendarFillerFactory()
	{
	}

	/**
	 * @return инстанс фабрики
	 */
	public static FinanceCalendarFillerFactory getInstance()
	{
		return INSTANCE;
	}

	/**
	 * @param reminder напоминание
	 * @return наполнитель данных финансового календар€
	 * @throws BusinessException
	 */
	public FinanceCalendarFiller getFiller(TemplateDocument reminder) throws BusinessException
	{
		if (reminder == null)
		{
			throw new IllegalArgumentException("Ќапоминание не может быть null");
		}

		ReminderInfo info = reminder.getReminderInfo();
		Long reminderId = reminder.getId();
		if(info == null)
		{
			return new NullFinanceCalendarFiller(reminder.getExactAmount(), reminderId);
		}

		ReminderType type = info.getType();

		if (ReminderType.ONCE == type)
		{
			return new OnceFinanceCalendarFiller(info.getOnceDate(), reminderId, reminder.getExactAmount());
		}
		if (ReminderType.ONCE_IN_MONTH == type)
		{
			return new OnceInMonthFinanceCalendarFiller(info.getDayOfMonth(), info.getCreatedDate(), reminder.getExactAmount(), reminderId);
		}
		if (ReminderType.ONCE_IN_QUARTER == type)
		{
			return new OnceInQuarterFinanceCalendarFiller(info.getDayOfMonth(), info.getMonthOfQuarter(), info.getCreatedDate(), reminder.getExactAmount(), reminderId);
		}
		throw new BusinessException("Ќеизвестный тип напоминани€ " + type);
	}
}
