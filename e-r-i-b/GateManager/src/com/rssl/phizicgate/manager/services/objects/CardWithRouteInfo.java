package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardWithRouteInfo extends CardBase
{
	private String routeInfo;

	public CardWithRouteInfo(Card delegate, String routeInfo)
	{
		super(delegate);
		this.routeInfo = routeInfo;
	}

	public String getId()
	{
		return IDHelper.storeRouteInfo(delegate.getId(), routeInfo);
	}

	public String getPrimaryAccountExternalId()
	{
		return IDHelper.storeRouteInfo(delegate.getPrimaryAccountExternalId(), routeInfo);
	}
}
