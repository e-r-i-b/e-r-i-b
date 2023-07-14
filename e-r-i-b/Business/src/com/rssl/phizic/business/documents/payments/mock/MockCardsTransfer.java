package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;
import com.rssl.phizic.gate.payments.ReceiverCardType;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockCardsTransfer extends MockAbstractTransfer implements InternalCardsTransfer
{
	private String receiverCard = EMPTY_STRING;
	private String chargeOffCard = EMPTY_STRING;

	public String getReceiverCard()
	{
		return receiverCard;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return null;
	}

	public void setReceiverCard(String receiverCard)
	{
		this.receiverCard = receiverCard;
	}

	public Calendar getReceiverCardExpireDate()
	{
		return null;
	}

	public ReceiverCardType getReceiverCardType()
	{
		return null;
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
