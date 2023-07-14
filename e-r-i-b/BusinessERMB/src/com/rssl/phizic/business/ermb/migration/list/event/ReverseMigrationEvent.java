package com.rssl.phizic.business.ermb.migration.list.event;

import com.rssl.phizic.events.Event;

import java.util.Calendar;

/**
 * ������� ������ �������� �� - ���� �� ������������ ����
 * @author Puzikov
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ReverseMigrationEvent implements Event
{
	//����, ����� ������� ���������� �������� ��������
	private Calendar date;

	/**
	 * ctor
	 * @param date ����
	 */
	public ReverseMigrationEvent(Calendar date)
	{
		this.date = date;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public String getStringForLog()
	{
		return getClass().getSimpleName();
	}
}
