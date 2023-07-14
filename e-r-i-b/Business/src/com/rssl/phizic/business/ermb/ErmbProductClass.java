package com.rssl.phizic.business.ermb;


/**
 * User: moshenko
 * Date: 12.03.2013
 * Time: 16:01:01
 * Классы продуктов ермб
 */
public enum ErmbProductClass

{
	preferential("Льготный"),
	premium("Премиум"),
	social("Социальный"),
	standart("Стандарт");


	private String value;
	ErmbProductClass(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
