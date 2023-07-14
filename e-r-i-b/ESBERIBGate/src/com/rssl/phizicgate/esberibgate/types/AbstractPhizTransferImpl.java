package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;

/**
 * @author khudyakov
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class AbstractPhizTransferImpl extends AbstractRUSPaymentImpl implements AbstractPhizTransfer
{
	private String receiverSurName;
	private String receiverFirstName;
	private String receiverPatrName;
	private String receiverName;
	private Currency chargeOffCurrency;

	public AbstractPhizTransferImpl()
	{
	}

	public AbstractPhizTransferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public String getReceiverSurName()
	{
		return receiverSurName;
	}

	public void setReceiverSurName(String receiverSurName)
	{
		this.receiverSurName = receiverSurName;
	}

	public String getReceiverFirstName()
	{
		return receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName)
	{
		this.receiverFirstName = receiverFirstName;
	}

	public String getReceiverPatrName()
	{
		return receiverPatrName;
	}

	public void setReceiverPatrName(String receiverPatrName)
	{
		this.receiverPatrName = receiverPatrName;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return chargeOffCurrency;
	}

	public void setChargeOffCurrency(Currency chargeOffCurrency)
	{
		this.chargeOffCurrency = chargeOffCurrency;
	}
}
