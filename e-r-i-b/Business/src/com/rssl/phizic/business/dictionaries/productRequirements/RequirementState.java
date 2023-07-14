package com.rssl.phizic.business.dictionaries.productRequirements;

/**
 * ��������, ������� ����� ��������� ������� "������" � ���������� � �������� �������
 * @author lepihina
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public enum RequirementState
{
	//���������
	CONNECTED("1"),
	//�� ���������
	NOTCONNECTED("0");

	private String value;

	RequirementState(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}

	public static RequirementState fromValue(String value)
	{
		if(value.equals(CONNECTED.value))
			return CONNECTED;
		if(value.equals(NOTCONNECTED.value))
			return NOTCONNECTED;

		throw new IllegalArgumentException("����������� ��� ������ ���������� � �������� ������� [" + value + "]");
	}
}
