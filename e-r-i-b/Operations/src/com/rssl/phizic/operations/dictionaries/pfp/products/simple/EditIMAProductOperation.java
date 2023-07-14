package com.rssl.phizic.operations.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import com.rssl.phizic.business.ima.IMAProductPart;
import com.rssl.phizic.business.ima.IMAProductService;

/**
 * @author lepihina
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * операция редактирования ОМСа
 */

public class EditIMAProductOperation extends EditInvestmentProductOperationBase<IMAProduct>
{
	private static final IMAProductService imaProductService = new IMAProductService();

	protected Class<IMAProduct> getProductClass()
	{
		return IMAProduct.class;
	}

	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.IMA;
	}

	protected IMAProduct getNewProduct()
	{
		IMAProduct imaProduct = new IMAProduct();
		imaProduct.setParameters(ProductHelper.getNewProductParametersMap());
		return imaProduct;
	}

	/**
	 * Возвращает название ОМС, к которому привязан данный ОМС(ПФП)
	 * @return название ОМС
	 */
	public String getIMAName() throws BusinessException
	{
		IMAProduct imaPfpProduct = getEntity();
		if (imaPfpProduct.getImaId() == null)
			return null;

		IMAProductPart imaProduct = imaProductService.findByExternalId(imaPfpProduct.getImaId(), imaPfpProduct.getImaAdditionalId());
		return imaProduct == null ? null : imaProduct.getName();
	}
}
