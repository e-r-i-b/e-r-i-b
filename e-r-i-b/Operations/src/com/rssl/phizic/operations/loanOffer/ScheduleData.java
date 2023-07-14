package com.rssl.phizic.operations.loanOffer;

/**
 * User: Moshenko
 * Date: 06.07.2011
 * Time: 17:09:27
 * хранит данные передаваемые в шеддулер и тригер,
 * используемые для выгрузки заявок на кредитные продукты
 */

public class ScheduleData
{
	/**
	 *  тригер Simple или Cron
	 */
	private boolean isSimple = false;

	/**
	 * крон выражение
	 */
	private String expression = "";

	/**
	 * день
	 */
	private String day = "";

	/**
	 * час
	 */
	private String hour = "";

	/**
	 * время HH:MM
	 */
	private String time = "";

	/**
	 * каталог выгрузки
	 */
	private String dir = "";

	/**
	 * день месяца
	 */
	private String dayInMonth = "";

	/**
	 * Признак того что задание готово к исполнению
	 */
	private boolean enabled = true;

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isSimple()
	{
		return isSimple;
	}

	public void setSimple(boolean simple)
	{
		isSimple = simple;
	}


	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}

	public String getHour()
	{
		return hour;
	}

	public void setHour(String hour)
	{
		this.hour = hour;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getDir()
	{
		return dir;
	}

	public void setDir(String dir)
	{
		this.dir = dir;
	}

	public String getDayInMonth()
	{
		return dayInMonth;
	}

	public void setDayInMonth(String dayInMonth)
	{
		this.dayInMonth = dayInMonth;
	}
}
