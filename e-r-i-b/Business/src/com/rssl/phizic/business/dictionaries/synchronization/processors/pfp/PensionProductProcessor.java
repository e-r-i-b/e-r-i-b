package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей пенсионных продуктов
 */

public class PensionProductProcessor extends ProductProcessorBase<PensionProduct>
{
	@Override
	protected Class<PensionProduct> getEntityClass()
	{
		return PensionProduct.class;
	}

	@Override
	protected PensionProduct getNewProduct()
	{
		return new PensionProduct();
	}

	@Override
	protected void update(PensionProduct source, PensionProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setPensionFund(getLocalVersionByGlobal(source.getPensionFund()));
		destination.setEntryFee(source.getEntryFee());
		destination.setQuarterlyFee(source.getQuarterlyFee());
		destination.setMinPeriod(source.getMinPeriod());
		destination.setMaxPeriod(source.getMaxPeriod());
		destination.setDefaultPeriod(source.getDefaultPeriod());
	}
}
