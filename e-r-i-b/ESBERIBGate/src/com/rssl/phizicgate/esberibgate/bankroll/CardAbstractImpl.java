package com.rssl.phizicgate.esberibgate.bankroll;

import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.bankroll.CardOperation;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.common.types.Money;

import java.util.List;
import java.util.Calendar;

/**
 * @author Pakhomova
 * @ created 15.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardAbstractImpl implements CardAbstract
{
    private Calendar                fromDate;
    private Calendar                toDate;
    private Money                   openingBalance;
    private Money                   closingBalance;
    private List<TransactionBase>   transactions;
	private List<CardOperation>     unsettledOperations;

	public List<CardOperation> getUnsettledOperations()
	{
		return unsettledOperations;
	}

	public void setUnsettledOperations(List<CardOperation> unsettledOperations)
	{
		this.unsettledOperations = unsettledOperations;
	}

	public Calendar getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public Calendar getToDate()
	{
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	public Money getOpeningBalance()
	{
		return openingBalance;
	}

	public void setOpeningBalance(Money openingBalance)
	{
		this.openingBalance = openingBalance;
	}

	public Money getClosingBalance()
	{
		return closingBalance;
	}

	public void setClosingBalance(Money closingBalance)
	{
		this.closingBalance = closingBalance;
	}

	public List<TransactionBase> getTransactions()
	{
		return transactions;
	}

	public void setTransactions(List<TransactionBase> transactions)
	{
		this.transactions = transactions;
	}
}