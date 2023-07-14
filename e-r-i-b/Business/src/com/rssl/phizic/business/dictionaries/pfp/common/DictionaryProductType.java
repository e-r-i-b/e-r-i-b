package com.rssl.phizic.business.dictionaries.pfp.common;

/**
 * @author mihaylov
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Тип продукта для которого заводим отдельную сущность в справочнике
 */

public enum DictionaryProductType
{
	INSURANCE("Банковское страхование"),
	PENSION("Пенсионный продукт"),
	COMPLEX_INSURANCE("Комплексный страховый продукт"),
	COMPLEX_INVESTMENT("Комплексный инвестиционный продукт"),
	COMPLEX_INVESTMENT_FUND("Комплексный инвестиционный продукт: Депозит + ПИФ"),
	COMPLEX_INVESTMENT_FUND_IMA("Комплексный инвестиционный продукт: Депозит + ПИФ + ОМС"),
	ACCOUNT("Вклад"),
	FUND("Паевой инвестиционный фонд"),
	IMA("Обезличенный металлический счет"),
	TRUST_MANAGING("Доверительное управление");

	private final String description;

	private DictionaryProductType(String description)
	{
		this.description = description;
	}

	/**
	 * @return описание типа продукта
	 */
	public String getDescription()
	{
		return description;
	}
}
