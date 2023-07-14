package com.rssl.phizic.business.dictionaries.pfp.products;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 21.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� ���������� ��������, ���������� � ������ ��������
 */

public class ProductParameters
{
	private BigDecimal minSum;

	/**
	 * �����������
	 */
	public ProductParameters()
	{}

	/**
	 * �����������
	 * @param minSum ����������� �����
	 */
	public ProductParameters(BigDecimal minSum)
	{
		this.minSum = minSum;
	}

	/**
	 * @return ����������� �����
	 */
	public BigDecimal getMinSum()
	{
		return minSum;
	}

	/**
	 * ������ ����������� �����
	 * @param minSum ����������� �����
	 */
	public void setMinSum(BigDecimal minSum)
	{
		this.minSum = minSum;
	}

	/**
	 * �������� ������ �������� �� ������ ����������
	 * @param source �������� ������ ��� ����������
	 */
	public void updateFrom(ProductParameters source)
	{
		setMinSum(source.getMinSum());
	}
}
