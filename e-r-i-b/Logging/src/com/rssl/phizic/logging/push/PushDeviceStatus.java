package com.rssl.phizic.logging.push;

/**
 * ������ ����������� push ����������� � ����������
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public enum PushDeviceStatus
{
	/**
	 * ����� �����������
	 */
	ADD("A"),

	/**
	 * ���������� security token
	 */
	UPDATE("U"),

	/**
	 * �������� �����������
	 */
	DELETE("D");

	PushDeviceStatus(String value)
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

	@Override public String toString()
	{
		return value;
	}

	public static PushDeviceStatus fromValue(String value)
	{
		if (value.equals(ADD.value))
			return ADD;
		if (value.equals(UPDATE.value))
			return UPDATE;
		if (value.equals(DELETE.value))
			return DELETE;

		throw new IllegalArgumentException("����������� ������ ����������� [" + value + "]");
	}

}

