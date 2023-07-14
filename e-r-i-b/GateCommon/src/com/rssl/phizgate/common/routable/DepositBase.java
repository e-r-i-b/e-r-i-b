package com.rssl.phizgate.common.routable;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositState;
import com.rssl.phizic.gate.Routable;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizicgate.manager.services.IDHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author hudyakov
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class DepositBase implements Deposit, Routable
{
	protected String id;
	protected String description;
	protected Money amount;
	protected Long duration;
	protected BigDecimal interestRate;
	protected Calendar openDate;
	protected Calendar endDate;
	protected Calendar closeDate;
	protected DepositState state;

	public void storeRouteInfo(String info)
	{
		setId(IDHelper.storeRouteInfo(getId(), info));
	}

	public String restoreRouteInfo()
	{
		return IDHelper.restoreRouteInfo(getId());
	}

	public String removeRouteInfo()
	{
		String info = IDHelper.restoreRouteInfo(id);
		setId(IDHelper.restoreOriginalId(id));

		return info;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Long getDuration()
	{
		return duration;
	}

	public void setDuration(Long duration)
	{
		this.duration = duration;
	}

	public BigDecimal getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate)
	{
		this.interestRate = interestRate;
	}

	public Calendar getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(Calendar openDate)
	{
		this.openDate = openDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Calendar getCloseDate()
	{
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	public DepositState getState()
	{
		return state;
	}

	public void setState(DepositState state)
	{
		this.state = state;
	}

	public String toString()
    {
        return id;
    }
}
