package com.rssl.phizicgate.esberibgate.ima;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.ima.IMAccountAbstract;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author Balovtsev
 * @created 15.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountAbstractImpl implements IMAccountAbstract
{
	private Money openingBalance;
	private Money closingBalance;
	private Money openingBalanceInRub;
	private Money closingBalanceInRub;

	private Calendar fromDate;
	private Calendar toDate;
	private Calendar previousOperationDate;

	private BigDecimal rate;

	private List<TransactionBase> transactions;

	public Money getOpeningBalanceInRub()
	{
		return openingBalanceInRub;
	}

	public void setOpeningBalanceInRub(Money openingBalanceInRub)
	{
		this.openingBalanceInRub = openingBalanceInRub;
	}

	public Money getClosingBalanceInRub()
	{
		return closingBalanceInRub;
	}

	public void setClosingBalanceInRub(Money closingBalanceInRub)
	{
		this.closingBalanceInRub = closingBalanceInRub;
	}

	public Calendar getPreviousOperationDate()
	{
		return previousOperationDate;
	}

	public void setPreviousOperationDate(Calendar previousOperationDate)
	{
		this.previousOperationDate = previousOperationDate;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
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
