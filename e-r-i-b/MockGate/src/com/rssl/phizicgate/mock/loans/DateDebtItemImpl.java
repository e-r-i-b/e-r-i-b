package com.rssl.phizicgate.mock.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.loans.DateDebtItem;
import com.rssl.phizic.gate.loans.PenaltyDateDebtItemType;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 26.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class DateDebtItemImpl implements DateDebtItem
{
	private PenaltyDateDebtItemType code;
	private Long priority;
	private String accountCount;
	private Calendar annuityDate;
	private String name;
	private Money amount;

	public DateDebtItemImpl(PenaltyDateDebtItemType code, Long priority, String accountCount,
							Calendar annuityDate, String name, Money amount)
	{
		this.code = code;
		this.priority = priority;
		this.accountCount = accountCount;
		this.annuityDate = annuityDate;
		this.name = name;
		this.amount = amount;
	}

	public PenaltyDateDebtItemType getCode()
	{
		return code;
	}

	public void setCode(PenaltyDateDebtItemType code)
	{
		this.code = code;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public String getAccountCount()
	{
		return accountCount;
	}

	public void setAccountCount(String accountCount)
	{
		this.accountCount = accountCount;
	}

	public Calendar getAnnuityDate()
	{
		return annuityDate;
	}

	public void setAnnuityDate(Calendar annuityDate)
	{
		this.annuityDate = annuityDate;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}
}
