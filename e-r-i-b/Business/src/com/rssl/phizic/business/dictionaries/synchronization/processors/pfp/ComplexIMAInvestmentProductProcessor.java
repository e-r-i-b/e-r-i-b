package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей комплексных инвестиционных продуктов (Вклад + ПИФ + ОМС)
 */

public class ComplexIMAInvestmentProductProcessor extends ProductProcessorBase<ComplexIMAInvestmentProduct>
{
	@Override
	protected Class<ComplexIMAInvestmentProduct> getEntityClass()
	{
		return ComplexIMAInvestmentProduct.class;
	}

	@Override
	protected ComplexIMAInvestmentProduct getNewProduct()
	{
		ComplexIMAInvestmentProduct complexProduct = new ComplexIMAInvestmentProduct();
		complexProduct.setImaProducts(new ArrayList<IMAProduct>());
		complexProduct.setParameters(ProductHelper.getNewProductParametersMap());
		complexProduct.setFundProducts(new ArrayList<FundProduct>());
		return complexProduct;
	}

	@Override
	protected void update(ComplexIMAInvestmentProduct source, ComplexIMAInvestmentProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.clearIMAProducts();
		destination.addIMAProducts(getLocalVersionByGlobal(source.getImaProducts()));
		destination.getParameters().clear();
		destination.getParameters().putAll(source.getParameters());
		destination.clearFundProducts();
		destination.addFundProducts(getLocalVersionByGlobal(source.getFundProducts()));
		destination.setAccount(getLocalVersionByGlobal(source.getAccount()));
	}
}
