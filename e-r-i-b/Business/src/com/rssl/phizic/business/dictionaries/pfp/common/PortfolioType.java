package com.rssl.phizic.business.dictionaries.pfp.common;

import com.rssl.phizic.utils.DateHelper;

/**
 * @author mihaylov
 * @ created 08.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Тип портфеля, для формирования при прохождении ПФП
 */

public enum PortfolioType
{
	START_CAPITAL("Стартовый капитал", null),
	QUARTERLY_INVEST("Ежеквартальные вложения", (long) DateHelper.MONTH_IN_QUARTER)
	{
		public boolean isProductTypeAvailable(DictionaryProductType productType)
		{
			return productType != DictionaryProductType.INSURANCE
					&& productType != DictionaryProductType.COMPLEX_INSURANCE
					&& productType != DictionaryProductType.PENSION;
		}
	};

	private final String description;
	private final Long monthCount;

	private PortfolioType(String description, Long monthCount)
	{
		this.description = description;
		this.monthCount = monthCount;
	}

	/**
	 * @return описание портфеля
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Проверяет доступен ли данный продукт в портфеле
	 * @param productType - тип продукта
	 * @return доступен ли данный продукт в портфеле
	 */
	public boolean isProductTypeAvailable(DictionaryProductType productType)
	{
		return true;
	}

	/**
	 * @return количество месяцев связанное с портфелем
	 */
	public Long getMonthCount()
	{
		return monthCount;
	}
}
