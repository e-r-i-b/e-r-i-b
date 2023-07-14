package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Гражданство
 */
public enum Citizenship
{
	/**
	 * РФ
	 */
	RUSSIA("RUSSIA")

	;

	private final String code;

	private Citizenship(String code)
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
