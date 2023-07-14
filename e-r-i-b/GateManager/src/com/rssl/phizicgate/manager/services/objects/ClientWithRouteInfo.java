package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientWithRouteInfo extends ClientBase
{
	private String routeInfo;

	public ClientWithRouteInfo(Client delegate, String routeInfo)
	{
		super(delegate);
		this.routeInfo = routeInfo;
	}

	public String getId()
	{
		return IDHelper.storeRouteInfo(delegate.getId(), routeInfo);
	}

	public Long getInternalId()
	{
		return null;
	}
}
