package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockAccountToCardTransfer extends MockAbstractTransfer implements AccountToCardTransfer
{
	private String receiverCard = EMPTY_STRING;
	private String chargeOffAccount = EMPTY_STRING;

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

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return null;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}
}
