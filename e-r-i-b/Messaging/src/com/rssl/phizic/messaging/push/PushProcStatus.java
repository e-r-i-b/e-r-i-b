package com.rssl.phizic.messaging.push;

/**
 * ��������� push �����������
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public enum PushProcStatus
{
	/**
	 * ������ � ���������
	 */
	READY("E"),

	/**
	 * ��������������� �� ���������
	 */
	RESERVED("R"),

	/**
	 * ����������
	 */
	SEND("P"),

	/**
	 * ������ ���������
	 */
	ERROR("F");

	PushProcStatus(String value)
	{
		this.value = value;
	}

	/**
	 * ��� �������
	 */
	private final String value;

	public String toValue()
	{
		return value;
	}

	public static PushProcStatus fromValue(String value)
	{
		if (value.equals(READY.value))
			return READY;
		if (value.equals(RESERVED.value))
			return RESERVED;
		if (value.equals(SEND.value))
			return SEND;
		if (value.equals(ERROR.value))
			return ERROR;

		throw new IllegalArgumentException("����������� ������ ��������� [" + value + "]");
	}

}
