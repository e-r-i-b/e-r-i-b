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
 * Наполнитель финансового календаря данными ежеквартального напоминания
 */
public class OnceInQuarterFinanceCalendarFiller extends FinanceCalendarFillerBase
{
	private Integer dayOfMonth;
	private Integer monthOfQuarter;
	private Calendar createdDate;

	/**
	 * ctor
	 * @param dayOfMonth день месяца напоминания
	 * @param monthOfQuarter номер месяца в квартале напоминания
	 * @param createdDate дата создания напоминания
	 * @param amount сумма напоминания
	 * @param reminderId идентификатор напоминания
	 */
	public OnceInQuarterFinanceCalendarFiller(Integer dayOfMonth, Integer monthOfQuarter, Calendar createdDate, Money amount, Long reminderId)
	{
		super(amount, reminderId);

		if (monthOfQuarter == null)
		{
			throw new IllegalArgumentException("Номер месяца в квартале не может быть null");
		}
		if (dayOfMonth == null)
		{
			throw new IllegalArgumentException("День месяца напоминания не может быть null");
		}
		if (createdDate == null)
		{
			throw new IllegalArgumentException("Дата создания напоминания не может быть null");
		}

		this.monthOfQuarter = monthOfQuarter;
		this.dayOfMonth = dayOfMonth;
		this.createdDate = createdDate;
	}

