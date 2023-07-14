package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;

/**
 * @author akrenev
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� ���������
 */

public abstract class InvestmentProduct extends Product
{
	private Risk risk;
	private InvestmentPeriod investmentPeriod;

	/**
	 * @return ����
	 */
	public Risk getRisk()
	{
		return risk;
	}

	/**
	 * ������ ����
	 * @param risk ����
	 */
	public void setRisk(Risk risk)
	{
		this.risk = risk;
	}

	/**
	 * @return "�������� ��������������"
	 */
	public InvestmentPeriod getInvestmentPeriod()
	{
		return investmentPeriod;
	}

	/**
	 * ������ "�������� ��������������"
	 * @param investmentPeriod "�������� ��������������"
	 */
	public void setInvestmentPeriod(InvestmentPeriod investmentPeriod)
	{
		this.investmentPeriod = investmentPeriod;
	}
}
