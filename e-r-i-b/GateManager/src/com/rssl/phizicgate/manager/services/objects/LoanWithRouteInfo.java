package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author gladishev
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanWithRouteInfo extends LoanBase
{
	private String routeInfo;

	public LoanWithRouteInfo(Loan delegate, String routeInfo)
	{
		super(delegate);
		this.routeInfo = routeInfo;
	}

	public String getId()
	{
		return IDHelper.storeRouteInfo(delegate.getId(), routeInfo);
	}
}
