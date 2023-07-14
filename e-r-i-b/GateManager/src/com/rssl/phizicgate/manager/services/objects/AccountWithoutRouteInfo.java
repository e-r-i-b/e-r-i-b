package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountWithoutRouteInfo extends AccountBase implements RouteInfoReturner
{
	public AccountWithoutRouteInfo(Account delegate)
	{
		super(delegate);
	}

	public String getId()
	{
		return IDHelper.restoreOriginalId(delegate.getId());
	}

	public String getRouteInfo()
	{
		return IDHelper.restoreRouteInfo(delegate.getId());
	}
}
