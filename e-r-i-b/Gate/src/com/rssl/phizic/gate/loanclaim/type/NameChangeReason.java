package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��������� �������
 */
public enum NameChangeReason
{
	/**
	 * ����
	 */
	MARRIAGE("MARRIAGE"),

	/**
	 * ����
	 */
	OTHER("OTHER"),

	;

	private final String code;

	private NameChangeReason(String code)
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
