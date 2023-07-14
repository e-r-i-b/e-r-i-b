package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.PersonalFinanceProfileService;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;

/**
 * @author akrenev
 * @ created 24.05.2012
 * @ $Author$
 * @ $Revision$
 */
public class PFPZeroStepOperation extends StartPlaningOperationBase
{
	private Map<String, PersonalFinanceProfile> profiles;

	public void initialize() throws BusinessException
	{
		profiles = getAllProfiles();
	}

	public PersonalFinanceProfile getNotCompletedPFP()
	{
		return profiles.get(PersonalFinanceProfileService.NOT_COMPLETED_PFP);
	}

	public PersonalFinanceProfile getCompletedPFP()
	{
		return profiles.get(PersonalFinanceProfileService.COMPLETED_PFP);
	}

}