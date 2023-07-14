package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Способ погашения кредита
 */
public enum NameChangeReason
{
	/**
	 * Брак
	 */
	MARRIAGE("MARRIAGE"),

	/**
	 * Иное
	 */
	OTHER("OTHER"),

	;

	private final String code;

	private NameChangeReason(String code)
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
