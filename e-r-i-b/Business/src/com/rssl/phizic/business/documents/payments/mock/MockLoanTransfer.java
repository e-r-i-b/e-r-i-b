package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.LoanTransfer;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 03.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockLoanTransfer extends MockAbstractTransfer implements LoanTransfer
{
	private String chargeOffCard = EMPTY_STRING;

	public String getLoanExternalId()
	{
		return null;
	}

	public String getAccountNumber()
	{
		return null;
	}

	public String getAgreementNumber()
	{
		return null;
	}

	public String getIdSpacing()
	{
		return null;
	}

	public Calendar getSpacingDate()
	{
		return null;
	}

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		return null;
	}

	public void setChargeOffCard(String chargeOffCard)
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
