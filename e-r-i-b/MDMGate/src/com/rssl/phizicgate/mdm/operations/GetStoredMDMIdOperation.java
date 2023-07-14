package com.rssl.phizicgate.mdm.operations;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.mdm.business.profiles.ProfileService;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Операция поиска mdm_id в БД
 */

public class GetStoredMDMIdOperation implements Operation<Long, String>
{
	private static final ProfileService profileService = new ProfileService();

	private Long innerId;

	public void initialize(Long innerId)
	{
		this.innerId = innerId;
	}

	public String execute() throws GateException
	{
		return profileService.getMdmId(innerId);
	}
}
