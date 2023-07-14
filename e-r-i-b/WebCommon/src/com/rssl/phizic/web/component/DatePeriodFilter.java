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
 * ��������� "������ �� ��������� �������"
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
	 * ����������
	 * ��������� ��������� ������� ���������� ��-���������
	 */
	public DatePeriodFilter()
	{
		parameters = new HashMap<String, Object>();
		parameters.put(TYPE_PERIOD, TYPE_PERIOD_WEEK);
	}

	/**
	 * ���������� �� ���� ���� ���������� �������
	 * @param parameters
	 */
	public DatePeriodFilter(Map<String, Object> parameters)
	{
		if (parameters == null)
			throw new NullPointerException("�������� 'parameters' �� ����� ���� null");
		this.parameters = new HashMap<String, Object>(parameters);
	}

	public Map<String, Object> getParameters()
	{
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * @return ���� ������ �������
	 */
	public Date getFromDate()
	{
		return (Date) parameters.get(FROM_DATE);
	}

	/**
	 * @return ���� ������ ������� � ���� ���������
	 */
	public Calendar getFromCalendar()
	{
		return DateHelper.toCalendar(getFromDate());
	}

	/**
	 * @return ���� ��������� �������
	 */
	public Date getToDate()
	{
		return (Date) parameters.get(TO_DATE);
	}

	/**
	 * @return ���� ��������� ������� � ���� ���������
	 */
	public Calendar getToCalendar()
	{
		return DateHelper.toCalendar(getToDate());
	}

	/**
	 * @return ��������� ����
	 */
	public Date getOnDate()
	{
		return (Date) parameters.get(ON_DATE);
	}

	/**
	 * @return ���� � ���� ���������
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
			// "�� �����"
			fromDate.set(Calendar.MONTH, fromDate.get(Calendar.MONTH) - 1);
			fromDate.get(Calendar.MONTH); //�� �������, ����� �� ���������� ��������
		}
		else if (TYPE_PERIOD_WEEK.equals(typePeriod))
		{
			// "�� ������"
			fromDate.set(Calendar.DATE, fromDate.get(Calendar.DATE) - 7);
			fromDate.get(Calendar.DATE);  //�� �������, ����� �� ���������� ��������
		}
		else if (TYPE_PERIOD_DAY.equals(typePeriod)) // "�� ����"
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
