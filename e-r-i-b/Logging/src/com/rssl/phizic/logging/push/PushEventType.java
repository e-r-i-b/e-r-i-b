package com.rssl.phizic.logging.push;

/**
 * ���� Push-�����������
 * @author basharin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */

public enum PushEventType
{
	/**
	 * ����������� � ����� �� ������ ��������.
	 */
	LOGIN_IN("1"),

	/**
	 * ���������� �� ������ ������
	 */
	HELP("2"),

	/**
	 * ���������� � ������ �� ���������� ��������
	 */
	OPERATION("3"),

	/**
	 * ����������� � ����������� ������� ������������� �����
	 */
	LOGIN_CONFIRM("4"),

	/**
	 * ����������� � ����������� ������� ������������� ��������, �������� ��������, ������������ � ������
	 */
	OPERATION_CONFIRM("5"),

	/**
	 * ����������� �� �������� � ������ ������������� (����� �������)
	 */
	FUND("6");

	PushEventType(String value)
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

	public static PushEventType fromValue(String value)
	{
		if (value.equals(LOGIN_IN.value))
			return LOGIN_IN;
		if (value.equals(HELP.value))
			return HELP;
		if (value.equals(OPERATION.value))
			return OPERATION;
		if (value.equals(LOGIN_CONFIRM.value))
			return LOGIN_CONFIRM;
		if (value.equals(OPERATION_CONFIRM.value))
			return OPERATION_CONFIRM;
		if (value.equals(FUND.value))
			return FUND;

		throw new IllegalArgumentException("����������� ��� push-����������� [" + value + "]");
	}


}
