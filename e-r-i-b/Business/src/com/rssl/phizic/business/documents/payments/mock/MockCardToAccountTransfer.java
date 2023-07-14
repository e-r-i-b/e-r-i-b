package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockCardToAccountTransfer extends MockAbstractTransfer implements CardToAccountTransfer
{
	private String receiverAccount = EMPTY_STRING;
	private String chargeOffCard = EMPTY_STRING;

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return null;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return null;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return null;
	}

	public String getChargeOffCardDescription()
	{
		return null;
	}

	public void setAuthorizeCode(String authorizeCode)
	{

	}

	public String getAuthorizeCode()
	{
		return null;
	}

	public Calendar getAuthorizeDate()
	{
		return null;
	}
	
	public void setAuthorizeDate(Calendar authorizeDate)
	{
	}
}
