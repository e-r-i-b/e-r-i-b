package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей горизонт инвестирования
 */

public class InvestmentPeriodProcessor extends PFPProcessorBase<InvestmentPeriod>
{
	@Override
	protected Class<InvestmentPeriod> getEntityClass()
	{
		return InvestmentPeriod.class;
	}

	@Override
	protected InvestmentPeriod getNewEntity()
	{
		return new InvestmentPeriod();
	}

	@Override
	protected void update(InvestmentPeriod source, InvestmentPeriod destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setPeriod(source.getPeriod());
	}
}
