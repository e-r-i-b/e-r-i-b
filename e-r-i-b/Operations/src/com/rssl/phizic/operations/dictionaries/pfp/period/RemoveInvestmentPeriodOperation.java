package com.rssl.phizic.operations.dictionaries.pfp.period;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriod;
import com.rssl.phizic.business.dictionaries.pfp.period.InvestmentPeriodService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * операция удаления "горизонта инвестирования"
 */

public class RemoveInvestmentPeriodOperation extends RemoveDictionaryEntityOperationBase
{
	private static final InvestmentPeriodService service = new InvestmentPeriodService();
	private InvestmentPeriod investmentPeriod;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		investmentPeriod = service.getById(id, getInstanceName());
		if (investmentPeriod == null)
			throw new ResourceNotFoundBusinessException("Горизонт инвестирования с id = " + id + " не найден.", InvestmentPeriod.class);
	}

	public InvestmentPeriod getEntity()
	{
		return investmentPeriod;
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		service.remove(investmentPeriod, getInstanceName());
	}
}
