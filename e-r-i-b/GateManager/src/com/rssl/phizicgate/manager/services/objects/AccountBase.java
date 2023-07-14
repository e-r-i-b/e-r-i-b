package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class AccountBase implements Account
{
	Account delegate;

	public AccountBase(Account delegate)
	{
		this.delegate = delegate;
	}

	public String getDescription()
	{
		return delegate.getDescription();
	}

	public String getType()
	{
		return delegate.getType();
	}

	public String getNumber()
	{
		return delegate.getNumber();
	}

	public Currency getCurrency()
	{
		return delegate.getCurrency();
	}

	public Calendar getOpenDate()
	{
		return delegate.getOpenDate();
	}

	public Boolean getCreditAllowed()
	{
		return delegate.getCreditAllowed();
	}

	public Boolean getDebitAllowed()
	{
		return delegate.getDebitAllowed();
	}

	public BigDecimal getInterestRate()
	{
		return delegate.getInterestRate();
	}

	public Money getBalance()
	{
		return delegate.getBalance();
	}

	public Money getMaxSumWrite()
	{
		return delegate.getMaxSumWrite();
	}

	public AccountState getAccountState()
	{
		return delegate.getAccountState();
	}

	public Office getOffice()
	{
		return delegate.getOffice();
	}

	public Long getKind()
	{
		return delegate.getKind();
	}

	public Long getSubKind()
	{
		return delegate.getSubKind();
	}

	public Money getMinimumBalance()
	{
		return delegate.getMinimumBalance();
	}

	public Boolean getDemand()
	{
		return delegate.getDemand();
	}

	public Boolean getPassbook()
	{
		return delegate.getPassbook();
	}

	public String getAgreementNumber()
	{
		return delegate.getAgreementNumber();
	}

	public Calendar getCloseDate()
	{
		return delegate.getCloseDate();
	}

	public DateSpan getPeriod()
	{
		return delegate.getPeriod();
	}

	public Boolean getCreditCrossAgencyAllowed()
	{
		return delegate.getCreditCrossAgencyAllowed();
	}

	public Boolean getDebitCrossAgencyAllowed()
	{
		return delegate.getDebitCrossAgencyAllowed();
	}

	public Boolean getProlongationAllowed()
	{
		return delegate.getProlongationAllowed();
	}

	public String getInterestTransferAccount()
	{
		return delegate.getInterestTransferAccount();
	}

	public String getInterestTransferCard()
	{
		return delegate.getInterestTransferCard();
	}

	public BigDecimal getClearBalance()
	{
		return delegate.getClearBalance();
	}

	public BigDecimal getMaxBalance()
	{
		return delegate.getMaxBalance();
	}

	public Calendar getLastTransactionDate()
	{
		return delegate.getLastTransactionDate();
	}

	public Client getAccountClient()
	{
		return delegate.getAccountClient();
	}

	public Calendar getProlongationDate()
	{
		return delegate.getProlongationDate();
	}

	public Long getClientKind()
	{
		return delegate.getClientKind();
	}
}
