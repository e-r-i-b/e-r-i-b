package com.rssl.phizic.common.types;

/**
 * @author vagin
 * @ created 30.09.14
 * @ $Author$
 * @ $Revision$
 * “ип - день исполнени€ длительного поручени€(автоплатежа/автоподписки/копилки)
 */
public class LongOfferPayDay
{
	/* ƒень платежа в мес€це. ѕередаетс€ значение от 1 до количества
	 * дней в мес€це создани€ јѕ (€нварь - 31, в апрель - 30) */
	private int day;

	/* Ќомер мес€ца в квартале: от 1 до 3 */
	private int monthInQuarter;

	/* Ќомер мес€ца в году: от 1 до 12 */
	private int monthInYear;

	/* ƒень недели платежа: Ц	Monday Ц ѕонедельник;Ц	Tuesday - ¬торник;Ц
	 * 	Wednesday  - —реда;Ц	Thursday - „етвергЦ	Friday Ц ѕ€тница;Ц	Saturday
	 * Ц —уббота;Sunday Ц ¬оскресенье */
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
