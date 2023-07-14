package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип карты
 */
public enum CardKind
{
	/**
	 * Зарплатная карта
	 */
	SALARY("0"),

	/**
	 * Пенсионная карта
	 */
	PENSION("1"),

	/**
	 * Значение "выберите тип карты"
	 */
	EMPTY("2"),

	;

	private final String code;

	private CardKind(String code)
	{
		this.code = code;
	}

	/**
	 * @return значение в кодировке Transact SM (never null nor empty)
	 */
	public String getCode()
	{
		return code;
	}
}
