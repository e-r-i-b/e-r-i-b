package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей ПИФов
 */

public class FundProductProcessor extends ProductProcessorBase<FundProduct>
{
	@Override
	protected Class<FundProduct> getEntityClass()
	{
		return FundProduct.class;
	}

	@Override
	protected FundProduct getNewProduct()
	{
		FundProduct product = new FundProduct();
		product.setParameters(ProductHelper.getNewProductParametersMap());
		return product;
	}

	@Override
	protected void update(FundProduct source, FundProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setRisk(getLocalVersionByGlobal(source.getRisk()));
		destination.setInvestmentPeriod(getLocalVersionByGlobal(source.getInvestmentPeriod()));
		destination.setForComplex(source.getForComplex());
		destination.setSumFactor(source.getSumFactor());
		destination.getParameters().clear();
		destination.getParameters().putAll(source.getParameters());
	}
}
