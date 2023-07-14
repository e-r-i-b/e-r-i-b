package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;

/**
 * @author osminin
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientAccountsTransferImpl extends AbstractTransferImpl implements ClientAccountsTransfer
{
	private String receiverAccount;
	private String chargeOffAccount;
	private Currency chargeOffCurrency;
	private Currency destinationCurrency;

	public ClientAccountsTransferImpl()
	{
	}

	public ClientAccountsTransferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return chargeOffCurrency;
	}

	public void setChargeOffCurrency(Currency chargeOffCurrency)
	{
		this.chargeOffCurrency = chargeOffCurrency;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return destinationCurrency;
	}

	public void setDestinationCurrency(Currency destinationCurrency)
	{
		this.destinationCurrency = destinationCurrency;
	}
}
