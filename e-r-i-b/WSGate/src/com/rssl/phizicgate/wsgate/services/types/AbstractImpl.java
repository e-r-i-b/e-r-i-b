package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.util.List;
import java.math.BigDecimal;

/**
 * @author krenev
 * @ created 13.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class AbstractImpl
{
	private Calendar previousOperationDate;
	private List<Trustee> trusteesDocuments;
	private Calendar closedDate;
	private Money closedSum;
	private BigDecimal interestRate;
	private String additionalInformation;
	private Calendar fromDate;
	private Calendar toDate;
	private Money openingBalance;
	private Money closingBalance;
	private List<TransactionBase> transactions;
	private List<CardOperation> unsettledOperations;

	public Calendar getPreviousOperationDate()
	{
		return previousOperationDate;
	}

	public List<Trustee> getTrusteesDocuments()
	{
		return trusteesDocuments;
	}

	public Calendar getClosedDate()
	{
		return closedDate;
	}

	public Money getClosedSum()
	{
		return closedSum;
	}

	public BigDecimal getInterestRate()
	{
		return interestRate;
	}

	public String getAdditionalInformation()
	{
		return additionalInformation;
	}

	public Calendar getFromDate()
	{
		return fromDate;
	}

	public Calendar getToDate()
	{
		return toDate;
	}

	public Money getOpeningBalance()
	{
		return openingBalance;
	}

	public Money getClosingBalance()
	{
		return closingBalance;
	}

	public List<TransactionBase> getTransactions()
	{
		return transactions;
	}

	public List<CardOperation> getUnsettledOperations()
	{
		return unsettledOperations;
	}

	public void setPreviousOperationDate(Calendar previousOperationDate)
	{
		this.previousOperationDate = previousOperationDate;
	}

	public void setTrusteesDocuments(List<Trustee> trusteesDocuments)
	{
		this.trusteesDocuments = trusteesDocuments;
	}

	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	public void setClosedSum(Money closedSum)
	{
		this.closedSum = closedSum;
	}

	public void setInterestRate(BigDecimal interestRate)
	{
		this.interestRate = interestRate;
	}

	public void setAdditionalInformation(String additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	public void setOpeningBalance(Money openingBalance)
	{
		this.openingBalance = openingBalance;
	}

	public void setClosingBalance(Money closingBalance)
	{
		this.closingBalance = closingBalance;
	}

	public void setTransactions(List<TransactionBase> transactions)
	{
		this.transactions = transactions;
	}

	public void setUnsettledOperations(List<CardOperation> unsettledOperations)
	{
		this.unsettledOperations = unsettledOperations;
	}
}
