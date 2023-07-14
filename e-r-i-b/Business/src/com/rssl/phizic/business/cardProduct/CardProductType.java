package com.rssl.phizic.business.cardProduct;

/**
 * @author gulov
 * @ created 29.09.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Тип карточного продукта
 */
public enum CardProductType
{
	/**
	 * Виртуальная карта
	 */
	VIRTUAL(1);

	private final int value;

	CardProductType(int value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static CardProductType fromValue(int value)
	{
		switch (value)
		{
			case 1:
				return VIRTUAL;
			default:
				throw new IllegalArgumentException("Неизвестный тип карты [" + value + "]");
		}
			
	}
}
