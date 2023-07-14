package com.rssl.phizic.business.dictionaries.pages.messages;

/**
 * Значения, которые может принимать атрибут "Важность" у информационного сообщения
 * @author lepihina
 * @ created 01.11.2011
 * @ $Author$
 * @ $Revision$
 */

public enum Importance
{
	HIGH("1"), // Высокая важность
	MEDIUM("2"), // Средняя важность
	LOW("3");   // Низкая важность

	private String value;

	Importance(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}

	public static Importance fromValue(String value)
	{
		if(value.equals(HIGH.value))
			return HIGH;
		if(value.equals(MEDIUM.value))
			return MEDIUM;
		if(value.equals(LOW.value))
			return LOW;
		
		throw new IllegalArgumentException("Неизвестный тип важности информационного сообщения [" + value + "]");
	}
}