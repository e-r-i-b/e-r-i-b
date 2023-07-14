package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositState;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Krenev
 * @ created 24.02.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class DepositBase implements Deposit
{
	Deposit delegate;

	public DepositBase(Deposit delegate)
	{
		this.delegate = delegate;
	}

	public String getDescription()
	{
		return delegate.getDescription();
	}

	public Money getAmount()
	{
		return delegate.getAmount();
	}

	public Long getDuration()
	{
		return delegate.getDuration();
	}

	public BigDecimal getInterestRate()
	{
		return delegate.getInterestRate();
	}

	public Calendar getOpenDate()
	{
		return delegate.getOpenDate();
	}

	public Calendar getEndDate()
	{
		return delegate.getEndDate();
	}

	public Calendar getCloseDate()
	{
		return delegate.getCloseDate();
	}

	public DepositState getState()
	{
		return delegate.getState();
	}
}
