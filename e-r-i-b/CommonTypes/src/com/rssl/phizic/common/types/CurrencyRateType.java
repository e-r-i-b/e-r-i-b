package com.rssl.phizic.common.types;

/**
 * Тип курса валюты (Курс ЦБ, Курс покупки, Курс продажи)
 *
 * @author Kosyakov
 * @ created 18.10.2006
 * @ $Author: rydvanskiy $
 * @ $Revision: 20783 $
 */
public enum CurrencyRateType
{
	// Курс ЦБ
	CB(0),

	// Курс покупки
	BUY(1),

	// Курс продажи
	SALE(2),

	// Курс удаленной покупки
	BUY_REMOTE(3),

	// Курс удаленной продажи
	SALE_REMOTE(4);

	private int id;

	CurrencyRateType(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public static CurrencyRateType valueOf(int id)
	{
		switch (id)
		{
			case 0:
				return CB;
			case 1:
				return BUY;
			case 2:
				return SALE;
			case 3:
				return BUY_REMOTE;
			case 4:
				return SALE_REMOTE;
		}
		throw new IllegalArgumentException("Неизвестный тип курса валюты [" + id + "]");
	}

	public static CurrencyRateType invert(CurrencyRateType type)
	{
		switch (type)
		{
			case CB:
				return CB;
			case BUY:
				return SALE;
			case SALE:
				return BUY;
			case BUY_REMOTE:
				return SALE_REMOTE;
			case SALE_REMOTE:
				return BUY_REMOTE;
		}
		throw new IllegalArgumentException("Неизвестный тип курса валюты [" + type + "]");
	}
}
