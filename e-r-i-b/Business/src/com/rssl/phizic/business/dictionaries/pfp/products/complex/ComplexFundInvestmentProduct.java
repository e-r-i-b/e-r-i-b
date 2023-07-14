package com.rssl.phizic.business.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

/**
 * @author akrenev
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Комплексный инвестиционный продукт (Депозит + ПИФ)
 */
public class ComplexFundInvestmentProduct extends ComplexInvestmentProductBase
{
	@Override
	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.COMPLEX_INVESTMENT_FUND;
	}
}
