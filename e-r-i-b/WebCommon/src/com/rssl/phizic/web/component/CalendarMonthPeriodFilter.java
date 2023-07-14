package com.rssl.phizic.web.component;

import com.rssl.phizic.utils.DateHelper;

import java.util.*;

/**
 * @author lepihina
 * @ created 23.04.14
 * $Author$
 * $Revision$
 * ��������� "������ �� ��������� �������" (�� �����)
 * ��������� ������ ������ ������ ������ �� ����������� ������, ���� ������ ���� �������� ������ �� �����������
 * ��������� ��������� ������ ������ ������ �� ���������� ������, ���� ��������� ���� �������� ������ �� �����������
 */
public class CalendarMonthPeriodFilter implements PeriodFilter
{
	public static final String ON_DATE = "onDate";
	public static final String FROM_DATE = "fromDate";
	public static final String TO_DATE = "toDate";

	private final Map<String, Object> parameters;

	/**
	 * ���������� �� ���� ���� ���������� �������
	 * @param parameters - ���������
	 */
	public CalendarMonthPeriodFilter(Map<String, Object> parameters)
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
	 * @return ��������� �����
	 */
	public Date getOnDate()
	{
		return (Date) parameters.get(ON_DATE);
	}

	public CalendarMonthPeriodFilter normalize()
	{
		Calendar fromDate = DateHelper.getFirstDayOfMonth(DateHelper.toCalendar(getOnDate()));
		Calendar toDate = DateHelper.getLastDayOfMonth(DateHelper.toCalendar(getOnDate()));

		int firstDay = fromDate.get(Calendar.DAY_OF_WEEK);
		int lastDay = toDate.get(Calendar.DAY_OF_WEEK);
		if (firstDay != Calendar.MONDAY)
		{
			int delta = firstDay == Calendar.SUNDAY ? -6 : -firstDay + 2; // ����������� - 2� ���� ������
			fromDate.add(Calendar.DATE, delta);
		}
		if (lastDay != Calendar.SUNDAY)
		{
			toDate.add(Calendar.DATE, 7 - lastDay + 1); // ����������� - 1� ���� ������
		}

		toDate.set(Calendar.HOUR_OF_DAY, 23);
		toDate.set(Calendar.MINUTE,      59);
		toDate.set(Calendar.SECOND,      59);

		parameters.put(FROM_DATE, fromDate.getTime());
		parameters.put(TO_DATE, toDate.getTime());

		return this;
	}
}
