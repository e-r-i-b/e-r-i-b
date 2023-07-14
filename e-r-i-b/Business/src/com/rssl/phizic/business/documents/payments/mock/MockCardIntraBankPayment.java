package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockCardIntraBankPayment extends MockAbstractRUSPayment implements CardIntraBankPayment
{
	private String chargeOffCard = EMPTY_STRING;

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return null;
	}

	public void setChergeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return null;
	}

	public String getChargeOffCardDescription()
	{
		return null;  
	}

	public void setAuthorizeCode(String authorizeCode)
	{

	}

	public String getAuthorizeCode()
	{
		return null;
	}

	public Calendar getAuthorizeDate()
	{
		return null;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
	}
}
