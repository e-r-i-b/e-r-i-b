package com.rssl.phizic.operations.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositProductShortCut;

/**
 * @author lepihina
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * операция редактирования вклада
 */

public class EditAccountProductOperation extends EditProductOperation<AccountProduct>
{
	private static final DepositProductService depositProductService = new DepositProductService();

	protected Class<AccountProduct> getProductClass()
	{
		return AccountProduct.class;
	}

	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.ACCOUNT;
	}

	protected AccountProduct getNewProduct()
	{
		AccountProduct accountProduct = new AccountProduct();
		accountProduct.setParameters(ProductHelper.getNewProductParametersMap());
		return accountProduct;
	}

	/**
	 * Возвращает название допозитного продукта, к которому привязан данный вклад
	 * @return название допозитного продукта
	 * @throws BusinessException
	 */
	public String getDepositName() throws BusinessException
	{
		AccountProduct accountProduct = getEntity();
		if (accountProduct.getAccountId() == null)
			return null;

		DepositProductShortCut depositProduct = depositProductService.findShortByProductId(accountProduct.getAccountId());
		return depositProduct == null ? null: depositProduct.getName();
	}
}
