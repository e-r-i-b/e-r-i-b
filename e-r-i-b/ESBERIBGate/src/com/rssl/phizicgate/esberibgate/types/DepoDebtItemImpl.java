package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.depo.DepoDebtItem;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 03.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoDebtItemImpl implements DepoDebtItem
{
	private String id;
	private String recNumber;
	private Money amount;
	private Money amountNDS;
	private Calendar recDate;
	private Calendar startDate;
	private Calendar endDate;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRecNumber()
	{
		return recNumber;
	}

	public void setRecNumber(String recNumber)
	{
		this.recNumber = recNumber;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Money getAmountNDS()
	{
		return amountNDS;
	}

	public void setAmountNDS(Money amountNDS)
	{
		this.amountNDS = amountNDS;
	}

	public Calendar getRecDate()
	{
		return recDate;
	}

	public void setRecDate(Calendar recDate)
	{
		this.recDate = recDate;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}
}
