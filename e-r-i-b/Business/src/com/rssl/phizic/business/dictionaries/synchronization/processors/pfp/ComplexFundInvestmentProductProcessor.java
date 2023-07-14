package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexFundInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей комплексных инвестиционных продуктов (Вклад + ПИФ)
 */

public class ComplexFundInvestmentProductProcessor extends ProductProcessorBase<ComplexFundInvestmentProduct>
{
	@Override
	protected Class<ComplexFundInvestmentProduct> getEntityClass()
	{
		return ComplexFundInvestmentProduct.class;
	}

	@Override
	protected ComplexFundInvestmentProduct getNewProduct()
	{
		ComplexFundInvestmentProduct complexProduct = new ComplexFundInvestmentProduct();
		complexProduct.setParameters(ProductHelper.getNewProductParametersMap());
		complexProduct.setFundProducts(new ArrayList<FundProduct>());
		return complexProduct;
	}

	@Override
	protected void update(ComplexFundInvestmentProduct source, ComplexFundInvestmentProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.getParameters().clear();
		destination.getParameters().putAll(source.getParameters());
		destination.clearFundProducts();
		destination.addFundProducts(getLocalVersionByGlobal(source.getFundProducts()));
		destination.setAccount(getLocalVersionByGlobal(source.getAccount()));
	}
}
