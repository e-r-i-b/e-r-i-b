package com.rssl.phizic.business.ermb.migration.list.event;

import com.rssl.phizic.events.Event;

import java.util.Calendar;

/**
 * Событие отката миграции МБ - ЕРМБ на определенную дату
 * @author Puzikov
 * @ created 28.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ReverseMigrationEvent implements Event
{
	//Дата, после которой необходимо откатить миграцию
	private Calendar date;

	/**
	 * ctor
	 * @param date дата
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
