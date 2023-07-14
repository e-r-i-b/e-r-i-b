package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;


/**
 * @author akrenev
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * продукт "доверительное управление"
 */

public class TrustManagingProduct extends SimpleProductBase
{
	private Risk risk;
	private InvestmentPeriod investmentPeriod;

	/**
	 * @return риск
	 */
	public Risk getRisk()
	{
		return risk;
	}

	/**
	 * задать риск
	 * @param risk риск
	 */
	public void setRisk(Risk risk)
	{
		this.risk = risk;
	}

	/**
	 * @return "горизонт инвестирования"
	 */
	public InvestmentPeriod getInvestmentPeriod()
	{
		return investmentPeriod;
	}

	/**
	 * задать "горизонт инвестирования"
	 * @param investmentPeriod "горизонт инвестирования"
	 */
	public void setInvestmentPeriod(InvestmentPeriod investmentPeriod)
	{
		this.investmentPeriod = investmentPeriod;
	}
	
	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.TRUST_MANAGING;
	}

	public void updateFrom(DictionaryRecord that)
	{
		super.updateFrom(that);
		TrustManagingProduct source = (TrustManagingProduct) that;
		setParameters(source.getParameters());
		setRisk(source.getRisk());
		setInvestmentPeriod(source.getInvestmentPeriod());
	}
}
