package com.rssl.phizic.web.common.socialApi.cards;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * @ author: Gololobov
 * @ created: 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class GetCardIssuerByNumberMobileForm extends EditFormBase
{
	private String cardNumber;
	private boolean sbrfCardIssuer;

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public boolean isSbrfCardIssuer()
	{
		return sbrfCardIssuer;
	}

	public void setSbrfCardIssuer(boolean sbrfCardIssuer)
	{
		this.sbrfCardIssuer = sbrfCardIssuer;
	}
}
