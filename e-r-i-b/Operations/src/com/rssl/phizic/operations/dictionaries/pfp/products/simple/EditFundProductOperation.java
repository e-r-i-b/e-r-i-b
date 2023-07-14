package com.rssl.phizic.operations.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;

/**
 * @author akrenev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * операция редактирования ПИФа
 */

public class EditFundProductOperation extends EditInvestmentProductOperationBase<FundProduct>
{
	protected Class<FundProduct> getProductClass()
	{
		return FundProduct.class;
	}

	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.FUND;
	}

	protected FundProduct getNewProduct()
	{
		FundProduct fundProduct = new FundProduct();
		fundProduct.setParameters(ProductHelper.getNewProductParametersMap());
		return fundProduct;
	}
}
