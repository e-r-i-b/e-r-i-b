package com.rssl.phizic.business.loans.products;

/**
 * @author gulov
 * @ created 04.07.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Макcимальный срок кредита
 */
public class MaxDuration extends YearMonth
{
	private boolean include;

	public MaxDuration()
	{
	}

	public MaxDuration(Integer year, Integer month, boolean include)
	{
		super(year, month);
		this.include = include;
	}

	public void setInclude(boolean include)
	{
		this.include = include;
	}

	/**
	 * Количество месяцев минимального срока кредита
	 * @return - минимальный срок кредита
	 */
	public Integer getDuration()
	{
		int result = super.getDuration();

		if (result == 0)
			return 0;
		if (include)
			result++;
		return result;

	}

	public String toString()
	{
		if (include)
			return super.toString() + " включительно";
		return super.toString();
	}

	String getBeginString()
	{
		return "до ";
	}

}
