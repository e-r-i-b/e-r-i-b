package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей кредитных продуктов
 */

public class LoanKindProductProcessor extends PFPProcessorBase<LoanKindProduct>
{
	@Override
	protected Class<LoanKindProduct> getEntityClass()
	{
		return LoanKindProduct.class;
	}

	@Override
	protected LoanKindProduct getNewEntity()
	{
		return new LoanKindProduct();
	}

	@Override
	protected void update(LoanKindProduct source, LoanKindProduct destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setFromAmount(source.getFromAmount());
		destination.setToAmount(source.getToAmount());
		destination.setFromPeriod(source.getFromPeriod());
		destination.setToPeriod(source.getToPeriod());
		destination.setDefaultPeriod(source.getDefaultPeriod());
		destination.setFromRate(source.getFromRate());
		destination.setToRate(source.getToRate());
		destination.setDefaultRate(source.getDefaultRate());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
	}

	@Override
	protected void doRemove(LoanKindProduct localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
