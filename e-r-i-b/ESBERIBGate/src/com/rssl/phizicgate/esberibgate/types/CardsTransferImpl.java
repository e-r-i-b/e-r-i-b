package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;
import com.rssl.phizic.gate.payments.ReceiverCardType;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardsTransferImpl extends AbstractTransferImpl implements InternalCardsTransfer
{
	private String receiverCard;
	private Currency destinationCurrency;
	private Calendar receiverCardExpireDate;
	private String chargeOffCard;
	private Currency chargeOffCurrency;
	private Calendar chargeOffCardExpireDate;
	private String authorizeCode;
	private String chargeOffCardDescription;
	private Calendar authorizeDate;
	private ReceiverCardType receiverCardType;

	public CardsTransferImpl()
	{
	}

	public CardsTransferImpl(LongOffer longOffer) throws GateException
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

	public Calendar getReceiverCardExpireDate()
	{
		return receiverCardExpireDate;
	}

	public void setReceiverCardExpireDate(Calendar receiverCardExpireDate)
	{
		this.receiverCardExpireDate = receiverCardExpireDate;
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

	public ReceiverCardType getReceiverCardType()
	{
		return receiverCardType;
	}

	public void setReceiverCardType(ReceiverCardType receiverCardType)
	{
		this.receiverCardType = receiverCardType;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		return destinationCurrency;
	}

	public void setDestinationCurrency(Currency destinationCurrency)
	{
		this.destinationCurrency = destinationCurrency;
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
