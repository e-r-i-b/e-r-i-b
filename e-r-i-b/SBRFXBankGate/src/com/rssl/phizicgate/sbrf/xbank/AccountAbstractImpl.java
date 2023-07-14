package com.rssl.phizicgate.sbrf.xbank;

import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.DepositAbstract;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.bankroll.Trustee;
import com.rssl.phizic.common.types.Money;

import java.util.List;
import java.util.Calendar;

/**
 * @author Gololobov
 * @ created 18.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountAbstractImpl implements AccountAbstract, DepositAbstract
{
	private List<TransactionBase> transactions;
	private Money closingBalance;
	private Calendar fromDate;
	private Money openingBalance;
	private Calendar toDate;
    private Calendar previousOperationDate;
	private List<Trustee> trusteesDocuments;
	private Calendar closedDate;
	private Money closedSum;
	private String additionalInformation;

	public List<TransactionBase> getTransactions()
	{
		return transactions;
	}

	public void setTransactions(List<TransactionBase> transactions)
	{
		this.transactions = transactions;
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

	public List<Trustee> getTrusteesDocuments()
	{
		return trusteesDocuments;
	}

	public void setTrusteesDocuments(List<Trustee> trusteesDocuments)
	{
		this.trusteesDocuments = trusteesDocuments;
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

	public String getAdditionalInformation()
	{
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}
}
