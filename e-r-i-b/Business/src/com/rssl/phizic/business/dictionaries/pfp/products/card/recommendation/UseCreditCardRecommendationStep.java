package com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ўаг в рекоммендации
 */

public class UseCreditCardRecommendationStep
{
	private String name;
	private String description;

	/**
	 * конструктор
	 */
	public UseCreditCardRecommendationStep(){}

	/**
	 * @return им€ шага
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать им€ шага
	 * @param name им€ шага
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * задать описание
	 * @param description описание
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
