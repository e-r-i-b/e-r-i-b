package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.PeriodType;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей типов периодов
 */

public class PeriodTypeProcessor extends PFPProcessorBase<PeriodType>
{
	@Override
	protected Class<PeriodType> getEntityClass()
	{
		return PeriodType.class;
	}

	@Override
	protected PeriodType getNewEntity()
	{
		return new PeriodType();
	}

	@Override
	protected void update(PeriodType source, PeriodType destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setMonths(source.getMonths());
	}
}
