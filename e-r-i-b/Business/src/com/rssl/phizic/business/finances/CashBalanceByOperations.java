package com.rssl.phizic.business.finances;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * ќпераци€ дл€ построени€ графиков за период на странице "ќперации" (Ќаличные и безналичные)
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CashBalanceByOperations extends BalanceByOperations
{
	BigDecimal cashIncome; // доход наличными
	BigDecimal cashlessIncome; // доход безналичными
	BigDecimal cashOutcome; // расход наличными
	BigDecimal cashlessOutcome; // расход безналичными

	public CashBalanceByOperations(Calendar date, BigDecimal cashIncome, BigDecimal cashlessIncome, BigDecimal cashOutcome, BigDecimal cashlessOutcome)
	{
		this.date = date;
		this.cashIncome = cashIncome;
		this.cashlessIncome = cashlessIncome;
		this.cashOutcome = cashOutcome;
		this.cashlessOutcome = cashlessOutcome;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public BigDecimal getCashIncome()
	{
		return cashIncome;
	}

	public void setCashIncome(BigDecimal cashIncome)
	{
		this.cashIncome = cashIncome;
	}

	public BigDecimal getCashlessIncome()
	{
		return cashlessIncome;
	}

	public void setCashlessIncome(BigDecimal cashlessIncome)
	{
		this.cashlessIncome = cashlessIncome;
	}

	public BigDecimal getCashOutcome()
	{
		return cashOutcome;
	}

	public void setCashOutcome(BigDecimal cashOutcome)
	{
		this.cashOutcome = cashOutcome;
	}

	public BigDecimal getCashlessOutcome()
	{
		return cashlessOutcome;
	}

	public void setCashlessOutcome(BigDecimal cashlessOutcome)
	{
		this.cashlessOutcome = cashlessOutcome;
	}
}
