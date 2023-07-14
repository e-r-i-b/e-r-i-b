package com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������������� ��������
 */

public class ProductEfficacy
{
	private BigDecimal fromIncome;
	private BigDecimal toIncome;
	private BigDecimal defaultIncome;
	private String description;

	/**
	 * @return ����������� ����������
	 */
	public BigDecimal getFromIncome()
	{
		return fromIncome;
	}

	/**
	 * ������ ����������� ����������
	 * @param fromIncome ����������
	 */
	public void setFromIncome(BigDecimal fromIncome)
	{
		this.fromIncome = fromIncome;
	}

	/**
	 * @return ������������ ����������
	 */
	public BigDecimal getToIncome()
	{
		return toIncome;
	}

	/**
	 * ������ ������������ ����������
	 * @param toIncome ����������
	 */
	public void setToIncome(BigDecimal toIncome)
	{
		this.toIncome = toIncome;
	}

	/**
	 * @return ���������� �� ���������
	 */
	public BigDecimal getDefaultIncome()
	{
		return defaultIncome;
	}

	/**
	 * ������ ���������� �� ���������
	 * @param defaultIncome ����������
	 */
	public void setDefaultIncome(BigDecimal defaultIncome)
	{
		this.defaultIncome = defaultIncome;
	}

	/**
	 * @return �������� ����
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ������ �������� ����
	 * @param description �������� ����
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
}
