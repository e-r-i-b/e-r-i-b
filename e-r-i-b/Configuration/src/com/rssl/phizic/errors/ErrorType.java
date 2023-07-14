package com.rssl.phizic.errors;

/**
 * @author gladishev
 * @ created 20.11.2007
 * @ $Author$
 * @ $Revision$
 */

public enum ErrorType
{
	Client("1"),
	Validation("2");

	private String value;

	ErrorType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static ErrorType fromValue(String value)
	{
		if( value.equals(Client.value)) return Client;
		if( value.equals(Validation.value)) return Validation;
		throw new IllegalArgumentException("Неизвестный тип ошибки [" + value + "]");
	}
}
