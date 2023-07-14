package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.List;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class AccountListRestrictionForm extends RestrictionFormBase
{
	private List<AccountLink> accounts;

	public List<AccountLink> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}
}