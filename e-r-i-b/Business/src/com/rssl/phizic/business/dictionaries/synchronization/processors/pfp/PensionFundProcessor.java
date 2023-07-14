package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей пенсионных фондов
 */

public class PensionFundProcessor extends PFPProcessorBase<PensionFund>
{
	@Override
	protected Class<PensionFund> getEntityClass()
	{
		return PensionFund.class;
	}

	@Override
	protected PensionFund getNewEntity()
	{
		return new PensionFund();
	}

	@Override
	protected void update(PensionFund source, PensionFund destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
	}

	@Override
	protected void doRemove(PensionFund localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