	@Override
	protected void fillWithProcessDate(Calendar reminderDate, Calendar processDate) throws BusinessException
	{
		//Ни одна дата не попадает в выбранный период - ничего не отображаем
		if (getCurrentDate().before(getStartDate()) && isOutOfFinanceCalendar(processDate) && isOutOfFinanceCalendar(reminderDate))
		{
			return;
		}

		//напоминание оплачено, ничего не отображаем
		if (processDate.compareTo(reminderDate) >= 0)
		{
			return;
		}

		//дата предыдущего напоминания
		Calendar previousReminderDate = getPreviousReminderDate(reminderDate);

		//дата напоминания позднее даты окончания выбранного периода календаря
		if (reminderDate.after(getEndDate()))
		{
			//Предыдущее напоминание оплачено, ничего не отображаем
			if (processDate.compareTo(previousReminderDate) >= 0)
			{
				return;
			}

			//Предыдущее напоминание не оплачено, отображаем его текущим днем
			fillData();
			return;
		}

		//если текущая дата больше либо равна дате напоминания, отображаем напоминание текущим днем
		if (getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//для оставшихся случаев если предыщее напоминание не оплачено, отображаем его текущим днем
		if (previousReminderDate.after(processDate))
		{
			fillData();
		}

		//напоминания отображаем в установленный день
		fillData(reminderDate);
	}

	@Override
	protected void fillWithDelayDate(Calendar reminderDate, Calendar delayedDate) throws BusinessException
	{
		//Ни одна дата не попадает в выбранный период - ничего не отображаем
		if (getCurrentDate().before(getStartDate()) && isOutOfFinanceCalendar(delayedDate) && isOutOfFinanceCalendar(reminderDate))
		{
			return;
		}

		//если дата, на которую было отложено напоминание, больше чем дата окончания периода выборки, то ничего не отображаем
		if (delayedDate.after(getEndDate()))
		{
			return;
		}

		//если все даты алгоритма входят в выбранный период, обрабатываем отдельно
		if (!isOutOfFinanceCalendar(getCurrentDate()) && !isOutOfFinanceCalendar(delayedDate) && !isOutOfFinanceCalendar(reminderDate))
		{
			fillWithDelayDateOnlyInFinanceCalendar(reminderDate, delayedDate);
			return;
		}

		//если дата, на которую было отложено напоминание, и дата расчетного напоминания меньше либо равны текущей,
		//то отображаем одно напоминание текущим днем
		if (getCurrentDate().compareTo(delayedDate) >= 0 && getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//если дата расчитанного напоминания выходит за период просмотра календаря и текущая дата больше либо равна дате,
		//на которую было отложено напоминание, отображаем ее текущим днем
		if (reminderDate.after(getEndDate()) && getCurrentDate().compareTo(delayedDate) >= 0)
		{
			fillData();
			return;
		}

		//если дата расчитанного напоминания выходит за период просмотра календаря и дата, на которую было отложено
		//напоминания идет после текущего дня, то отображаем ее
		if (reminderDate.after(getEndDate()) && delayedDate.after(getCurrentDate()))
		{
			fillData(delayedDate);
			return;
		}

		//в оставшихся случаях если дата напоминания больше текущей даты и даты, на которую было отложено напоминание, то
		if (reminderDate.after(getCurrentDate()) && reminderDate.after(delayedDate))
		{
			//если в период так же входит отложенная дата, то отображаем ее
			if (!isOutOfFinanceCalendar(delayedDate))
			{
				fillData(delayedDate);
			}
			//иначе если в период так же входит текущая дата, то отображаем ее
			else if (!isOutOfFinanceCalendar(getCurrentDate()))
			{
				fillData();
			}

			//отображаем в запланированное время напоминание
			fillData(reminderDate);
			return;
		}

		//в остальных случаях если дата, на которую было отложено напоминания, впереди либо равна другим датам, отображаем ее
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

		// в остальном поведение такое же как и для отложенного на прошлое счета
		fillWithDelayDate(reminderDate, residualDate);
	}

	@Override
	protected void fillReminder(Calendar reminderDate) throws BusinessException
	{
		//Ни одна дата не попадает в выбранный период - ничего не отображаем
		if (getCurrentDate().before(getStartDate()) && isOutOfFinanceCalendar(reminderDate))
		{
			return;
		}

		//если текущая дата больше либо равна дате напоминания, отображаем напоминание текущим днем
		if (getCurrentDate().compareTo(reminderDate) >= 0)
		{
			fillData();
			return;
		}

		//дата предыдущего напоминания
		Calendar previousReminderDate = getPreviousReminderDate(reminderDate);

		//если дата рассчитанного предыдущего напоминания выходит за дату создания, то в текущий день отображать нечего
		if (previousReminderDate.compareTo(createdDate) < 1)
		{
			//если дата напоминания за пределом периода, то ничего не отображаем
			if (reminderDate.after(getEndDate()))
			{
				return;
			}
		}
		//если рассчитанное предыдущее напоминание существует, отображаем его текущим днем
		else if (getCurrentDate().compareTo(getStartDate()) >= 0)
		{
			fillData();
		}

		//если напоминание входит в выбранный период календаря, отображаем его в установленный день
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
			fillData(delayedDate);
			return;
		}

		//для всех остальных случаев учитывается ежемесячная дата напоминания и
		//дата отложенного напоминания, если она больше текущей
		if (getCurrentDate().before(delayedDate))
		{
			fillData(delayedDate);
		}
		//дата текущая, в обратном случае
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

		//Если номер месяца начальной даты в квартале совпадает с номером месяца напоминания
		if (monthOfQuarter == startMonthInQuarter)
		{
			return (Calendar) getStartDate().clone();
		}

		int endMonthInQuarter = DateHelper.getMonthOfQuarter(getEndDate());

		//Если номер месяца конечной даты в квартале совпадает с номером месяца напоминания
		if (monthOfQuarter == endMonthInQuarter)
		{
			return (Calendar) getEndDate().clone();
		}

		int diff = endMonthInQuarter - startMonthInQuarter;
		//если выбранный период календаря охватывает 3 месяца и начальный и конечный не подошли, берем средний (1-2-3, 2-3-1, 3-1-2)
		if (diff == 2 || diff == -1)
		{
			return DateHelper.addMonths(getStartDate(), 1);
		}

		//дата напоминания не попадает в выбранный период (1-2(3),2-3(1),3-1(2))
		return DateHelper.addMonths(getEndDate(), 1);
	}
}
