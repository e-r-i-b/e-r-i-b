package com.rssl.phizic.operations.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductHelper;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * операция редактирования и удаления комплексного инвестиционного продукта (вклад + ПИФ + ОМС)
 */

public class EditComplexIMAInvestmentProductOperation extends EditComplexInvestmentProductOperationBase<ComplexIMAInvestmentProduct>
{
	protected Class<ComplexIMAInvestmentProduct> getProductClass()
	{
		return ComplexIMAInvestmentProduct.class;
	}

	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.COMPLEX_INVESTMENT;
	}

	protected ComplexIMAInvestmentProduct getNewProduct()
	{
		ComplexIMAInvestmentProduct product = new ComplexIMAInvestmentProduct();
		product.setFundProducts(new ArrayList<FundProduct>());
		product.setImaProducts(new ArrayList<IMAProduct>());
		product.setParameters(ProductHelper.getNewProductParametersMap());
		return product;
	}

	/**
	 * задать список ОМСов
	 * @param imaProductIds идентификаторы ОМСов
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void setIMAProducts(Long[] imaProductIds) throws BusinessLogicException, BusinessException
	{
		ComplexIMAInvestmentProduct product = getEntity();
		product.clearIMAProducts();
		if (!ArrayUtils.isEmpty(imaProductIds))
			product.addIMAProducts(productService.getListByIds(imaProductIds, IMAProduct.class, getInstanceName()));
	}
}
