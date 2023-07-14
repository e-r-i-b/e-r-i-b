package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.common.types.Money;

import java.util.List;
import java.util.Calendar;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 * @author Omeliyanchuk
 * @ created 28.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class AccountAbstractImpl implements AccountAbstract, DepositAbstract
{
	private List<TransactionBase> accountTransactions;
	private Money closingBalance;
	private Calendar fromDate;
	private Money openingBalance;
	private Calendar toDate;
    private Calendar previousOperationDate;
	private List<Trustee> trustees;
	private Calendar closedDate;
	private Money closedSum;
	private String additionalInformation;

	public List<TransactionBase> getTransactions()
	{
		return accountTransactions;
	}

	public void setTransactions(List<TransactionBase> accountTransactions)
	{
		this.accountTransactions = accountTransactions;
	}

	public Money getClosingBalance()
	{
		return closingBalance;
	}

	public void setClosingBalance(Money closingBalance)
	{
		this.closingBalance = closingBalance;
	}

	public Calendar getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public Money getOpeningBalance()
	{
		return openingBalance;
	}

	public void setOpeningBalance(Money openingBalance)
	{
		this.openingBalance = openingBalance;
	}

	public Calendar getToDate()
	{
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	public Calendar getPreviousOperationDate()
	{
		return previousOperationDate;
	}

	public void setPreviousOperationDate(Calendar previousOperationDate)
	{
		this.previousOperationDate = previousOperationDate;
	}

	public String getAdditionalInformation()
	{
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}

	public List<Trustee> getTrusteesDocuments()
	{
		return trustees;
	}

	public void setTrusteesDocuments(List<Trustee> trustees)
	{
		this.trustees = trustees;
	}

	public Calendar getClosedDate()
	{
		return closedDate;
	}

	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	public Money getClosedSum()
	{
		return closedSum;
	}

	public void setClosedSum(Money closedSum)
	{
		this.closedSum = closedSum;
	}

	public List<TransactionBase> getTransaction()
	{
		return null;
	}
}

