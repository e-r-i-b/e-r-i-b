package com.rssl.phizic.common.types;

/**
 * @author gulov
 * @ created 23.11.2010
 * @ $Authors$
 * @ $Revision$
 */
/**
 * �������� ��������� ����� ������
 */
public enum DynamicExchangeRate
{
	/**
	 * �� ���������
	 */
	NONE(1),
	/**
	 * ����������
	 */
	UP(2),
	/**
	 * ����������
	 */
	DOWN(3);

	private final int value;

	DynamicExchangeRate(int value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static DynamicExchangeRate fromValue(int value)
	{
		if (value == NONE.value)
			return NONE;
		if (value == UP.value)
			return UP;
		if (value == DOWN.value)
			return DOWN;

		throw new IllegalArgumentException("����������� �������� ��������� ����� ������ [" + value + "]");
	}
}
