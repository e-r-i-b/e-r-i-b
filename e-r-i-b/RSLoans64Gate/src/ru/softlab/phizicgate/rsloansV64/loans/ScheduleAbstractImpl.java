package ru.softlab.phizicgate.rsloansV64.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.gate.loans.ScheduleItem;

import java.util.List;

/**
 * @author mihaylov
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ScheduleAbstractImpl implements ScheduleAbstract
{

	private List<ScheduleItem> schedules;
	private Money doneAmount;
	private Money remainAmount;
	private Money penaltyAmount;
	private Long paymentCount;

	public List<ScheduleItem> getSchedules()
	{
		return schedules;
	}

	public void setSchedules(List<ScheduleItem> schedules)
	{
		this.schedules = schedules;
	}

	public Money getDoneAmount()
	{
		return doneAmount;
	}

	public void setDoneAmount(Money doneAmount)
	{
		this.doneAmount = doneAmount;
	}

	public Money getRemainAmount()
	{
		return remainAmount;
	}

	public void setRemainAmount(Money remainAmount)
	{
		this.remainAmount = remainAmount;
	}

	public Money getPenaltyAmount()
	{
		return penaltyAmount;
	}

	public void setPenaltyAmount(Money penaltyAmount)
	{
		this.penaltyAmount = penaltyAmount;
	}

	public Long getPaymentCount()
	{
		return paymentCount;
	}

	public void setPaymentCount(Long paymentCount)
	{
		this.paymentCount = paymentCount;
	}

	public boolean getIsAvailable()
	{
		return true;
	}
}
