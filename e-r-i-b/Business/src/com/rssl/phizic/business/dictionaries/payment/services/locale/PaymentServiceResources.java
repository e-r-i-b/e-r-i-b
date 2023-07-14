package com.rssl.phizic.business.dictionaries.payment.services.locale;

import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;

/**
 * @author mihaylov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Локалезависимые данные услуги
 */
public class PaymentServiceResources extends MultiBlockLanguageResources
{
	private String name;
	private String description;

	/**
	 * @return наименование услуги
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Установить наименование услуги
	 * @param name - наименование услуги
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return описание услуги
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Установить описание услуги
	 * @param description описание услуги
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
