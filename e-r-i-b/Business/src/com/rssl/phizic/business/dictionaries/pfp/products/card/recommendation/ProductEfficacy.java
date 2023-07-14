package com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Параметры эффективности продукта
 */

public class ProductEfficacy
{
	private BigDecimal fromIncome;
	private BigDecimal toIncome;
	private BigDecimal defaultIncome;
	private String description;

	/**
	 * @return минимальная доходность
	 */
	public BigDecimal getFromIncome()
	{
		return fromIncome;
	}

	/**
	 * задать минимальную доходность
	 * @param fromIncome доходность
	 */
	public void setFromIncome(BigDecimal fromIncome)
	{
		this.fromIncome = fromIncome;
	}

	/**
	 * @return максимальная доходность
	 */
	public BigDecimal getToIncome()
	{
		return toIncome;
	}

	/**
	 * задать максимальную доходность
	 * @param toIncome доходность
	 */
	public void setToIncome(BigDecimal toIncome)
	{
		this.toIncome = toIncome;
	}

	/**
	 * @return доходность по умолчанию
	 */
	public BigDecimal getDefaultIncome()
	{
		return defaultIncome;
	}

	/**
	 * задать доходность по умолчанию
	 * @param defaultIncome доходность
	 */
	public void setDefaultIncome(BigDecimal defaultIncome)
	{
		this.defaultIncome = defaultIncome;
	}

	/**
	 * @return описание шага
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * задать описание шага
	 * @param description описание шага
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
