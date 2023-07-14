package com.rssl.phizic.business.reminders.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;
import com.rssl.phizic.business.reminders.ReminderHelper;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс для заполнения календаря напоминаниями
 */
public abstract class FinanceCalendarFillerBase implements FinanceCalendarFiller
{
	private Calendar startDate;
	private Calendar endDate;
	private Calendar currentDate;

	private List<CalendarDataDescription> calendarData;
	private Money amount;
	private Long reminderId;

	/**
	 * ctor
	 * @param amount сумма напоминания
	 * @param reminderId идентификатор напоминания
	 */
	protected FinanceCalendarFillerBase(Money amount, Long reminderId)
	{
		this.amount = amount;
		this.reminderId = reminderId;
	}

	public void fill(List<CalendarDataDescription> calendarData) throws BusinessException
	{
		if (CollectionUtils.isEmpty(calendarData))
		{
			return;
		}

		this.calendarData = calendarData;
		startDate = DateHelper.clearTime(calendarData.get(0).getDate());
		endDate = DateHelper.clearTime(calendarData.get(calendarData.size()-1).getDate());
		currentDate = DateHelper.getCurrentDate();

		if (currentDate.after(endDate))
		{
			return;
		}

		fillBase();
	}

	private void fillBase() throws BusinessException
	{
		ReminderLink link = ReminderHelper.getLinkByReminderId(reminderId);
		Calendar reminderDate = getReminderDate();

		//смотрим, не выполнялись ли уже какие-нибудь действия с напоминанием
		if (link != null)
		{
			// проверяем оставшиеся выставленные счета
			if(isFillWithResidualAvailable(link))
			{
				fillWithResidualDate(reminderDate, link.getResidualDate());
				return;
			}
			//проверям дату последней оплаты напоминания
			else if (isFillWithProcessAvailable(link))
			{
				fillWithProcessDate(reminderDate, link.getProcessDate());
				return;
			}
			//проверяем не было ли напоминание отложено
			else if (isFillWithDelayedAvailable(link))
			{
				fillWithDelayDate(reminderDate, link.getDelayedDate());
				return;
			}
		}

		//действие не выполнялось, заполняем данными
		fillReminder(reminderDate);
	}

	/**
	 * Заполнить календаль данными напоминания с учетом оставшегося выставленного счета
	 * @param reminderDate дата напоминания
	 * @param residualDate дата выставления оставшего счета
	 * @throws BusinessException
	 */
	protected abstract void fillWithResidualDate(Calendar reminderDate, Calendar residualDate) throws BusinessException;

	/**
	 * Заполнить календарь данными напоминания
	 * @param reminderDate дата напоминания
	 * @throws BusinessException
	 */
	protected abstract void fillReminder(Calendar reminderDate) throws BusinessException;

	/**
	 * Заполнить календарь данными напоминания с учетом даты отложенного напоминания
	 * @param reminderDate дата напоминания
	 * @param delayedDate дата, на которую было отложено напоминание
	 * @throws BusinessException
	 */
	protected abstract void fillWithDelayDate(Calendar reminderDate, Calendar delayedDate) throws BusinessException;

	/**
	 * Заполнить календарь данными напоминания с учетом даты оплаченного напоминания
	 * @param reminderDate дата напоминания
	 * @param processDate дата, в которую было оплачено напоминание
	 * @throws BusinessException
	 */
	protected abstract void fillWithProcessDate(Calendar reminderDate, Calendar processDate) throws BusinessException;

	/**
	 * @return дата напоминания
	 */
	protected abstract Calendar getReminderDate();

	/**
	 * Необходимо ли заполнять календарь с учетом даты оплаты напоминания
	 * @param link ссылка на напоминание
	 * @return true - если необходимо
	 */
	protected boolean isFillWithProcessAvailable(ReminderLink link)
	{
		return link.getProcessDate() != null;
	}

	/**
	 * Необходимо ли заполнять календарь с учетом даты, на которую было отложено напоминание
	 * @param link ссылка на напоминание
	 * @return true - если необходимо
	 */
	protected boolean isFillWithDelayedAvailable(ReminderLink link)
	{
		return link.getDelayedDate() != null;
	}

	protected boolean isFillWithResidualAvailable(ReminderLink link)
	{
		return link.getResidualDate() != null;
	}

	/**
	 * Заполнить календарь данными для текущей даты
	 */
	protected void fillData()
	{
		fillData(currentDate);
	}

	/**
	 * Заполнить календарь
	 * @param reminderDate дата напоминания
	 */
	protected void fillData(Calendar reminderDate)
	{
		Long daysDiff = DateHelper.daysDiff(startDate, reminderDate);
		CalendarDataDescription dataDescription = calendarData.get(daysDiff.intValue());
		dataDescription.setInvoiceSubscriptionsCount(dataDescription.getInvoiceSubscriptionsCount() + 1);

		if (amount != null)
		{
			dataDescription.setPaymentsAmount(dataDescription.getPaymentsAmount().add(amount.getDecimal()));
		}
	}

	/**
	 * @return начало календаря
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * @return конец календаря
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * @return текущая дата
	 */
	public Calendar getCurrentDate()
	{
		return currentDate;
	}

	/**
	 * Попадает ли дата в рамки календаря
	 * @param date дата
	 * @return true - если не попадает
	 */
	protected boolean isOutOfFinanceCalendar(Calendar date)
	{
		return isOutOfPeriod(date, startDate, endDate);
	}

	/**
	 * Попадает ли дата в выбранный период
	 * @param date дата
	 * @param startPeriod начало периода
	 * @param endPeriod конец периода
	 * @return true - если не попадает
	 */
	protected boolean isOutOfPeriod(Calendar date, Calendar startPeriod, Calendar endPeriod)
	{
		return date.before(startPeriod) || date.after(endPeriod);
	}
}
