package com.rssl.phizic.business.operations.background;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class TaskResultBase  implements TaskResult
{
	private Calendar startDate;//дата начала выполения задачи
	private Calendar endDate;//дата окончания выполения задачи

	public Calendar getStartDate()
	{
		return startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	/**
	 * Установить дату начала выполения задачи
	 * @param startDate дата начала выполения задачи
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * Установить дату окончания выполения задачи
	 * @param endDate дата окончания выполения задачи
	 */
	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
