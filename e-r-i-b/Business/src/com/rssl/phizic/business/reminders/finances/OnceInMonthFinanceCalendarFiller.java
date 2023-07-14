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
 * Наполнитель финансового каледаря данными о ежемесячных напоминаниях
 */
public class OnceInMonthFinanceCalendarFiller extends FinanceCalendarFillerBase
{
	private Integer dayOfMonth;
	private Calendar createdDate;

	/**
	 * ctor
	 * @param dayOfMonth день месяца
	 * @param createdDate дата создания
	 * @param amount сумма
	 * @param reminderId идентификатор напоминания
	 */
	public OnceInMonthFinanceCalendarFiller(Integer dayOfMonth, Calendar createdDate, Money amount, Long reminderId)
	{
		super(amount, reminderId);

		if (dayOfMonth == null)
		{
			throw new IllegalArgumentException("День месяца напоминания не может быть null");
		}
		if (createdDate == null)
		{
			throw new IllegalArgumentException("Дата создания напоминания не может быть null");
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
		//Если текущая дата выходит за рамки периода, ничего не отображаем в этом периоде
		if (getCurrentDate().after(endDate))
		{
			return;
		}

		//если текущая дата и дата напоминания не попадают в период, ничего не отображаем
		if (getCurrentDate().before(startDate) && isOutOfPeriod(reminderDate, startDate, endDate))
		{
			return;
		}

		//текущая дата не попадает в выбранный период, отображаем напоминание в нужный день
		if (getCurrentDate().before(startDate))
		{
			fillData(reminderDate);
			return;
		}

		//текущая дата входит в выбранный период, дата напоминания меньше либо равна текущей, отображаем в текущий день
		if (reminderDate.compareTo(getCurrentDate()) < 1)
		{
			fillData();
			return;
		}

		//получаем дату предыдущего напоминания
		Calendar previousRemindDate = DateHelper.addMonths(reminderDate, -1);
		//на случай, если дата напоминания например 31е и текущий месяц февраль, что бы не потерять дни
		DateHelper.setDayOfMonth(previousRemindDate, dayOfMonth);

		//текущая дата входит в выбранный период, напоминание стоит позже текущего дня,
		//дата предыдущего напоминания идет после создания напоминания
		//учитываем предыдущее напоминание в сегодняшнем дне, текущее напоминание в нужный день
		if (previousRemindDate.compareTo(createdDate) > 0)
		{
			fillData();
		}

		//текущая дата входит в выбранный период, напоминание стоит позже текущего дня, учитываем в нужный день
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
		//Если текущая дата выходит за рамки периода, ничего не отображаем в этом периоде
		if (getCurrentDate().after(endDate))
		{
			return;
		}

		//в этом месяце оплата уже была, напоминаний выставлять не нужно
		if (processDate.compareTo(reminderDate) >= 0)
		{
			return;
		}

		//если текущая дата и дата напоминания не попадают в период, ничего не отображаем
		if (getCurrentDate().before(startDate) && isOutOfPeriod(reminderDate, startDate, endDate))
		{
			return;
		}

		//текущая дата не входит в выбранный перид, оплаты в месяц не было, добавляем напоминание только в нужный день
		if (getCurrentDate().before(startDate))
		{
			fillData(reminderDate);
			return;
		}

		//текущая дата входит в выбранный период, оплаты в месяце не было, напоминание должно отобразиться сегодняшним днем
		if (reminderDate.compareTo(getCurrentDate()) < 1)
		{
			fillData();
			return;
		}

		//получаем дату предыдущего напоминания
		Calendar previousReminderDate = DateHelper.addMonths(reminderDate, -1);

		//текущая дата входит в выбранный период, оплаты в месяце не было, предыдущее напоминание не оплачено и должно отобразиться сегодняшним днем
		//текущее напоминание должно отобразиться в указанную дату
		if (processDate.before(previousReminderDate))
		{
			fillData();
		}

		//текущая дата входит в выбранный период, оплаты в месяце не было, предыдущее напоминание оплачено и не должно отображаться сегодняшним днем
		//текущее напоминание должно отобразиться в указанную дату
		fillData(reminderDate);
	}

	@Override
	protected void fillWithResidualDate(Calendar reminderDate, Calendar residualDate) throws BusinessException
	{
		// не может быть
		if(residualDate.after(getCurrentDate()))
			return;

		// в остальном поведение такое же как и для отложенного на прошлое счета
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
		//Если текущая дата выходит за рамки периода, ничего не отображаем в этом периоде
		if (getCurrentDate().after(endDate))
		{
			return;
		}

		//если все даты не входят в период, ничего не отображаем
		if (getCurrentDate().before(startDate) && isOutOfPeriod(reminderDate, startDate, endDate) && isOutOfPeriod(delayedDate, startDate, endDate))
		{
			return;
		}

		//если даты отложенного напоминания и ежемесячного напоминания меньше или равны текущей,
		//то учитываем только одно напоминание только в текущий день
		if (getCurrentDate().compareTo(delayedDate) >=0 && getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//если дата отложенного напоминания больше либо равна дате ежемесячного напоминания и больше текущей
		//то учитываем только отложенное напоминание
		if (delayedDate.compareTo(reminderDate) >= 0)
		{
			if (delayedDate.compareTo(endDate) < 1)
			{
				fillData(delayedDate);
			}
			return;
		}

		//для всех остальных случаев учитывается ежемесячная дата напоминания и
		//дата отложенного напоминания, если она больше текущей
		if (getCurrentDate().before(delayedDate))
		{
			if (!isOutOfPeriod(delayedDate, startDate, endDate))
			{
				fillData(delayedDate);
			}
		}
		//дата текущая, в обратном случае
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
