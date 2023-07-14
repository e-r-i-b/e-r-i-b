package com.rssl.phizic.business.deposits;

import java.math.BigDecimal;

/**
 * ƒополнительна€ информаци€ по депозиту (полученна€ из описани€)
 * @author lepihina
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class DepositInfo
{
	private BigDecimal minRate;

	/**
	 * ¬озвращает минимальную ставку по продукту
	 * @return минимальна€ ставка
	 */
	public BigDecimal getMinRate()
	{
		return minRate;
	}

	/**
	 * ”станавливает минимальную ставку дл€ депозитного продукта
	 * @param minRate - ставка
	 */
	public void setMinRate(BigDecimal minRate)
	{
		this.minRate = minRate;
	}
}
