package com.rssl.phizic.web.client.securities;

import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.web.actions.ActionFormBase;


import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */

public class ListSecuritiesForm  extends ActionFormBase
{
	private List<SecurityAccountLink> securityLinks;
	private Map<SecurityAccountLink, SecurityAccount> securityAccounts;
	private boolean isBackError;
	private boolean isUseStoredResource;

	public List<SecurityAccountLink> getSecurityLinks()
	{
		return securityLinks;
	}

	public void setSecurityLinks(List<SecurityAccountLink> securityLinks)
	{
		this.securityLinks = securityLinks;
	}

	public Map<SecurityAccountLink, SecurityAccount> getSecurityAccounts()
	{
		return securityAccounts;
	}

	public void setSecurityAccounts(Map<SecurityAccountLink, SecurityAccount> securityAccounts)
	{
		this.securityAccounts = securityAccounts;
	}

	public boolean isBackError()
	{
		return isBackError;
	}

	public void setBackError(boolean backError)
	{
		isBackError = backError;
	}

	public boolean getUseStoredResource()
	{
		return isUseStoredResource;
	}

	public void setUseStoredResource(boolean useStoredResource)
	{
		isUseStoredResource = useStoredResource;
	}
}
