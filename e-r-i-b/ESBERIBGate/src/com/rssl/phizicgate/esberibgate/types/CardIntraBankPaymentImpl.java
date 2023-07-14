package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;
import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardIntraBankPaymentImpl extends AbstractPhizTransferImpl implements CardIntraBankPayment
{
	private String chargeOffCard;
	private Calendar chargeOffCardExpireDate;
	private String authorizeCode;
	private String chargeOffCardDescription;
	private Calendar authorizeDate;

	public CardIntraBankPaymentImpl()
	{
	}

	public CardIntraBankPaymentImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
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
}
