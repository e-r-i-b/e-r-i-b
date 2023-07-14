package com.rssl.phizic.common.types;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * ѕериод между двум€ датами (в дн€х, мес€цах и годах)
 *
 * @author Kosyakov
 * @ created 30.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1426 $
 */
public final class DateSpan implements Serializable
{
	private int years;
	private int months;
	private int days;

	private int getValue ()
	{
		return this.years*100000+this.months*1000+this.days;
	}
	
	public DateSpan()
	{
		new DateSpan(0,0,0);
	}

	public DateSpan ( int years, int months, int days )
	{
		this.years = years;
		this.months = months;
		this.days = days;
	}

	public DateSpan (Calendar fromDate,Calendar toDate)
	{
		double difference = 0;
		if(fromDate.compareTo(toDate)<0)
		{
			long from = fromDate.getTime().getTime();
			long to = toDate.getTime().getTime();
			difference = to - from;
		}

		long daysResult = Math.round((difference/(1000*60*60*24)));
		years = (int)(daysResult / 365);
		months = (int)( (daysResult % 365)/30 );
		days = (int)( (daysResult % 365)% 30 );
	}

	public DateSpan ( String period )
	{
		if (!period.matches("\\d{2}-\\d{2}-\\d{3}"))
		{
			throw new IllegalArgumentException("Cannot format "+period+" as a DateSpan");
		}
		this.years = Integer.parseInt(period.substring(0, 2));
		this.months = Integer.parseInt(period.substring(3, 5));
		this.days = Integer.parseInt(period.substring(6));
	}

	public DateSpan(Long day)
	{
		years = (int)(day / 365);
		months = (int)( (day % 365)/30 );
		days = (int)( (day % 365)% 30 );
	}

	public int getYears ()
	{
		return years;
	}

	public int getMonths ()
	{
		return months;
	}

	public long getValueInDays()
	{
		return years*365 + months*30 + days;
	}

	public long getValueInMonth()
	{
		return years*12 + months;
	}

	public int getDays ()
	{
		return days;
	}

	public void setYears(int years)
	{
		this.years = years;
	}

	public void setMonths(int months)
	{
		this.months = months;
	}

	public void setDays(int days)
	{
		this.days = days;
	}

	public String toString ()
	{
		return String.format("%1$02d-%2$02d-%3$03d", this.years, this.months, this.days);
	}

	public boolean equals ( Object o )
	{
		if (this==o)
		{
			return true;
		}
		if (o==null || getClass()!=o.getClass())
		{
			return false;
		}
		return this.getValue()==((DateSpan)o).getValue();
	}

	public int hashCode ()
	{
		return getValue();
	}
}
