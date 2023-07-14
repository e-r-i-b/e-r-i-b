package com.rssl.phizic.business.dictionaries.productRequirements;

/**
 * Значения, которые может принимать атрибут "Статус" у требования к продукту клиента
 * @author lepihina
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public enum RequirementState
{
	//Подключен
	CONNECTED("1"),
	//Не подключен
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

		throw new IllegalArgumentException("Неизвестный тип статус требования к продукту клиента [" + value + "]");
	}
}
