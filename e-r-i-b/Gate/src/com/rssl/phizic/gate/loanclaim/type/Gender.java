package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���
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
	 * @return �������� � ��������� Transact SM (never null nor empty)
	 */
	public String getCode()
	{
		return code;
	}
}
