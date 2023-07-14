package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.*;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей страховых продуктов
 */

public class InsuranceProductProcessor extends ProductProcessorBase<InsuranceProduct>
{
	@Override
	protected Class<InsuranceProduct> getEntityClass()
	{
		return InsuranceProduct.class;
	}

	@Override
	protected InsuranceProduct getNewProduct()
	{
		return new InsuranceProduct();
	}

	private void updatePeriodInformation(InsuranceProduct source, InsuranceProduct destination) throws BusinessException
	{
		destination.clearPeriodInformation();
		for (InsuranceDatePeriod sourcePeriod : source.getPeriods())
		{
			InsuranceDatePeriod destinationPeriod = new InsuranceDatePeriod();
			destinationPeriod.setDefaultPeriod(sourcePeriod.getDefaultPeriod());
			destinationPeriod.setType(getLocalVersionByGlobal(sourcePeriod.getType()));
			destinationPeriod.setMinSum(sourcePeriod.getMinSum());
			destinationPeriod.setMaxSum(sourcePeriod.getMaxSum());
			destinationPeriod.setPeriod(sourcePeriod.getPeriod());
			destination.addPeriodInformation(destinationPeriod);
		}
	}

	@Override
	protected void update(InsuranceProduct source, InsuranceProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setInsuranceCompany(getLocalVersionByGlobal(source.getInsuranceCompany()));
		destination.setType(getLocalVersionByGlobal(source.getType()));
		destination.setForComplex(source.isForComplex());
		destination.setMinAge(source.getMinAge());
		destination.setMaxAge(source.getMaxAge());

		updatePeriodInformation(source, destination);
	}
}
