package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.CardJurTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardJurTransferImpl extends AbstractJurTransferImpl implements CardJurTransfer
{
	private String chargeOffCard;
	private Calendar chargeOffCardExpireDate;
	private String authorizeCode;
	private String chargeOffCardDescription;
	private Calendar authorizeDate;

	public CardJurTransferImpl()
	{
	}

	public CardJurTransferImpl(LongOffer longOffer) throws GateException
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

	public Calendar getAuthorizeDate()
	{
		return authorizeDate;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		this.authorizeCode = authorizeCode;
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
