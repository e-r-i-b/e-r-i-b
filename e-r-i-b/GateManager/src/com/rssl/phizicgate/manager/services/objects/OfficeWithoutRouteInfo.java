package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class OfficeWithoutRouteInfo extends OfficeBase implements RouteInfoReturner
{
	public OfficeWithoutRouteInfo(Office delegate)
	{
		super(delegate);
	}

	public Comparable getSynchKey()
	{
		return IDHelper.restoreOriginalId(delegate.getSynchKey().toString());
	}

	public String getRouteInfo()
	{
		return IDHelper.restoreRouteInfo(delegate.getSynchKey().toString());
	}

	public String getId()
	{
		return IDHelper.restoreOriginalId(delegate.getSynchKey().toString());
	}

}
