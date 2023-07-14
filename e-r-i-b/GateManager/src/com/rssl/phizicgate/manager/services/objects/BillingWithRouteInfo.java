package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class BillingWithRouteInfo extends BillingBase
{
	private String routeInfo;

	public BillingWithRouteInfo(Billing delegate, String routeInfo)
	{
		super(delegate);
		this.routeInfo = routeInfo;
	}

	public Comparable getSynchKey()
	{
		return IDHelper.storeRouteInfo(delegate.getSynchKey().toString(), routeInfo);
	}
}
