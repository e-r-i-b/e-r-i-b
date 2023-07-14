package com.rssl.phizic.web.component;

import com.rssl.phizic.utils.DateHelper;

import java.util.*;

/**
 * ��������� "������ �� ��������� �������" (�� �������)
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
	 * ����������
	 * ��������� ��������� ������� ���������� ��-���������
	 */
	public MonthPeriodFilter()
	{
		parameters = new HashMap<String, Object>();
		parameters.put(TYPE_PERIOD, TYPE_PERIOD_PERIOD);
	}

	/**
	 * ���������� �� ���� ���� ���������� �������
	 * @param parameters
	 */
	public MonthPeriodFilter(Map<String, Object> parameters)
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
	 * @return ���� ������ ������� � ���� ���������
	 */
	public Calendar getFromMonthCalendar()
	{
		Calendar fromDate = DateHelper.toCalendar(getFromDate());
		return DateHelper.getFirstDayOfMonth(fromDate);
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
	 * @return ���� ��������� ������� � ���� ���������
	 */
	public Calendar getToMonthCalendar()
	{
		Calendar toDate = DateHelper.toCalendar(getToDate());
		return DateHelper.getLastDayOfMonth(toDate);
	}

	/**
	 * @return ��������� �����
	 */
	public Date getOnDate()
	{
		return (Date) parameters.get(ON_DATE);
	}

	/**
	 * @return ��������� ����� � ���� ���������
	 */
	public Calendar getOnCalendar()
	{
		Calendar toDate = DateHelper.toCalendar(getOnDate());
		//���� ���������� ���������� �� �������� ������, �� ����� ��������� - ��� ������� ����
		Calendar currentDate = DateHelper.getCurrentDate();
		//���� ������� ���� ������, ��� ���� ����� ������, ����� ��� ����� - �������, ��� ��������� �� �������� �� ������������� �� �����
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
