package com.rssl.phizic.business.pfp.portfolio;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 01.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Класс для работы со стоимостью продукта.
 */
public class ProductAmount
{
	private BigDecimal investment; //инвестиции в продукт
	private BigDecimal income;     //доход от продукта
	private BigDecimal totalAmount;//полная стоимость продукта

	public ProductAmount()
	{
		investment = BigDecimal.ZERO;
		income = BigDecimal.ZERO;
		totalAmount = BigDecimal.ZERO;
	}

	public ProductAmount(BigDecimal investment, BigDecimal income, BigDecimal totalAmount)
	{
		this.investment = investment;
		this.income = income;
		this.totalAmount = totalAmount;
	}

	public BigDecimal getInvestment()
	{
		return investment;
	}

	public void setInvestment(BigDecimal investment)
	{
		this.investment = investment;
	}

	public BigDecimal getIncome()
	{
		return income;
	}

	public void setIncome(BigDecimal income)
	{
		this.income = income;
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	/**
	 * сложить стоимости двух продуктов
	 * @param productAmount - стоимость другого продукта
	 * @return сумма стоимостей продуктов
	 */
	public ProductAmount add(ProductAmount productAmount)
	{
		BigDecimal newInvestment = investment.add(productAmount.getInvestment());
		BigDecimal newIncome = income.add(productAmount.getIncome());
		BigDecimal newTotalAmount = totalAmount.add(productAmount.getTotalAmount());
		return new ProductAmount(newInvestment,newIncome,newTotalAmount);
	}

	/**
	 * Вычесть стоимость другого продукта
	 * @param productAmount - стоимость другого продукта
	 * @return разность стоимостей продуктов
	 */
	public ProductAmount sub(ProductAmount productAmount)
	{
		BigDecimal newInvestment = investment.subtract(productAmount.getInvestment());
		BigDecimal newIncome = income.subtract(productAmount.getIncome());
		BigDecimal newTotalAmount = totalAmount.subtract(productAmount.getTotalAmount());
		return new ProductAmount(newInvestment,newIncome,newTotalAmount);
	}
}
