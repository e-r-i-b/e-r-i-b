package com.rssl.phizic.common.types;

/**
 * @author vagin
 * @ created 30.09.14
 * @ $Author$
 * @ $Revision$
 * ��� - ���� ���������� ����������� ���������(�����������/������������/�������)
 */
public class LongOfferPayDay
{
	/* ���� ������� � ������. ���������� �������� �� 1 �� ����������
	 * ���� � ������ �������� �� (������ - 31, � ������ - 30) */
	private int day;

	/* ����� ������ � ��������: �� 1 �� 3 */
	private int monthInQuarter;

	/* ����� ������ � ����: �� 1 �� 12 */
	private int monthInYear;

	/* ���� ������ �������: �	Monday � �����������;�	Tuesday - �������;�
	 * 	Wednesday  - �����;�	Thursday - �������	Friday � �������;�	Saturday
	 * � �������;Sunday � ����������� */
	private int weekDay;

	public LongOfferPayDay(int day, int monthInQuarter, int monthInYear, int weekDay)
	{
		this.day = day;
		this.monthInQuarter = monthInQuarter;
		this.monthInYear = monthInYear;
		this.weekDay = weekDay;
	}

	public LongOfferPayDay(String day, String monthInQuarter, String monthInYear, String weekDay)
	{
		if (day != null && !day.equals(""))
			this.day = Integer.valueOf(day);
		if (monthInQuarter != null && !monthInQuarter.equals(""))
			this.monthInQuarter = Integer.valueOf(monthInQuarter);
		if (monthInYear != null && !monthInYear.equals(""))
			this.monthInYear = Integer.valueOf(monthInYear);
		if (weekDay != null && !weekDay.equals(""))
		{
			for (Day dayOfWeek : Day.values())
			{
				if (dayOfWeek.toFullName().equals(weekDay))
					this.weekDay = dayOfWeek.ordinal() + 1;
			}
		}
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getMonthInQuarter()
	{
		return monthInQuarter;
	}

	public void setMonthInQuarter(int monthInQuarter)
	{
		this.monthInQuarter = monthInQuarter;
	}

	public int getMonthInYear()
	{
		return monthInYear;
	}

	public void setMonthInYear(int monthInYear)
	{
		this.monthInYear = monthInYear;
	}

	public int getWeekDay()
	{
		return weekDay;
	}

	public void setWeekDay(int weekDay)
	{
		this.weekDay = weekDay;
	}
}
