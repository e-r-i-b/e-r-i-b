package com.rssl.phizic.business.dictionaries.providers;

/**
 * @author akrenev
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */

// Тип счета при оплате
public enum AccountType
{
	DEPOSIT(0),         // Счет вклада
	CARD(1),            // Счет карты
	ALL(2);             // Счет вклада и счет карты

	private int value;

	AccountType(int value)
	{
		this.value = value;
	}

	public static AccountType fromValue(int value)
	{
		if (DEPOSIT.value == value)
			return DEPOSIT;
		if (CARD.value == value)
			return CARD;
		if (ALL.value == value)
			return ALL;

		throw new IllegalArgumentException("Неизвестный тип поля [" + value + "]");
	}
}
