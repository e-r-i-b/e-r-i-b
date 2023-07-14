package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей вкладов
 */

public class AccountProductProcessor extends ProductProcessorBase<AccountProduct>
{
	@Override
	protected Class<AccountProduct> getEntityClass()
	{
		return AccountProduct.class;
	}

	@Override
	protected AccountProduct getNewProduct()
	{
		AccountProduct product = new AccountProduct();
		product.setParameters(ProductHelper.getNewProductParametersMap());
		return product;
	}

	@Override
	protected void update(AccountProduct source, AccountProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setAdvisableSum(source.getAdvisableSum());
		destination.setAccountId(source.getAccountId());
		destination.setForComplex(source.getForComplex());
		destination.setSumFactor(source.getSumFactor());
		destination.getParameters().clear();
		destination.getParameters().putAll(source.getParameters());
	}
}
