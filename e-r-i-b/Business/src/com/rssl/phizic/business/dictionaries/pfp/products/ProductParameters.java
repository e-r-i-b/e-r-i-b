package com.rssl.phizic.business.dictionaries.pfp.products;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 21.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * набор параметров продукта, уникатьных в рамках портфеля
 */

public class ProductParameters
{
	private BigDecimal minSum;

	/**
	 * конструктор
	 */
	public ProductParameters()
	{}

	/**
	 * конструктор
	 * @param minSum минимальная сумма
	 */
	public ProductParameters(BigDecimal minSum)
	{
		this.minSum = minSum;
	}

	/**
	 * @return минимальная сумма
	 */
	public BigDecimal getMinSum()
	{
		return minSum;
	}

	/**
	 * задать минимальную сумму
	 * @param minSum минимальная сумма
	 */
	public void setMinSum(BigDecimal minSum)
	{
		this.minSum = minSum;
	}

	/**
	 * обновить данные сущности на основе переданной
	 * @param source источник данных для обновления
	 */
	public void updateFrom(ProductParameters source)
	{
		setMinSum(source.getMinSum());
	}
}
