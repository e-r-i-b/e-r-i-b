package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.ima.IMAccountAbstract;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountAbstractImpl implements IMAccountAbstract
{
	private Money                 openingBalanceInRub;
	private Money                 closingBalanceInRub;
	private Calendar              previousOperationDate;
	private BigDecimal            rate;
	private Calendar              fromDate;
	private Calendar              toDate;
	private Money                 openingBalance;
	private Money                 closingBalance;
	private List<TransactionBase> transactions;

	public IMAccountAbstractImpl(Money openingBalanceInRub,
	                             Money closingBalanceInRub,
	                             Calendar previousOperationDate,
	                             BigDecimal rate, 
	                             Calendar fromDate,
	                             Calendar toDate,
	                             Money openingBalance,
	                             Money closingBalance,
	                             List<TransactionBase> transactions)
	{
		this.openingBalanceInRub = openingBalanceInRub;
		this.closingBalanceInRub = closingBalanceInRub;
		this.previousOperationDate = previousOperationDate;
		this.rate = rate;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.openingBalance = openingBalance;
		this.closingBalance = closingBalance;
		this.transactions = transactions;
	}

	public Money getOpeningBalanceInRub()
	{
		return openingBalanceInRub;
	}

	public Money getClosingBalanceInRub()
	{
		return closingBalanceInRub;
	}

	public Calendar getPreviousOperationDate()
	{
		return previousOperationDate;
	}

	public BigDecimal getRate()
	{
		return rate;
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
}