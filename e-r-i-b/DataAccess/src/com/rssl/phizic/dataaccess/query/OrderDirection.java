package com.rssl.phizic.dataaccess.query;

/**
 * @author mihaylov
 * @ created 30.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Направление сортировки в SQL запросе
 */
public enum OrderDirection
{
	ASC(1),// по возрастанию
	DESC(-1); // по убыванию

	private int sign;

	private OrderDirection(int sign)
	{
		this.sign = sign;
	}

	public static OrderDirection value(String str)
	{
		return valueOf(str.toUpperCase());
    }

	/**
	 * @return направление сортировки
	 */
	public int getSign()
	{
		return sign;
	}
}
