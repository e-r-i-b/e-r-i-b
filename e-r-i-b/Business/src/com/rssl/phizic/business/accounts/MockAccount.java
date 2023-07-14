package com.rssl.phizic.business.accounts;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 26.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class MockAccount implements Account, MockObject
{
	private static String EMPTY_STRING=""; 

	String id = EMPTY_STRING;
	String number = EMPTY_STRING;
	Office office = null;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public AccountState getAccountState()
	{
		return null;
	}
	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
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
		return false;
	}

	public Money getMinimumBalance()
	{
		return null;
	}

	public Boolean getPassbook()
	{
		return true;
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
