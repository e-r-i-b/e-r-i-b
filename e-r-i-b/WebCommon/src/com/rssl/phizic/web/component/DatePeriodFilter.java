package com.rssl.phizic.web.component;

import com.rssl.phizic.utils.DateHelper;

import java.util.*;

/**
 * @author Erkin
 * @ created 28.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 *  омпонент "фильтр по интервалу времени"
 */
public class DatePeriodFilter implements PeriodFilter
{
	public static final String TYPE_PERIOD = "typeDate";
	public static final String TYPE_PERIOD_WEEK = "week";
	public static final String TYPE_PERIOD_MONTH = "month";
	public static final String TYPE_PERIOD_DAY = "day";
	public static final String TYPE_PERIOD_PERIOD = "period";
	public static final String FROM_DATE = "fromDate";
	public static final String TO_DATE = "toDate";
	public static final String ON_DATE = "onDate";

	private final Map<String, Object> parameters;

	///////////////////////////////////////////////////////////////////////////

	/**
	 *  онстуктор
	 * «аполн€ет параметры фильтра значени€ми по-умолчанию
	 */
	public DatePeriodFilter()
	{
		parameters = new HashMap<String, Object>();
		parameters.put(TYPE_PERIOD, TYPE_PERIOD_WEEK);
	}

	/**
	 *  онстуктор на базе мапа параметров фильтра
	 * @param parameters
	 */
	public DatePeriodFilter(Map<String, Object> parameters)
	{
		if (parameters == null)
			throw new NullPointerException("јргумент 'parameters' не может быть null");
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
	 * @return дата начала периода в виде календар€
	 */
	public Calendar getFromCalendar()
	{
		return DateHelper.toCalendar(getFromDate());
	}

	/**
	 * @return дата окончани€ периода
	 */
	public Date getToDate()
	{
		return (Date) parameters.get(TO_DATE);
	}

	/**
	 * @return дата окончани€ периода в виде календар€
	 */
	public Calendar getToCalendar()
	{
		return DateHelper.toCalendar(getToDate());
	}

	/**
	 * @return выбранна€ дата
	 */
	public Date getOnDate()
	{
		return (Date) parameters.get(ON_DATE);
	}

	/**
	 * @return дата в виде календар€
	 */
	public Calendar getOnCalendar()
	{
		return DateHelper.toCalendar(getOnDate());
	}


	public DatePeriodFilter normalize()
	{
		String typePeriod = (String) parameters.get(TYPE_PERIOD);
		Calendar fromDate = DateHelper.getCurrentDate();
		Calendar toDate = DateHelper.getCurrentDate();

		if (TYPE_PERIOD_PERIOD.equals(typePeriod))
		{
			Calendar from = getFromCalendar();
			Calendar to   = getToCalendar();			

			fromDate = from == null ? fromDate : from;
			toDate   = to   == null ? toDate : to;
		}
		else if (TYPE_PERIOD_MONTH.equals(typePeriod))
		{
			// "за мес€ц"
			fromDate.set(Calendar.MONTH, fromDate.get(Calendar.MONTH) - 1);
			fromDate.get(Calendar.MONTH); //не убирать, иначе не обновитьс€ значение
		}
		else if (TYPE_PERIOD_WEEK.equals(typePeriod))
		{
			// "за неделю"
			fromDate.set(Calendar.DATE, fromDate.get(Calendar.DATE) - 7);
			fromDate.get(Calendar.DATE);  //не убирать, иначе не обновитьс€ значение
		}
		else if (TYPE_PERIOD_DAY.equals(typePeriod)) // "за день"
		{
			fromDate = getOnCalendar();
			toDate = getOnCalendar();
		}

		toDate.set(Calendar.HOUR_OF_DAY, 23);
		toDate.set(Calendar.MINUTE,      59);
		toDate.set(Calendar.SECOND,      59);

		parameters.put(FROM_DATE, fromDate.getTime());
		parameters.put(TO_DATE, toDate.getTime());

		return this;
	}
}
