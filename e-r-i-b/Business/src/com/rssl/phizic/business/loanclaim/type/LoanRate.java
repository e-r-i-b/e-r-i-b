package com.rssl.phizic.business.loanclaim.type;

import java.math.BigDecimal;

/**
 * ���������� ������ �� �������
 * @author Rtischeva
 * @ created 07.03.14
 * @ $Author$
 * @ $Revision$
 */
public class LoanRate
{
	private BigDecimal minLoanRate; //����������� ���������� ������
	private BigDecimal maxLoanRate; //������������ ���������� ������

	public LoanRate(BigDecimal minLoanRate)
	{
		if (minLoanRate == null)
			throw new IllegalArgumentException("�������� minLoanRate �� ����� ���� null");

		this.minLoanRate = minLoanRate;
		this.maxLoanRate = minLoanRate;
	}

	public LoanRate(BigDecimal minLoanRate, BigDecimal maxLoanRate)
	{
		if (minLoanRate == null || maxLoanRate == null)
			throw new IllegalArgumentException("��������� minLoanRate �  maxLoanRate �� ����� ���� null");

		this.minLoanRate = minLoanRate;
		this.maxLoanRate = maxLoanRate;
	}

	public BigDecimal getMinLoanRate()
	{
		return minLoanRate;
	}

	public BigDecimal getMaxLoanRate()
	{
		return maxLoanRate;
	}
}
