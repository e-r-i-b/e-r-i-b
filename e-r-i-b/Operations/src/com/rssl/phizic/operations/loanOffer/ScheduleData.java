package com.rssl.phizic.operations.loanOffer;

/**
 * User: Moshenko
 * Date: 06.07.2011
 * Time: 17:09:27
 * ������ ������ ������������ � �������� � ������,
 * ������������ ��� �������� ������ �� ��������� ��������
 */

public class ScheduleData
{
	/**
	 *  ������ Simple ��� Cron
	 */
	private boolean isSimple = false;

	/**
	 * ���� ���������
	 */
	private String expression = "";

	/**
	 * ����
	 */
	private String day = "";

	/**
	 * ���
	 */
	private String hour = "";

	/**
	 * ����� HH:MM
	 */
	private String time = "";

	/**
	 * ������� ��������
	 */
	private String dir = "";

	/**
	 * ���� ������
	 */
	private String dayInMonth = "";

	/**
	 * ������� ���� ��� ������� ������ � ����������
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
