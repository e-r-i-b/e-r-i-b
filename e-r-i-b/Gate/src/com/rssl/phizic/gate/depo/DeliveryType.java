package com.rssl.phizic.gate.depo;

/**
 * @author akrenev
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */
/**
 * Тип доставки.
 */
public enum DeliveryType
{
	/**
	 * Фельдсвязь
	 */
	FELD("1"),
	/**
	 * Служба инкассации
	 */
	INKASS("2"),
	/**
	 * Без доставки
	 */
	NOT_DELIVER("3");


	private String value;

	/**
	 * @return код операции
	 */
	public String getValue()
	{
		return value;
	}

	private DeliveryType(String value)
	{
		this.value = value;
	}
}