package com.rssl.phizicgate.mock.depo;

import com.rssl.phizic.gate.depo.DepoDebtItem;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 20.08.2010
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

	public DepoDebtItemImpl(String id, String recNumber, Money amount, Money amountNDS, Calendar recDate, Calendar startDate, Calendar endDate)
	{
		this.id = id;
		this.recNumber = recNumber;
		this.amount = amount;
		this.amountNDS = amountNDS;
		this.recDate = recDate;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getId()
	{
		return id;
	}

	public String getRecNumber()
	{
		return recNumber;
	}

	public Money getAmount()
	{
		return amount;
	}

	public Money getAmountNDS()
	{
		return amountNDS;
	}

	public Calendar getRecDate()
	{
		return recDate;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}
}
