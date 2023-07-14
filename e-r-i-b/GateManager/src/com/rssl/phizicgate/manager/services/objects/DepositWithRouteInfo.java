package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class DepositWithRouteInfo extends DepositBase
{
	private String routeInfo;

	public DepositWithRouteInfo(Deposit delegate, String routeInfo)
	{
		super(delegate);
		this.routeInfo = routeInfo;
	}

	public String getId()
	{
		return IDHelper.storeRouteInfo(delegate.getId(), routeInfo);
	}
}
