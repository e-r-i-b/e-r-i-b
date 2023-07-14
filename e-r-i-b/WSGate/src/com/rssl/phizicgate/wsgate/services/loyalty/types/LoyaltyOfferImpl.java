package com.rssl.phizicgate.wsgate.services.loyalty.types;

import com.rssl.phizic.gate.loyalty.LoyaltyOffer;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyOfferImpl implements LoyaltyOffer
{
	private String description;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
