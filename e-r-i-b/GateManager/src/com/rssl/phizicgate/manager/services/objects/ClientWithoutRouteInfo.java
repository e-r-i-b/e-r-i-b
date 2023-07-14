package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizic.gate.clients.Client;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientWithoutRouteInfo extends ClientBase implements RouteInfoReturner
{
	public ClientWithoutRouteInfo(Client delegate)
	{
		super(delegate);
	}

	public String getId()
	{
		return IDHelper.restoreOriginalId(delegate.getId());
	}

	public Long getInternalId()
	{
		return null;
	}

	public String getRouteInfo()
	{
		return IDHelper.restoreRouteInfo(delegate.getId());
	}
}
