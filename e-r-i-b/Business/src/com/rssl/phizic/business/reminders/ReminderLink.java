package com.rssl.phizic.business.reminders;

import com.rssl.phizic.gate.reminder.ReminderState;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 18.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Ссылка на напоминание
 */
public class ReminderLink implements ReminderState, Serializable
{
	private long loginId;
	private long reminderId;
	private Calendar delayedDate;
	private Calendar processDate;
	private Calendar residualDate;

	public ReminderLink()
	{
	}

	public ReminderLink(long loginId, long reminderId)
	{
		this.loginId = loginId;
		this.reminderId = reminderId;
	}

	public ReminderLink(long loginId, long reminderId, Calendar residualDate)
	{
		this.loginId = loginId;
		this.reminderId = reminderId;
		this.residualDate = residualDate;
	}

	public ReminderLink(long loginId, long reminderId, Calendar processDate, Calendar delayedDate)
	{
		this.loginId = loginId;
		this.reminderId = reminderId;
		this.processDate = processDate;
		this.delayedDate = delayedDate;
	}

	/**
	 * @return логин клиента
	 */
	public long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId логин клиента
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return идентификатор напоминания
	 */
	public long getReminderId()
	{
		return reminderId;
	}

	/**
	 * @param reminderId идентификатор напоминания
	 */
	public void setReminderId(long reminderId)
	{
		this.reminderId = reminderId;
	}

	/**
	 * @return дата, на которую было отложено напоминание
	 */
	public Calendar getDelayedDate()
	{
		return delayedDate;
	}

	/**
	 * @param delayedDate дата, на которую было отложено напоминание
	 */
	public void setDelayedDate(Calendar delayedDate)
	{
		this.delayedDate = delayedDate;
	}

	/**
	 * @return дата последней обработки
	 */
	public Calendar getProcessDate()
	{
		return processDate;
	}

	/**
	 * @param processDate дата последней обработки
	 */
	public void setProcessDate(Calendar processDate)
	{
		this.processDate = processDate;
	}

	/**
	 * @return дата оставшегося выставленного счета
	 */
	public Calendar getResidualDate()
	{
		return residualDate;
	}

	/**
	 * Установить дату оставшегося выставленного счета
	 * @param residualDate дата
	 */
	public void setResidualDate(Calendar residualDate)
	{
		this.residualDate = residualDate;
	}
}
