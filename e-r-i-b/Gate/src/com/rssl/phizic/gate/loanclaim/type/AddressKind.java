package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ������
 */
public enum AddressKind
{
	/**
	 * ����� ���������� ��������
	 */
	FIXED("1"),

	/**
	 * ����� ������������ ����������
	 */
	RESIDENCE("2"),

	/**
	 * ����� ��������� ��������
	 */
	TEMPORARY("3"),

	;

	private final String code;

	private AddressKind(String code)
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
