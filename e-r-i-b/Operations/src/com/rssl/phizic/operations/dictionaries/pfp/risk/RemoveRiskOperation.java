package com.rssl.phizic.operations.dictionaries.pfp.risk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.risk.Risk;
import com.rssl.phizic.business.dictionaries.pfp.risk.RiskService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления риска
 */

public class RemoveRiskOperation extends RemoveDictionaryEntityOperationBase
{
	private static final RiskService service = new RiskService();
	private Risk risk;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		risk = service.getById(id, getInstanceName());

		if (risk == null)
			throw new ResourceNotFoundBusinessException("Риск с id = " + id + " не найден.", Risk.class);
	}

	public Risk getEntity()
	{
		return risk;
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		service.remove(risk, getInstanceName());
	}
}
