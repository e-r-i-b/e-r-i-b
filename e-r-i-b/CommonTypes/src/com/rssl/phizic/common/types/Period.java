package com.rssl.phizic.common.types;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 28.03.2013
 * @ $Author$
 * @ $Revision$
 * Класс, описывающий период(диапазон дат)
 */
public class Period
{
	private Calendar fromDate;
	private Calendar toDate;

	/**
	 * Конструктор. параметры обязательны. начальная дата должна быть не больше конечной
	 * @param fromDate начальная дата
	 * @param toDate конечная дата
	 */
	public Period(Calendar fromDate, Calendar toDate)
	{
		if (fromDate == null)
		{
			throw new IllegalArgumentException("Начальная дата должна быть задана");
		}
		if (toDate == null)
		{
			throw new IllegalArgumentException("Конечная дата должна быть задана");
		}
		if (fromDate.after(toDate))
		{
			throw new IllegalArgumentException("Конечная дата должна быть больше начальной");
		}
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	/**
	 * @return начальная дата периода
	 */
	public Calendar getFromDate()
	{
		return fromDate;
	}

	/**
	 * @return конечная дата периода
	 */
	public Calendar getToDate()
	{
		return toDate;
	}
}
