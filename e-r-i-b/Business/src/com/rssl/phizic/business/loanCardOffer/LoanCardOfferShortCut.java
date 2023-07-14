package com.rssl.phizic.business.loanCardOffer;

import com.rssl.phizic.business.offers.LoanCardOffer;

/**
 * Тип предложения и ИД(если есть)
 * @author Jatsky
 * @ created 25.09.14
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardOfferShortCut
{
	String type;
	String id;

	public LoanCardOfferShortCut(LoanCardOffer loanCardOffer)
	{
		type = loanCardOffer.getOfferType().getValue();
		id = loanCardOffer.getOfferId().toString();
	}

	public LoanCardOfferShortCut(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public String getId()
	{
		return id;
	}
}
