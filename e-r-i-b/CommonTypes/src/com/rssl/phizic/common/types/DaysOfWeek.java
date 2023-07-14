package com.rssl.phizic.common.types;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User: moshenko
 * Date: 03.10.12
 * Time: 19:00
 * ������� ��� ������ � ���������� ���� ������
 */
public class DaysOfWeek implements Serializable
{
	private Set<Day> days = new HashSet<Day>();

	public DaysOfWeek()
	{
	}

	/**
	 * @param isAll true = ������ �� ����� �����, false = ������ ������ ���
	 */
	public DaysOfWeek(boolean isAll)
	{
		if (isAll)
		{
			this.days.add(Day.SUNDAY);
			this.days.add(Day.MONDAY);
			this.days.add(Day.TUESDAY);
			this.days.add(Day.WEDNESDAY);
			this.days.add(Day.THURSDAY);
			this.days.add(Day.FRIDAY);
			this.days.add(Day.SATURDAY);
		}
		else
		{
			this.days.add(Day.MONDAY);
			this.days.add(Day.TUESDAY);
			this.days.add(Day.WEDNESDAY);
			this.days.add(Day.THURSDAY);
			this.days.add(Day.FRIDAY);
		}
	}

	public DaysOfWeek(Set<Day> days)
	{
		this.days = days;
	}

	/**
	 * @param days ���
	 * @param isFullName true - ������ ������������, false - �����������
	 */
	public DaysOfWeek(String[] days,boolean isFullName)
	{
		if (isFullName)
		   for (String day : days)
				add(Day.valueFromFullName(day));	
		else
			for (String day : days)
				add(Day.fromValue(day));
	}

	public void add(Day day)
	{
		this.days.add(day);
	}

	public void remove(Day day)
	{
		this.days.remove(day);
	}

	public Set<Day> getDays()
	{
		return days;
	}

	public void setDays(Set<Day> days)
	{
		this.days = days;
	}

	public boolean isEmpty()
	{
		return days.isEmpty();
	}

	public String[] getStringDays()
	{
		if (days.isEmpty())
			return new String[0];

		String[] strDays = new String[days.size()];
		int i = 0;
		for (Day day : days)
		{
			strDays[i] = day.toValue();
			i++;
		}
		return strDays;
	}

	/**
	 * @return ������ ���� � ������� ������� �����
	 */
	public String[] getFullNameStrDays()
	{
		if (days.isEmpty())
			return new String[0];

		String[] strDays = new String[days.size()];
		int i = 0;
		for (Day day : days)
		{
			switch(day)
			{
				case MONDAY:strDays[i] = "Monday";break;
				case TUESDAY:strDays[i] = "Tuesday";break;
				case WEDNESDAY:strDays[i] = "Wednesday";break;
				case THURSDAY:strDays[i] = "Thursday";break;
				case FRIDAY:strDays[i] = "Friday";break;
				case SATURDAY:strDays[i] = "Saturday";break;
				case SUNDAY:strDays[i] = "Sunday";break;
			}
			i++;
		}
		return strDays;
	}

	/**
	 * @return ������ ���� ������ (�� �������)
	 */
	public String[] getFullNameStrDaysRus()
	{
		if (days.isEmpty())
			return new String[0];

		String[] strDays = new String[days.size()];
		int i = 0;
		for (Day day : days)
		{
			switch(day)
			{
				case MONDAY:strDays[i] = "�����������";break;
				case TUESDAY:strDays[i] = "�������";break;
				case WEDNESDAY:strDays[i] = "�����";break;
				case THURSDAY:strDays[i] = "�������";break;
				case FRIDAY:strDays[i] = "�������";break;
				case SATURDAY:strDays[i] = "�������";break;
				case SUNDAY:strDays[i] = "�����������";break;
			}
			i++;
		}
		return strDays;
	}

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof DaysOfWeek)) return false;

        DaysOfWeek that = (DaysOfWeek) o;

        if (days == null || that == null || that.getDays() == null) return false;

        Set<Day> thatDays = that.getDays();

        if (!days.containsAll(thatDays)) return false;

        return true;
    }

    public int hashCode ()
    {
        return days != null ? days.hashCode() : 0;
    }
}
