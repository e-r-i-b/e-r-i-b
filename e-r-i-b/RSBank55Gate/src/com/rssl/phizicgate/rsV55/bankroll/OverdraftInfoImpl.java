package com.rssl.phizicgate.rsV55.bankroll;

import com.rssl.phizic.gate.bankroll.OverdraftInfo;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author Danilov
 * @ created 26.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class OverdraftInfoImpl implements OverdraftInfo
{
	private Calendar openDate;
	private Money limit;
	private Money unsettledDebtSum;
	private Money unsettledPenalty;
	private Money currentOverdraftSum;
	private Money rate;
	private Money technicalOverdraftSum;
	private Money technicalPenalty;
	private Money totalDebtSum;
	private Calendar unsetltedDebtCreateDate;
	private Money minimalPayment;
	private Calendar minimalPaymentDate;
	private Money ownSum;

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public Money getLimit()
	{
		return limit;
	}

	public Money getUnsettledDebtSum()
	{
		return unsettledDebtSum;
	}

	public Money getUnsettledPenalty()
	{
		return unsettledPenalty;
	}

	public Money getCurrentOverdraftSum()
	{
		return currentOverdraftSum;
	}

	public Money getRate()
	{
		return rate;
	}

	public Money getTechnicalOverdraftSum()
	{
		return technicalOverdraftSum;
	}

	public Money getTechnicalPenalty()
	{
		return technicalPenalty;
	}

	public Money getTotalDebtSum()
	{
		return totalDebtSum;
	}

	public Calendar getUnsetltedDebtCreateDate()
	{
		return unsetltedDebtCreateDate;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public void setLimit(Money limit)
	{
		this.limit = limit;
	}

	public void setUnsettledDebtSum(Money unsettledDebtSum)
	{
		this.unsettledDebtSum = unsettledDebtSum;
	}

	public void setUnsettledPenalty(Money unsettledPenalty)
	{
		this.unsettledPenalty = unsettledPenalty;
	}

	public void setCurrentOverdraftSum(Money currentOverdraftSum)
	{
		this.currentOverdraftSum = currentOverdraftSum;
	}

	public void setRate(Money rate)
	{
		this.rate = rate;
	}

	public void setTechnicalOverdraftSum(Money technicalOverdraftSum)
	{
		this.technicalOverdraftSum = technicalOverdraftSum;
	}

	public void setTechnicalPenalty(Money technicalPenalty)
	{
		this.technicalPenalty = technicalPenalty;
	}

	public void setTotalDebtSum(Money totalDebtSum)
	{
		this.totalDebtSum = totalDebtSum;
	}

	public void setUnsetltedDebtCreateDate(Calendar unsetltedDebtCreateDate)
	{
		this.unsetltedDebtCreateDate = unsetltedDebtCreateDate;
	}

	public Money getMinimalPayment()
	{
		return minimalPayment;
	}

	public void setMinimalPayment(Money minimalPayment)
	{
		this.minimalPayment = minimalPayment;
	}

	public Calendar getMinimalPaymentDate()
	{
		return minimalPaymentDate;
	}

	public void setMinimalPaymentDate(Calendar minimalPaymentDate)
	{
		this.minimalPaymentDate = minimalPaymentDate;
	}

	public Money getOwnSum()
	{
		return ownSum;
	}

	public void setOwnSum(Money ownSum)
	{
		this.ownSum = ownSum;
	}
}
