package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class CancelationCallBackWithRouteInfo implements CancelationCallBack
{
	private CancelationCallBack delegate;
	private String routeInfo;

	public CancelationCallBackWithRouteInfo(CancelationCallBack delegate, String routeInfo)
	{
		this.delegate = delegate;
		this.routeInfo = routeInfo;
	}

	public String getId()
	{
		return IDHelper.storeRouteInfo(delegate.getId(), routeInfo);
	}
}
