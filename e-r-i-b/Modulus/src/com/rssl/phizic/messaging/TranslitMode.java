package com.rssl.phizic.messaging;

/**
 * @author Erkin
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� �������������� ���������
 */
public enum TranslitMode
{
	/**
	 * �������� ��-���������
	 * ������ �������� �� �����������������
	 * (����� ��������, ����� ��-������� ������)
	 */
	DEFAULT("0"),

	/**
	 * �����������������
	 */
	TRANSLIT("1");

	private String value;

	TranslitMode(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static TranslitMode fromValue(String value)
	{
		if (value.equals(TRANSLIT.value))
			return TRANSLIT;
		if (value.equals(DEFAULT.value))
			return DEFAULT;
		throw new IllegalArgumentException("����������� ����� �������������� [" + value + "]");
	}
}
