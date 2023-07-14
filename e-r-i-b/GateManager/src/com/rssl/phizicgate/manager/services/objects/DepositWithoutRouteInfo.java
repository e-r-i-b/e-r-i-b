package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class DepositWithoutRouteInfo extends DepositBase implements RouteInfoReturner
{
	public DepositWithoutRouteInfo(Deposit delegate)
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
