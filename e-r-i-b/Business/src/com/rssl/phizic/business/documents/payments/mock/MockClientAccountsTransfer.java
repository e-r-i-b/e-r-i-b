package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;

/**
 * @author osminin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockClientAccountsTransfer extends MockAbstractTransfer implements ClientAccountsTransfer
{
	private String receiverAccount = EMPTY_STRING;
	private String chargeOffAccount = EMPTY_STRING;

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
