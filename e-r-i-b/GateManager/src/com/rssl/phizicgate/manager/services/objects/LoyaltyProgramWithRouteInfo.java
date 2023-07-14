package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramWithRouteInfo implements LoyaltyProgram
{
	private LoyaltyProgram delegate;
	private String routeInfo;

	public LoyaltyProgramWithRouteInfo(LoyaltyProgram payment, String routeInfo)
	{
		this.routeInfo = routeInfo;
		this.delegate = payment;
	}

	/**
	 * @return внешний идентификатор
	 */
	public String getExternalId()
	{
		return IDHelper.storeRouteInfo(delegate.getExternalId(), routeInfo);
	}

	public BigDecimal getBalance()
	{
		return delegate.getBalance();
	}

	public String getPhone()
	{
		return delegate.getPhone();
	}

	public String getEmail()
	{
		return delegate.getEmail();
	}
}
