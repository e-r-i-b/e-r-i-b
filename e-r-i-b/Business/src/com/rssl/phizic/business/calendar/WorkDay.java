package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.resources.external.CardLink;

import java.util.Calendar;

/**
 * @author Gainanov
 * @ created 24.03.2009
 * @ $Author $
 * @ $Revision $
 */
public class WorkDay implements Comparable
{
	private Calendar date;
	private boolean isWorkDay;
	private Long id;

	/**
	 * получить дату
	 * @return date - дата
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * установить дату
	 * @param date устанавливаемая дата
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * признак рабочего дня
	 * @return признак рабочего дня
	 */
	public boolean getIsWorkDay()
	{
		return isWorkDay;
	}

	/**
	 * установить признак рабочего дня
	 * @param workDay признак рабочего дня
	 */
	public void setIsWorkDay(boolean workDay)
	{
		isWorkDay = workDay;
	}

	/**
	 * получить ид записи
	 * @return ид записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * установка ид записи
	 * @param id ид записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	public int compareTo(Object o)
	{
		return date.compareTo( ((WorkDay)o).getDate());
	}

	public boolean equals(Object o)
	{
		return ((WorkDay)o).getDate().equals(date) && ((WorkDay)o).getIsWorkDay()==isWorkDay ;
	}

	public int hashCode()
	{
		return date.hashCode() + (isWorkDay ? 1 : 0);
	}
}
