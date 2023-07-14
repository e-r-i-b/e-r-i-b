package com.rssl.phizic.gate.documents;

/**
 * ��� ��������� �����
 * @author niculichev
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */
public enum InputSumType
{
	CHARGEOFF("charge-off-field-exact"), // ����� ��������
	DESTINATION("destination-field-exact"); //����� ����������

	private String value;

	InputSumType(String value)
	{
		this.value = value.trim();
	}

	public String toValue()
	{
		return value;
	}

	public static InputSumType fromValue(String stringValue)
	{
		if (("charge-off-field-exact").equals(stringValue == null ? null : stringValue.trim()))
		{
			return InputSumType.CHARGEOFF;
		}
		if (("destination-field-exact").equals(stringValue == null ? null : stringValue.trim()))
		{
			return InputSumType.DESTINATION;
		}
		return null;
	}
}
