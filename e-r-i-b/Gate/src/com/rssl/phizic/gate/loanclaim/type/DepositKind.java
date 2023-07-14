package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип вклада
 */
public enum DepositKind
{
	/**
	 * Зарплатный вклад
	 */
	SALARY("0"),

	/**
	 * Пенсионный вклад
	 */
	PENSION("1"),

	/**
	 * Значение "выберите тип счета"
	 */
	EMPTY("2"),
	;

	private final String code;

	private DepositKind(String code)
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
