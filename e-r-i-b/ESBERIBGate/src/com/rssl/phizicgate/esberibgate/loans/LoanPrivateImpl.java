package com.rssl.phizicgate.esberibgate.loans;

import com.rssl.phizic.gate.loans.LoanPrivate;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Jatsky
 * @ created 20.08.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanPrivateImpl implements LoanPrivate
{
	private BigDecimal recPaymentAmount;
	private Calendar closestPaymentDate;

	public BigDecimal getRecPaymentAmount()
	{
		return recPaymentAmount;
	}

	public void setRecPaymentAmount(BigDecimal recPaymentAmount)
	{
		this.recPaymentAmount = recPaymentAmount;
	}

	public Calendar getClosestPaymentDate()
	{
		return closestPaymentDate;
	}

	public void setClosestPaymentDate(Calendar closestPaymentDate)
	{
		this.closestPaymentDate = closestPaymentDate;
	}
}
