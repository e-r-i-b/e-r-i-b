package com.rssl.phizic.operations.securities;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.insurance.BusinessProcess;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.resources.external.security.StoredSecurityAccount;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.insurance.InsuranceService;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.gate.security.SecurityAccountService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class GetSecurityDetailOperation extends OperationBase implements ViewEntityOperation
{
	private SecurityAccountLink securityAccountLink;
	private SecurityAccount securityAccount;
	private boolean isBackError;
	private boolean isUseStoredResource;

	public void initialize(Long linkId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		securityAccountLink = personData.getSecurityAccountLink(linkId);
		securityAccount = securityAccountLink.getSecurityAccount();

		if(MockHelper.isMockObject(securityAccount))
			isBackError = true;

		if (securityAccount instanceof StoredSecurityAccount)
			isUseStoredResource = true;
	}

	public SecurityAccountLink getEntity()
	{
		return securityAccountLink;
	}

	public boolean isBackError()
	{
		return isBackError;
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	public SecurityAccount getSecurityAccount()
	{
		return securityAccount;
	}
}

