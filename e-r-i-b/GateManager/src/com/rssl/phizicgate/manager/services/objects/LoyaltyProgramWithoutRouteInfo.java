package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 17.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramWithoutRouteInfo implements LoyaltyProgram, RouteInfoReturner
{
	private LoyaltyProgram delegate;

	public LoyaltyProgramWithoutRouteInfo(LoyaltyProgram payment)
	{
		this.delegate = payment;
	}

	/**
	 * @return внешний идентификатор
	 */
	public String getExternalId()
	{
		return IDHelper.restoreOriginalId(delegate.getExternalId());
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

	public String getRouteInfo()
	{
		return IDHelper.restoreRouteInfo(delegate.getExternalId());
	}

	public String getId()
	{
		return getExternalId();
	}
}