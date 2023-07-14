package com.rssl.phizic.gate.bki;

/**
 * @author Erkin
 * @ created 24.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� ������� � ���
 */
public enum BKIRequestType
{
	/**
	 * ����������� ������: �������� ������� �� ������� � ���
	 */
	BKICheckCreditHistory("51"),

	/**
	 * ������ ������: �������� ������ �� �� ���
	 */
	BKIGetCreditHistory("52"),

	;

	/**
	 * ��� � ���
	 */
	public final String code;

	private BKIRequestType(String code)
	{
		this.code = code;
	}

	/**
	 * �������� ��� �� ����
	 * @param code - ���
	 * @return ��� (never null)
	 * @throws IllegalArgumentException ���� ������ ����������� ���
	 */
	public static BKIRequestType fromCode(String code)
	{
		for (BKIRequestType type : values())
		{
			if (type.code.equals(code))
				return type;
		}

		throw new IllegalArgumentException("����������� ��� ������� � ���: " + code);
	}
}
