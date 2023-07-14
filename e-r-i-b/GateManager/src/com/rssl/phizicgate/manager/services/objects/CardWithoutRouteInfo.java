package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardWithoutRouteInfo extends CardBase implements RouteInfoReturner
{
	public CardWithoutRouteInfo(Card delegate)
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

	public String getPrimaryAccountExternalId()
	{
		return IDHelper.restoreRouteInfo(delegate.getPrimaryAccountExternalId());
	}
}
