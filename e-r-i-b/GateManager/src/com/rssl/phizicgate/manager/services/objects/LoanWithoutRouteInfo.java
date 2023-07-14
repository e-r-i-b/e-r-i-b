package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author gladishev
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanWithoutRouteInfo extends LoanBase implements RouteInfoReturner
{
	public LoanWithoutRouteInfo(Loan delegate)
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
}
