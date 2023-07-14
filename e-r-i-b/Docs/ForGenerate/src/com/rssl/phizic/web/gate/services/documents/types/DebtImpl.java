package com.rssl.phizic.web.gate.services.documents.types;

import java.util.Calendar;

/**
 * @author egorova
 * @ created 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class DebtImpl
{
	private Money amount;
	private Money fine;
	private Calendar lastPayDate;

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public void setFine(Money fine)
	{
		this.fine = fine;
	}

	public void setLastPayDate(Calendar lastPayDate)
	{
		this.lastPayDate = lastPayDate;
	}

	public Money getDebt()
	{
		return amount;
	}

	public Money getFine()
	{
		return fine;
	}

	public Calendar getLastPayDate()
	{
		return lastPayDate;
	}
}
