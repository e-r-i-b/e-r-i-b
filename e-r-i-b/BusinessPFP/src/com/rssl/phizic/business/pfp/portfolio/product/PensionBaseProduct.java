package com.rssl.phizic.business.pfp.portfolio.product;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 * Пенсионный продукт в портфеле клиента
 */
public class PensionBaseProduct extends BaseProduct implements LinkedBaseProduct
{
	private static final String SELECTED_PERIOD_KEY = "selectedPeriodKey";
	private static final String QURTERLY_INVEST_KEY = "quarterlyInvestKey";
	private static final String LINKED_PRODUCT_ID_KEY = "linkedProductId";
	private static final String PENSION_FUND_KEY = "pensionFund";

	/**
	 * Установить срок продукта
	 * @param period срок
	 */
	public void setSelectedPeriod(Long period)
	{
		addExtendedField(SELECTED_PERIOD_KEY,period);
	}

	/**
	 * @return срок продукта
	 */
	public Long getSelectedPeriod()
	{
		return getExtendedFieldLongValue(SELECTED_PERIOD_KEY);
	}

	public void setQurterlyInvest(BigDecimal qurterlyInvest)
	{
		addExtendedField(QURTERLY_INVEST_KEY,qurterlyInvest);
	}

	public BigDecimal getQurterlyInvest()
	{
		return getExtendedFieldBigDecimalValue(QURTERLY_INVEST_KEY);
	}

	public void setLinkedProductId(Long id)
	{
		addExtendedField(LINKED_PRODUCT_ID_KEY,id);
	}

	public Long getLinkedProductId()
	{
		return getExtendedFieldLongValue(LINKED_PRODUCT_ID_KEY);
	}

	/**
	 * задать название пенсионного фонда
	 * @param pensionFund название пенсионного фонда
	 */
	public void setPensionFund(String pensionFund)
	{
		addExtendedField(PENSION_FUND_KEY, pensionFund);
	}

	/**
	 * @return название пенсионного фонда
	 */
	public String getPensionFund()
	{
		return getExtendedFieldStringValue(PENSION_FUND_KEY);
	}
}
