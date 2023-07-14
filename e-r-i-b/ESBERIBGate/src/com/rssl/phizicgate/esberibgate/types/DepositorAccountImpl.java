package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.depo.DepositorAccount;

/**
 * @author lukina
 * @ created 19.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepositorAccountImpl implements DepositorAccount
{
	private String  accountNumber;
	private String 	cardType;
	private String 	cardId;
	private String  bankName;
	private String  BIC;
	private String  corAccount;
	private String  corBankName;
	private String  destination;
	private Currency currency;

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getCardType()
	{
		return cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}

	public String getCardId()
	{
		return cardId;
	}

	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public String getCorAccount()
	{
		return corAccount;
	}

	public void setCorAccount(String corAccount)
	{
		this.corAccount = corAccount;
	}

	public String getCorBankName()
	{
		return corBankName;
	}

	public void setCorBankName(String corBankName)
	{
		this.corBankName = corBankName;
	}

	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}
}
