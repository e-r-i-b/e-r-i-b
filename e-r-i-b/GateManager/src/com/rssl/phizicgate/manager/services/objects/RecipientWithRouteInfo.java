package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class RecipientWithRouteInfo extends RecipientBase
{
	private String routeInfo;

	public RecipientWithRouteInfo(Recipient delegate, String routeInfo)
	{
		super(delegate);
		this.routeInfo = routeInfo;
	}

	public Comparable getSynchKey()
	{
		return IDHelper.storeRouteInfo(delegate.getSynchKey().toString(), routeInfo);
	}
}
