package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * @author osminin
 * @ created 23.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class AbstractRUSPaymentImpl extends AbstractTransferImpl implements AbstractRUSPayment
{
	private String receiverINN;
	private String receiverAccount;
	private ResidentBank receiverBank;
	private Currency destinationCurrency;

	public AbstractRUSPaymentImpl()
	{
	}

	public AbstractRUSPaymentImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public String getReceiverINN()
	{
		return receiverINN;
	}

	public void setReceiverINN(String receiverINN)
	{
		this.receiverINN = receiverINN;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public ResidentBank getReceiverBank()
	{
		return receiverBank;
	}

	public void setReceiverBank(ResidentBank receiverBank)
	{
		this.receiverBank = receiverBank;
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
