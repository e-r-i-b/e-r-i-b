package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Единица измерения площади
 */
public enum SquareUnits
{
	/**
	 * Квадратный метр
	 */
	METER("1", "кв.м"),

	/**
	 * Сотка
	 */
	HUNDRED("2", "сотка"),

	;

	private final String code;
	private final String description;

	private SquareUnits(String code, String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * @return значение в кодировке Transact SM (never null nor empty)
	 */
	public String getCode()
	{
		return code;
	}

	public String getDescription()
	{
		return description;
	}
}
