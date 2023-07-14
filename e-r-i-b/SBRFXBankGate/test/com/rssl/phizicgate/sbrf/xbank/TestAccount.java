package com.rssl.phizicgate.sbrf.xbank;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author Gololobov
 * @ created 16.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class TestAccount implements Account
{
	private String number;
	public String getId()
	{
		return null;
	}

	public String getDescription()
	{
		return null;
	}

	public String getType()
	{
		return null;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Currency getCurrency()
	{
		return null;
	}

	public Calendar getOpenDate()
	{
		return null;
	}

	public Boolean getCreditAllowed()
	{
		return null;
	}

	public Boolean getDebitAllowed()
	{
		return null;
	}

	public BigDecimal getInterestRate()
	{
		return null;
	}

	public Money getBalance()
	{
		return null;
	}

	public Money getMaxSumWrite()
	{
		return null;
	}

	public Money getMinimumBalance()
	{
		return null;
	}

	public AccountState getAccountState()
	{
		return null;
	}

	public Office getOffice()
	{
		return null;
	}

	public Long getKind()
	{
		return null;
	}

	public Long getSubKind()
	{
		return null;
	}

	public Boolean getDemand()
	{
		return null;
	}

	public Boolean getPassbook()
	{
		return null;
	}

	public String getAgreementNumber()
	{
		return null;
	}

	public Calendar getCloseDate()
	{
		return null;
	}

	public DateSpan getPeriod()
	{
		return null;
	}

	public Boolean getCreditCrossAgencyAllowed()
	{
		return null;
	}

	public Boolean getDebitCrossAgencyAllowed()
	{
		return null;
	}

	public Boolean getProlongationAllowed()
	{
		return null;
	}

	public String getInterestTransferAccount()
	{
		return null;
	}

	public String getInterestTransferCard()
	{
		return null;
	}

	public BigDecimal getClearBalance()
	{
		return null;
	}

	public BigDecimal getMaxBalance()
	{
		return null;
	}

	public Calendar getLastTransactionDate()
	{
		return null;
	}

	public Client getAccountClient()
	{
		return null;
	}

	public Calendar getProlongationDate()
	{
		return null;
	}

    public Long getClientKind()
    {
        return null;
    }
}
