package com.rssl.phizic.operations.dictionaries.pfp.insurance;

import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.operations.dictionaries.pfp.products.RemoveProductOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * операция удаления страхового продукта
 */

public class RemoveInsuranceProductOperation extends RemoveProductOperationBase<InsuranceProduct>
{
	@Override
	protected Class<InsuranceProduct> getProductClass()
	{
		return InsuranceProduct.class;
	}
}
