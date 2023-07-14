package com.rssl.phizicgate.iqwave.types;

import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItemState;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 02.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class ScheduleItemImpl implements ScheduleItem
{
	private Calendar date;
	private Money amount;
	private ScheduleItemState state;

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public ScheduleItemState getState()
	{
		return state;
	}

	public void setState(ScheduleItemState state)
	{
		this.state = state;
	}
}
