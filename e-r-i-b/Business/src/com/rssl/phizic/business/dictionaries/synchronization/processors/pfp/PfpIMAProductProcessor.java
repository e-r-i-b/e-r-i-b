package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей ОМСов
 */

public class PfpIMAProductProcessor extends ProductProcessorBase<IMAProduct>
{
	@Override
	protected Class<IMAProduct> getEntityClass()
	{
		return IMAProduct.class;
	}

	@Override
	protected IMAProduct getNewProduct()
	{
		IMAProduct product = new IMAProduct();
		product.setParameters(ProductHelper.getNewProductParametersMap());
		return product;
	}

	@Override
	protected void update(IMAProduct source, IMAProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setImaId(source.getImaId());
		destination.setImaAdditionalId(source.getImaAdditionalId());
		destination.setRisk(getLocalVersionByGlobal(source.getRisk()));
		destination.setInvestmentPeriod(getLocalVersionByGlobal(source.getInvestmentPeriod()));
		destination.setForComplex(source.getForComplex());
		destination.setSumFactor(source.getSumFactor());
		destination.getParameters().clear();
		destination.getParameters().putAll(source.getParameters());
	}
}
