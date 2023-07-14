package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.TrustManagingProduct;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей продуктов "доверительное управление"
 */

public class TrustManagingProductProcessor extends ProductProcessorBase<TrustManagingProduct>
{
	@Override
	protected Class<TrustManagingProduct> getEntityClass()
	{
		return TrustManagingProduct.class;
	}

	@Override
	protected TrustManagingProduct getNewProduct()
	{
		TrustManagingProduct product = new TrustManagingProduct();
		product.setParameters(ProductHelper.getNewProductParametersMap());
		return product;
	}

	@Override
	protected void update(TrustManagingProduct source, TrustManagingProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setRisk(getLocalVersionByGlobal(source.getRisk()));
		destination.setInvestmentPeriod(getLocalVersionByGlobal(source.getInvestmentPeriod()));
		destination.getParameters().clear();
		destination.getParameters().putAll(source.getParameters());
	}
}
