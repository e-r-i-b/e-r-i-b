package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� �����
 */
public enum CardKind
{
	/**
	 * ���������� �����
	 */
	SALARY("0"),

	/**
	 * ���������� �����
	 */
	PENSION("1"),

	/**
	 * �������� "�������� ��� �����"
	 */
	EMPTY("2"),

	;

	private final String code;

	private CardKind(String code)
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
