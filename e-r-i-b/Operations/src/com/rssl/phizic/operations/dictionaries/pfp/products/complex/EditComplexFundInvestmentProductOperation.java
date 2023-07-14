package com.rssl.phizic.operations.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexFundInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * операция редактирования и удаления комплексного инвестиционного продукта (вклад + ПИФ)
 */

public class EditComplexFundInvestmentProductOperation extends EditComplexInvestmentProductOperationBase<ComplexFundInvestmentProduct>
{
	protected Class<ComplexFundInvestmentProduct> getProductClass()
	{
		return ComplexFundInvestmentProduct.class;
	}

	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.COMPLEX_INVESTMENT;
	}

	protected ComplexFundInvestmentProduct getNewProduct()
	{
		ComplexFundInvestmentProduct product = new ComplexFundInvestmentProduct();
		product.setFundProducts(new ArrayList<FundProduct>());
		product.setParameters(ProductHelper.getNewProductParametersMap());
		return product;
	}
}