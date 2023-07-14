package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem;
import com.rssl.phizic.gate.longoffer.autopayment.ScheduleItemState;

import java.util.Calendar;

/**
 * @author niculichev
 * @ created 05.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class ScheduleItemImpl implements ScheduleItem
{
	private Money amount;
	private Calendar date;
	private ScheduleItemState state;
	/**
	 * @return Дата исполнения
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @return Сумма платежа
	 */
	public Money getAmount()
	{
		return amount;
	}

	/**
	 * @return Статус платежа
	 */
	public ScheduleItemState getState()
	{
		return state;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public void setState(ScheduleItemState state)
	{
		this.state = state;
	}

	public void setState(String state)
	{
		if (state == null || state.trim().length() == 0)
		{
			return;
		}
		this.state = Enum.valueOf(ScheduleItemState.class, state);
	}
}
