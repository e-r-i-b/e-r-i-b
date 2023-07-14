package com.rssl.phizic.gate.documents;

/**
 * Тип введенной суммы
 * @author niculichev
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */
public enum InputSumType
{
	CHARGEOFF("charge-off-field-exact"), // сумма списания
	DESTINATION("destination-field-exact"); //сумма зачисления

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
