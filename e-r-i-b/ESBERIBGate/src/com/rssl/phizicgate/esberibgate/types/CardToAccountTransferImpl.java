package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.CardToAccountTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardToAccountTransferImpl extends AbstractTransferImpl implements CardToAccountTransfer
{
	private String receiverAccount;
	private String chargeOffCard;
	private Calendar chargeOffCardExpireDate;
	private String authorizeCode;
	private String chargeOffCardDescription;
	private Calendar authorizeDate;
	private Currency chargeOffCurrency;
	private Currency destinationCurrency;

	public CardToAccountTransferImpl()
	{
	}

	public CardToAccountTransferImpl(LongOffer longOffer) throws GateException
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

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return chargeOffCardExpireDate;
	}

	public void setChargeOffCardExpireDate(Calendar chargeOffCardExpireDate)
	{
		this.chargeOffCardExpireDate = chargeOffCardExpireDate;
	}

	public String getAuthorizeCode()
	{
		return authorizeCode;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		this.authorizeCode = authorizeCode;
	}

	public Calendar getAuthorizeDate()
	{
		return authorizeDate;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
		this.authorizeDate = authorizeDate;
	}

	public String getChargeOffCardDescription()
	{
		return chargeOffCardDescription;
	}

	public void setChargeOffCardDescription(String chargeOffCardDescription)
	{
		this.chargeOffCardDescription = chargeOffCardDescription;
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
