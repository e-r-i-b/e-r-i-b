package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей комплексных страховых продуктов
 */

public class ComplexInsuranceProductProcessor extends ProductProcessorBase<ComplexInsuranceProduct>
{
	@Override
	protected Class<ComplexInsuranceProduct> getEntityClass()
	{
		return ComplexInsuranceProduct.class;
	}

	@Override
	protected ComplexInsuranceProduct getNewProduct()
	{
		ComplexInsuranceProduct complexInsuranceProduct = new ComplexInsuranceProduct();
		complexInsuranceProduct.setInsuranceProducts(new ArrayList<InsuranceProduct>());
		return complexInsuranceProduct;
	}

	@Override
	protected void update(ComplexInsuranceProduct source, ComplexInsuranceProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setAccount(getLocalVersionByGlobal(source.getAccount()));
		destination.setMinSum(source.getMinSum());
		destination.clearInsuranceProducts();
		destination.addInsuranceProducts(getLocalVersionByGlobal(source.getInsuranceProducts()));
		destination.setMinSumInsurance(source.getMinSumInsurance());
	}
}
