package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.LoanTransfer;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizicgate.esberibgate.types.AbstractTransferImpl;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 23.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanTransferImpl extends AbstractTransferImpl implements LoanTransfer
{
	private String loanExternalId;
	private String accountNumber;
	private String chargeOffCard;
	private Currency chargeOffCurrency;
	private Calendar chargeOffCardExpireDate;
	private String authorizeCode;
	private String idSpacing;
	private String agreementNumber;
	private String chargeOffCardDescription;
	private Calendar spacingDate;
	private Calendar authorizeDate;

	public LoanTransferImpl()
	{
	}

	public LoanTransferImpl(LongOffer longOffer) throws GateException
	{
		super(longOffer);
	}

	public String getLoanExternalId()
	{
		return loanExternalId;
	}

	public void setLoanExternalId(String loanExternalId)
	{
		this.loanExternalId = loanExternalId;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
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

	public String getChargeOffCard()
	{
		return chargeOffCard;
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		this.chargeOffCard = chargeOffCard;
	}

	public String getIdSpacing()
	{
		return idSpacing;
	}

	public void setIdSpacing(String idSpacing)
	{
		this.idSpacing = idSpacing;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public Calendar getSpacingDate()
	{
		return spacingDate;
	}

	public void setSpacingDate(Calendar spacingDate)
	{
		this.spacingDate = spacingDate;
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
}
