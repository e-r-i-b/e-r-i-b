package com.rssl.phizic.business.dictionaries.pfp.products.types;

/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Шаг на диаграме
 */

public class DiagramStep
{
	private long from;
	private long to;
	private String name;

	/**
	 * @return минимальное значение шага
	 */
	public long getFrom()
	{
		return from;
	}

	/**
	 * задать минимальное значение шага
	 * @param from минимальное значение шага
	 */
	public void setFrom(long from)
	{
		this.from = from;
	}

	/**
	 * @return максимальное значение шага
	 */
	public long getTo()
	{
		return to;
	}

	/**
	 * задать максимальное значение шага
	 * @param to максимальное значение шага
	 */
	public void setTo(long to)
	{
		this.to = to;
	}

	/**
	 * @return название шага
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название шага
	 * @param name название шага
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
