package com.rssl.phizic.business.deposits;

import java.math.BigDecimal;

/**
 * �������������� ���������� �� �������� (���������� �� ��������)
 * @author lepihina
 * @ created 26.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class DepositInfo
{
	private BigDecimal minRate;

	/**
	 * ���������� ����������� ������ �� ��������
	 * @return ����������� ������
	 */
	public BigDecimal getMinRate()
	{
		return minRate;
	}

	/**
	 * ������������� ����������� ������ ��� ����������� ��������
	 * @param minRate - ������
	 */
	public void setMinRate(BigDecimal minRate)
	{
		this.minRate = minRate;
	}
}
