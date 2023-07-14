package com.rssl.phizic.common.types.basket;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Тип объекта учета
 */
public enum AccountingEntityType
{
	HOUSE("Мой дом"),                    //дом
	FLAT("Моя квартира"),                //квартира
	GARAGE("Мой гараж"),                 //гараж
	CAR("Моя машина");                   //автомобиль

	private String defaultName;

	AccountingEntityType(String defaultName)
	{
		this.defaultName = defaultName;
	}

	/**
	 * @return наименование объекта учета по умолчанию
	 */
	public String getDefaultName()
	{
		return defaultName;
	}

	/**
	 * @return возвращает имя константы. Нужен для получения в jsp
	 */
	public String getCode()
	{
		return this.name();
	}

	public static AccountingEntityType toValue(String name)
	{
		if (name == null)
		{
			return null;
		}

		for (AccountingEntityType type : AccountingEntityType.values())
		{
			if (type.name().equals(name))
			{
				return type;
			}
		}

		return null;
	}
}
