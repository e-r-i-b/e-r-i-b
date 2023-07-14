package com.rssl.phizic.operations.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления риск-профиля
 */

public class RemoveRiskProfileOperation extends RemoveDictionaryEntityOperationBase
{
	private static final RiskProfileService riskProfileService = new RiskProfileService();
	private RiskProfile profile;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		profile = riskProfileService.getById(id, getInstanceName());

		if(profile == null)
			throw new ResourceNotFoundBusinessException("В системе не найден риск-профиль с id: " + id, RiskProfile.class);
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		riskProfileService.remove(profile, getInstanceName());
	}

	public RiskProfile getEntity()
	{
		return profile;
	}
}
