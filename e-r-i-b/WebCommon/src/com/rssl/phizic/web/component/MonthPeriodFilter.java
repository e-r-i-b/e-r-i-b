package com.rssl.phizic.web.component;

import com.rssl.phizic.utils.DateHelper;

import java.util.*;

/**
 * Компонент "фильтр по интервалу времени" (по месяцам)
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class MonthPeriodFilter implements PeriodFilter
{
	public static final String TYPE_PERIOD = "typeDate";
	public static final String TYPE_PERIOD_MONTH = "month";
	public static final String TYPE_PERIOD_PERIOD = "period";
	public static final String TYPE_PERIOD_MONTH_PERIOD = "monthPeriod";
	public static final String FROM_DATE = "fromDate";
	public static final String TO_DATE = "toDate";
	public static final String ON_DATE = "onDate";

	private final Map<String, Object> parameters;

	/**
	 * Констуктор
	 * Заполняет параметры фильтра значениями по-умолчанию
	 */
	public MonthPeriodFilter()
	{
		parameters = new HashMap<String, Object>();
		parameters.put(TYPE_PERIOD, TYPE_PERIOD_PERIOD);
	}

	/**
	 * Констуктор на базе мапа параметров фильтра
	 * @param parameters
	 */
	public MonthPeriodFilter(Map<String, Object> parameters)
	{
		if (parameters == null)
			throw new NullPointerException("Аргумент 'parameters' не может быть null");
		this.parameters = new HashMap<String, Object>(parameters);
	}

	public Map<String, Object> getParameters()
	{
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * @return дата начала периода
	 */
	public Date getFromDate()
	{
		return (Date) parameters.get(FROM_DATE);
	}

	/**
	 * @return дата начала периода в виде календаря
	 */
	public Calendar getFromCalendar()
	{
		return DateHelper.toCalendar(getFromDate());
	}

	/**
	 * @return дата начала периода в виде календаря
	 */
	public Calendar getFromMonthCalendar()
	{
		Calendar fromDate = DateHelper.toCalendar(getFromDate());
		return DateHelper.getFirstDayOfMonth(fromDate);
	}

	/**
	 * @return дата окончания периода
	 */
	public Date getToDate()
	{
		return (Date) parameters.get(TO_DATE);
	}

	/**
	 * @return дата окончания периода в виде календаря
	 */
	public Calendar getToCalendar()
	{
		return DateHelper.toCalendar(getToDate());
	}

	/**
	 * @return дата окончания периода в виде календаря
	 */
	public Calendar getToMonthCalendar()
	{
		Calendar toDate = DateHelper.toCalendar(getToDate());
		return DateHelper.getLastDayOfMonth(toDate);
	}

	/**
	 * @return выбранный месяц
	 */
	public Date getOnDate()
	{
		return (Date) parameters.get(ON_DATE);
	}

	/**
	 * @return выбранный месяц в виде календаря
	 */
	public Calendar getOnCalendar()
	{
		Calendar toDate = DateHelper.toCalendar(getOnDate());
		//если происходит фильтрация по текущему месяцу, то конец интервала - это текущая дата
		Calendar currentDate = DateHelper.getCurrentDate();
		//если текущая дата меньше, чем дата конца месяца, тогда это месяц - текущий, так интервалы из будущего мы просматривать не можем
		if (currentDate.compareTo(DateHelper.getLastDayOfMonth(toDate)) < 0)
			return currentDate;
		return DateHelper.getLastDayOfMonth(toDate);
	}

	public MonthPeriodFilter normalize()
	{
		String typePeriod = (String) parameters.get(TYPE_PERIOD);
		Calendar fromDate = DateHelper.getCurrentDate();
		Calendar toDate = DateHelper.getCurrentDate();
		Calendar from = null;
		Calendar to = null;

		if (TYPE_PERIOD_PERIOD.equals(typePeriod))
		{
			from = getFromCalendar();
			to   = getToCalendar();
		}
		else if (TYPE_PERIOD_MONTH_PERIOD.equals(typePeriod))
		{
			from = getFromMonthCalendar();
			to   = getToMonthCalendar();
		}
		else if (TYPE_PERIOD_MONTH.equals(typePeriod))
		{
			to   = getOnCalendar();
			from = DateHelper.getFirstDayOfMonth(to);
		}

		fromDate = from == null ? fromDate : from;
		toDate   = to   == null ? toDate : to;

		toDate.set(Calendar.HOUR_OF_DAY, 23);
		toDate.set(Calendar.MINUTE,      59);
		toDate.set(Calendar.SECOND,      59);

		parameters.put(FROM_DATE, fromDate.getTime());
		parameters.put(TO_DATE, toDate.getTime());

		return this;
	}
}
