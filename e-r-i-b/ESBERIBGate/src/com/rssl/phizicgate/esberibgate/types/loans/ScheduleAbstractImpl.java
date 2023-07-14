package com.rssl.phizicgate.esberibgate.types.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.loans.LoanYearPayment;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.gate.loans.ScheduleAbstract;

import java.util.List;

/**
 * @author gladishev
 * @ created 14.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class ScheduleAbstractImpl implements ScheduleAbstract
{
	private Money doneAmount;
	private Money remainAmount;
	private Money penaltyAmount;
	private Long  paymentCount;
	private List<ScheduleItem> schedules;
	private boolean isAvailable;
    private List<LoanYearPayment> yearPayments;

	public ScheduleAbstractImpl()
	{
	}

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
		return isAvailable;
	}

	public void setIsAvailable(boolean isAvailable)
	{
		this.isAvailable = isAvailable;
	}

    public List<LoanYearPayment> getYearPayments()
    {
        return yearPayments;
    }

    public void setYearPayments(List<LoanYearPayment> yearPayments)
    {
        this.yearPayments = yearPayments;
	}
}