package com.rssl.phizic.gate.loanclaim.type;

/**
 * @author Erkin
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��������� �������
 */
public enum SquareUnits
{
	/**
	 * ���������� ����
	 */
	METER("1", "��.�"),

	/**
	 * �����
	 */
	HUNDRED("2", "�����"),

	;

	private final String code;
	private final String description;

	private SquareUnits(String code, String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * @return �������� � ��������� Transact SM (never null nor empty)
	 */
	public String getCode()
	{
		return code;
	}

	public String getDescription()
	{
		return description;
	}
}
