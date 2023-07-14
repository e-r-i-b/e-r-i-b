package com.rssl.phizic.errors;

/**
 * @author gladishev
 * @ created 15.11.2007
 * @ $Author$
 * @ $Revision$
 */

public enum ErrorSystem
{
	Rapida("0"),
	Retail("1"),
	IQWave("2"),
	Enisey("3");

	private String value;

	ErrorSystem(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static ErrorSystem fromValue(String value)
	{
		if (value.equals(Rapida.value)) return Rapida;
		if (value.equals(Retail.value)) return Retail;
		if (value.equals(IQWave.value)) return IQWave;
		if (value.equals(Enisey.value)) return Enisey;
		throw new IllegalArgumentException("Неизвестный тип системы [" + value + "]");
	}
}
