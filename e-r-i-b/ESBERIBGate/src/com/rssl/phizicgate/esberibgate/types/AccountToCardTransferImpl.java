package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 23.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountToCardTransferImpl extends AbstractTransferImpl implements AccountToCardTransfer
{
	private String receiverCard;
	private Calendar receiverCardExpireDate;
	private String chargeOffAccount;
	private Currency chargeOffCurrency;
	private Currency destinationCurrency;

	public AccountToCardTransferImpl()
	{
	}

	public AccountToCardTransferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public String getReceiverCard()
	{
		return receiverCard;
	}

	public void setReceiverCard(String receiverCard)
	{
		this.receiverCard = receiverCard;
	}

	public Calendar getReceiverCardExpireDate()
	{
		return receiverCardExpireDate;
	}

	public void setReceiverCardExpireDate(Calendar receiverCardExpireDate)
	{
		this.receiverCardExpireDate = receiverCardExpireDate;
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
