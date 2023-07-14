package com.rssl.phizic.business.cardProduct.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * Сущность для хранения мультиязычных текстовок
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class CardProductResources extends LanguageResource
{
	private String name;

	/**
	 * @return название продукта
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name название продукта
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
