package com.rssl.phizic.common.types;

/**
 * @author gulov
 * @ created 23.11.2010
 * @ $Authors$
 * @ $Revision$
 */
/**
 * Динамика изменения курса валюты
 */
public enum DynamicExchangeRate
{
	/**
	 * Не изменился
	 */
	NONE(1),
	/**
	 * Увеличился
	 */
	UP(2),
	/**
	 * Уменьшился
	 */
	DOWN(3);

	private final int value;

	DynamicExchangeRate(int value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static DynamicExchangeRate fromValue(int value)
	{
		if (value == NONE.value)
			return NONE;
		if (value == UP.value)
			return UP;
		if (value == DOWN.value)
			return DOWN;

		throw new IllegalArgumentException("Неизвестная динамика изменения курса валюты [" + value + "]");
	}
}
