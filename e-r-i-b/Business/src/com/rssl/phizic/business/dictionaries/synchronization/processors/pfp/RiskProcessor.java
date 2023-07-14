package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;

/**
 * @author akrenev
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей риска
 */

public class RiskProcessor extends PFPProcessorBase<Risk>
{
	@Override
	protected Class<Risk> getEntityClass()
	{
		return Risk.class;
	}

	@Override
	protected Risk getNewEntity()
	{
		return new Risk();
	}

	@Override
	protected void update(Risk source, Risk destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
	}
}
