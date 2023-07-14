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
 * ������ �� �����������
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
	 * @return ����� �������
	 */
	public long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId ����� �������
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ������������� �����������
	 */
	public long getReminderId()
	{
		return reminderId;
	}

	/**
	 * @param reminderId ������������� �����������
	 */
	public void setReminderId(long reminderId)
	{
		this.reminderId = reminderId;
	}

	/**
	 * @return ����, �� ������� ���� �������� �����������
	 */
	public Calendar getDelayedDate()
	{
		return delayedDate;
	}

	/**
	 * @param delayedDate ����, �� ������� ���� �������� �����������
	 */
	public void setDelayedDate(Calendar delayedDate)
	{
		this.delayedDate = delayedDate;
	}

	/**
	 * @return ���� ��������� ���������
	 */
	public Calendar getProcessDate()
	{
		return processDate;
	}

	/**
	 * @param processDate ���� ��������� ���������
	 */
	public void setProcessDate(Calendar processDate)
	{
		this.processDate = processDate;
	}

	/**
	 * @return ���� ����������� ������������� �����
	 */
	public Calendar getResidualDate()
	{
		return residualDate;
	}

	/**
	 * ���������� ���� ����������� ������������� �����
	 * @param residualDate ����
	 */
	public void setResidualDate(Calendar residualDate)
	{
		this.residualDate = residualDate;
	}
}
