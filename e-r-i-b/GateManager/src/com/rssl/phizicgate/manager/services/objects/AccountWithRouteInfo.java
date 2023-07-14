package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountWithRouteInfo extends AccountBase
{
	private String routeInfo;

	public AccountWithRouteInfo(Account delegate, String routeInfo)
	{
		super(delegate);
		this.routeInfo = routeInfo;
	}

	public String getId()
	{
		return IDHelper.storeRouteInfo(delegate.getId(), routeInfo);
	}
}
