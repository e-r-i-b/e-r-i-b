package com.rssl.phizic.operations.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditComplexInsuranceProductOperation extends EditComplexProductOperation<ComplexInsuranceProduct>
{
	protected Class<ComplexInsuranceProduct> getProductClass()
	{
		return ComplexInsuranceProduct.class;
	}

	protected DictionaryProductType getProductType()
	{
		return DictionaryProductType.COMPLEX_INSURANCE;
	}

	protected ComplexInsuranceProduct getNewProduct()
	{
		ComplexInsuranceProduct product = new ComplexInsuranceProduct();
		product.setInsuranceProducts(new ArrayList<InsuranceProduct>());
		return product;
	}

	/**
	 * задать список страховых продуктов
	 * @param insuranceProductIds идентификаторы страховых продуктов
	 * @throws BusinessException
	 */
	public void setInsuranceProducts(Long[] insuranceProductIds) throws BusinessLogicException, BusinessException
	{
		ComplexInsuranceProduct product = getEntity();
		product.clearInsuranceProducts();
		if (!ArrayUtils.isEmpty(insuranceProductIds))
			product.addInsuranceProducts(productService.getListByIds(insuranceProductIds, InsuranceProduct.class, getInstanceName()));
	}
}
