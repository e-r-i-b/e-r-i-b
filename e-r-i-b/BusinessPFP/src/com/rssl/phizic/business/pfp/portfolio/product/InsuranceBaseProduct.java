package com.rssl.phizic.business.pfp.portfolio.product;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 12.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Страховой продукт в портфеле клиента
 */
public class InsuranceBaseProduct extends BaseProduct implements LinkedBaseProduct
{
	private static final String SELECTED_PERIOD_ID_KEY = "selectedPeriodId";
	private static final String SELECTED_PERIOD_NAME_KEY = "selectedPeriodName";
	private static final String SELECTED_TERM_KEY = "selectedTerm";
	private static final String INSURANCE_COMPANY_KEY = "insuranceCompany";
	private static final String MONTH_IN_PERIOD_NAME_KEY = "monthInPeriod";
	private static final String INSURANCE_SUM_NAME_KEY = "insuranceSum";
	private static final String LINKED_PRODUCT_ID_KEY = "linkedProductId";

	/**
	 * Установить идентификатор выбранного периода
	 * @param selectedPeriodId
	 */
	public void setSelectedPeriodId(Long selectedPeriodId)
	{
		addExtendedField(SELECTED_PERIOD_ID_KEY,selectedPeriodId);
	}

	/**
	 * @return Идентификатор выбранного периода
	 */
	public Long getSelectedPeriodId()
	{
		return getExtendedFieldLongValue(SELECTED_PERIOD_ID_KEY);
	}

	/**
	 * Установить название выбранного периода
	 * @param selectedPeriod - название выбранного периода
	 */
	public void setSelectedPeriodName(String selectedPeriod)
	{
		addExtendedField(SELECTED_PERIOD_NAME_KEY,selectedPeriod);
	}

	/**
	 * @return получить название выбранного периода
	 */
	public String getSelectedPeriodName()
	{
		return getExtendedFieldStringValue(SELECTED_PERIOD_NAME_KEY);
	}

	/**
	 * Установить срок страховой программы
	 * @param selectedTerm
	 */
	public void setSelectedTermValue(Long selectedTerm)
	{
		addExtendedField(SELECTED_TERM_KEY,selectedTerm);
	}

	/**
	 * @return срок страховой программы
	 */
	public Long getSelectedTermValue()
	{
		return getExtendedFieldLongValue(SELECTED_TERM_KEY);
	}

	/**
	 * Установить название страховой компании
	 * @param insuranceCompany
	 */
	public void setInsuranceCompanyName(String insuranceCompany)
	{
		addExtendedField(INSURANCE_COMPANY_KEY,insuranceCompany);
	}

	/**
 	 * @return название страховой компании
	 */
	public String getInsuranceCompanyName()
	{
		return getExtendedFieldStringValue(INSURANCE_COMPANY_KEY);
	}

	/**
	 * Устанавливает количество месяцев в периоде для страхового продукта
	 * @param month - кол-во месяцев
	 */
	public void setMonthInPeriod(Long month)
	{
		addExtendedField(MONTH_IN_PERIOD_NAME_KEY,month);
	}

	/**
	 * @return Количество месяцев в выбранном периоде
	 */
	public Long getMonthInPeriod()
	{
		return getExtendedFieldLongValue(MONTH_IN_PERIOD_NAME_KEY);
	}

	/**
	 * Устанавливает Страховую сумму для страхового продукта
	 * @param sum - Страховая сумма
	 */                      
	public void setInsuranceSum(BigDecimal sum)
	{
		addExtendedField(INSURANCE_SUM_NAME_KEY, sum);
	}

	/**
	 * @return Страховая сумма
	 */
	public BigDecimal getInsuranceSum()
	{
		return getExtendedFieldBigDecimalValue(INSURANCE_SUM_NAME_KEY);
	}

	public void setLinkedProductId(Long id)
	{
		addExtendedField(LINKED_PRODUCT_ID_KEY,id);
	}

	public Long getLinkedProductId()
	{
		return getExtendedFieldLongValue(LINKED_PRODUCT_ID_KEY);
	}
}
