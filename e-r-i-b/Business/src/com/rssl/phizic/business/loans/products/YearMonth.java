package com.rssl.phizic.business.loans.products;

import com.rssl.phizic.utils.NumericUtil;

/**
 * @author gulov
 * @ created 04.07.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Год, месяц. Используется для определения минимального и максимального срока кредитов
 */
public class YearMonth
{
	public static final int MONTHS_COUNT = 12;

	private Integer year;
	private Integer month;

	public YearMonth()
	{
	}

	public YearMonth(Integer year, Integer month)
	{
		this.year = year;
		this.month = month;
	}

	public Integer getYear()
	{
		return year;
	}

	public void setYear(Integer year)
	{
		this.year = year;
	}

	public Integer getMonth()
	{
		return month;
	}

	public void setMonth(Integer month)
	{
		this.month = month;
	}

	/**
	 * Количество месяцев срока кредита
	 * @return - минимальный срок кредита
	 */
	public Integer getDuration()
	{
		return NumericUtil.getEmptyIfNull(year) * MONTHS_COUNT + NumericUtil.getEmptyIfNull(month);
	}

	public boolean isEmpty()
	{
		return NumericUtil.isEmpty(year) && NumericUtil.isEmpty(month);
	}

	public String toString()
	{
		return toString(this.year, this.month);
	}

	public String getString2()
	{
		return toString(getDuration() / 12, getDuration() % 12);
	}

	private String toString(Integer year, Integer month)
	{
		if (NumericUtil.isEmpty(year) && NumericUtil.isEmpty(month))
			return "";

		StringBuilder sb = new StringBuilder();

		sb.append(getBeginString());

		if (!NumericUtil.isEmpty(year))
		{
			sb.append(year.toString());
			sb.append(" ");
			sb.append(getYearName(year.toString()));
		}

		if (!NumericUtil.isEmpty(month))
		{
			if (!NumericUtil.isEmpty(year))
				sb.append(" ");
			sb.append(month.toString() + " мес.");
		}
		return sb.toString();
	}

	private String getYearName(String year)
	{
		if (year.equals(11))
			return "лет";
		if (year.substring(year.length() -1).equals("1"))
			return "года";
		return "лет";		
	}

	String getBeginString()
	{
		return "от ";
	}
}
