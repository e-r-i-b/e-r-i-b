package com.rssl.phizic.business.pfp.portfolio;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 01.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ��� ������ �� ���������� ��������.
 */
public class ProductAmount
{
	private BigDecimal investment; //���������� � �������
	private BigDecimal income;     //����� �� ��������
	private BigDecimal totalAmount;//������ ��������� ��������

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
	 * ������� ��������� ���� ���������
	 * @param productAmount - ��������� ������� ��������
	 * @return ����� ���������� ���������
	 */
	public ProductAmount add(ProductAmount productAmount)
	{
		BigDecimal newInvestment = investment.add(productAmount.getInvestment());
		BigDecimal newIncome = income.add(productAmount.getIncome());
		BigDecimal newTotalAmount = totalAmount.add(productAmount.getTotalAmount());
		return new ProductAmount(newInvestment,newIncome,newTotalAmount);
	}

	/**
	 * ������� ��������� ������� ��������
	 * @param productAmount - ��������� ������� ��������
	 * @return �������� ���������� ���������
	 */
	public ProductAmount sub(ProductAmount productAmount)
	{
		BigDecimal newInvestment = investment.subtract(productAmount.getInvestment());
		BigDecimal newIncome = income.subtract(productAmount.getIncome());
		BigDecimal newTotalAmount = totalAmount.subtract(productAmount.getTotalAmount());
		return new ProductAmount(newInvestment,newIncome,newTotalAmount);
	}
}
