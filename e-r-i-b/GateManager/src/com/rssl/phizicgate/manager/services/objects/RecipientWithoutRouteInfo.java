package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class RecipientWithoutRouteInfo extends RecipientBase implements RouteInfoReturner
{
	public RecipientWithoutRouteInfo(Recipient delegate)
	{
		super(delegate);
	}

	public Comparable getSynchKey()
	{
		return IDHelper.restoreOriginalId(IDHelper.restoreOriginalIdWithAdditionalInfo(delegate.getSynchKey().toString()));
	}

	public String getRouteInfo()
	{
		return IDHelper.restoreRouteInfo(delegate.getSynchKey().toString());
	}

	public String getId()
	{
		return (String) getSynchKey();
	}
}
