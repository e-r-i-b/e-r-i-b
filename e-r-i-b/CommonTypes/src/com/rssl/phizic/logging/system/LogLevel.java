package com.rssl.phizic.logging.system;

/**
 * @author Omeliyanchuk
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Уровень записи в журнал
 */
public enum LogLevel
{
	//TRACE
	T("T"),
	//DEBUG
	D("D"),
	//INFO
	I("I"),
	//WARN
	W("W"),
	//ERROR
	E("E"),
	//FATAL
	F("F");

	private String value;

	LogLevel(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}

	public String toString()
	{
		return value;
	}
	
	public static LogLevel fromValue(String value)
	{
		if (value.equals(T.value))
			return T;
		if (value.equals(D.value))
			return D;
		if (value.equals(I.value))
			return I;
		if (value.equals(W.value))
			return W;
		if (value.equals(E.value))
			return E;
		if (value.equals(F.value))
			return F;

		throw new IllegalArgumentException("Неизвестный тип уровеня [" + value + "]");
	}
}
