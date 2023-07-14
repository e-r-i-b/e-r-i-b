package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Вид адреса
 */
public enum AddressKind
{
	/**
	 * Адрес постоянной прописки
	 */
	FIXED("1"),

	/**
	 * Адрес фактического проживания
	 */
	RESIDENCE("2"),

	/**
	 * Адрес временной прописки
	 */
	TEMPORARY("3"),

	;

	private final String code;

	private AddressKind(String code)
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
