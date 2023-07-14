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
 * ������� ����� ��� ���������� ��������� �������������
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
	 * @param amount ����� �����������
	 * @param reminderId ������������� �����������
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

		//�������, �� ����������� �� ��� �����-������ �������� � ������������
		if (link != null)
		{
			// ��������� ���������� ������������ �����
			if(isFillWithResidualAvailable(link))
			{
				fillWithResidualDate(reminderDate, link.getResidualDate());
				return;
			}
			//�������� ���� ��������� ������ �����������
			else if (isFillWithProcessAvailable(link))
			{
				fillWithProcessDate(reminderDate, link.getProcessDate());
				return;
			}
			//��������� �� ���� �� ����������� ��������
			else if (isFillWithDelayedAvailable(link))
			{
				fillWithDelayDate(reminderDate, link.getDelayedDate());
				return;
			}
		}

		//�������� �� �����������, ��������� �������
		fillReminder(reminderDate);
	}

	/**
	 * ��������� ��������� ������� ����������� � ������ ����������� ������������� �����
	 * @param reminderDate ���� �����������
	 * @param residualDate ���� ����������� ��������� �����
	 * @throws BusinessException
	 */
	protected abstract void fillWithResidualDate(Calendar reminderDate, Calendar residualDate) throws BusinessException;

	/**
	 * ��������� ��������� ������� �����������
	 * @param reminderDate ���� �����������
	 * @throws BusinessException
	 */
	protected abstract void fillReminder(Calendar reminderDate) throws BusinessException;

	/**
	 * ��������� ��������� ������� ����������� � ������ ���� ����������� �����������
	 * @param reminderDate ���� �����������
	 * @param delayedDate ����, �� ������� ���� �������� �����������
	 * @throws BusinessException
	 */
	protected abstract void fillWithDelayDate(Calendar reminderDate, Calendar delayedDate) throws BusinessException;

	/**
	 * ��������� ��������� ������� ����������� � ������ ���� ����������� �����������
	 * @param reminderDate ���� �����������
	 * @param processDate ����, � ������� ���� �������� �����������
	 * @throws BusinessException
	 */
	protected abstract void fillWithProcessDate(Calendar reminderDate, Calendar processDate) throws BusinessException;

	/**
	 * @return ���� �����������
	 */
	protected abstract Calendar getReminderDate();

	/**
	 * ���������� �� ��������� ��������� � ������ ���� ������ �����������
	 * @param link ������ �� �����������
	 * @return true - ���� ����������
	 */
	protected boolean isFillWithProcessAvailable(ReminderLink link)
	{
		return link.getProcessDate() != null;
	}

	/**
	 * ���������� �� ��������� ��������� � ������ ����, �� ������� ���� �������� �����������
	 * @param link ������ �� �����������
	 * @return true - ���� ����������
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
	 * ��������� ��������� ������� ��� ������� ����
	 */
	protected void fillData()
	{
		fillData(currentDate);
	}

	/**
	 * ��������� ���������
	 * @param reminderDate ���� �����������
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
	 * @return ������ ���������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * @return ����� ���������
	 */
	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * @return ������� ����
	 */
	public Calendar getCurrentDate()
	{
		return currentDate;
	}

	/**
	 * �������� �� ���� � ����� ���������
	 * @param date ����
	 * @return true - ���� �� ��������
	 */
	protected boolean isOutOfFinanceCalendar(Calendar date)
	{
		return isOutOfPeriod(date, startDate, endDate);
	}

	/**
	 * �������� �� ���� � ��������� ������
	 * @param date ����
	 * @param startPeriod ������ �������
	 * @param endPeriod ����� �������
	 * @return true - ���� �� ��������
	 */
	protected boolean isOutOfPeriod(Calendar date, Calendar startPeriod, Calendar endPeriod)
	{
		return date.before(startPeriod) || date.after(endPeriod);
	}
}
