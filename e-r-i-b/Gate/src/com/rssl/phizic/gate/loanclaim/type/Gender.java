package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Пол
 */
public enum Gender
{
	MALE("1"),

	FEMALE("0"),

	;

	private final String code;

	private Gender(String code)
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
