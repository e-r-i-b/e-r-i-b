package com.rssl.phizic.business.creditcards.products.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * —ущность дл€ хранени€ мульти€зычных текстовок
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class CreditCardProductResources extends LanguageResource
{
	private String name;
	private String additionalTerms;

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

	/**
	 * @return дополнительные услови€
	 */
	public String getAdditionalTerms()
	{
		return additionalTerms;
	}

	/**
	 * @param additionalTerms дополнительные услови€
	 */

	public void setAdditionalTerms(String additionalTerms)
	{
		this.additionalTerms = additionalTerms;
	}
}
