package com.rssl.phizicgate.iqwave.loyalty.mock;

import com.rssl.phizic.gate.loyalty.LoyaltyOffer;

/**
 * @author gladishev
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyOfferImpl implements LoyaltyOffer
{
	private String description;

	public LoyaltyOfferImpl(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
