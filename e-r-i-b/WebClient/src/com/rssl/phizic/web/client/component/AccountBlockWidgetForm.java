package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gulov
 * @ created 20.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class AccountBlockWidgetForm extends ProductBlockWidgetForm
{
	private List<AccountLink> accounts = new ArrayList<AccountLink>();
	private Map<AccountLink, AccountAbstract> accountAbstract;
	private boolean isAllAccountDown;

	private List<Long> showAccountLinkIds;

	public List<AccountLink> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}

	public Map<AccountLink, AccountAbstract> getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(Map<AccountLink, AccountAbstract> accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public boolean isAllAccountDown()
	{
		return isAllAccountDown;
	}

	public void setAllAccountDown(boolean allAccountDown)
	{
		isAllAccountDown = allAccountDown;
	}

	public List<Long> getShowAccountLinkIds()
	{
		return showAccountLinkIds;
	}

	public void setShowAccountLinkIds(List<Long> showAccountLinkIds)
	{
		this.showAccountLinkIds = showAccountLinkIds;
	}
}
