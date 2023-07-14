package com.rssl.phizic.business.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;

/**
 * @author akrenev
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 * паевый инвестиционный фонд
 */
public class FundProduct extends InvestmentProduct
{
	public DictionaryProductType getProductType()
	{
		return DictionaryProductType.FUND;
	}
}
