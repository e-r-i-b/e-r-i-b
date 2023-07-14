package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.longoffer.ScheduleItem;
import com.rssl.phizic.gate.longoffer.SheduleItemState;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 07.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferScheduleItemImpl implements ScheduleItem
{
	private Calendar date;
	private Money amount;
	private SheduleItemState state;

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

	public SheduleItemState getState()
	{
		return state;
	}

	public void setState(SheduleItemState state)
	{
		this.state = state;
	}
}
