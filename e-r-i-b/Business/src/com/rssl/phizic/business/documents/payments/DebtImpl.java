package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.DebtRow;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class DebtImpl implements Debt
{
	private Calendar lastPayDate;
	private String code;
	private String description;
	private boolean fixed;
	private Calendar period;
	private List<DebtRow> rows;
	private String accountNumber;

	public String getCode()
	{
		return code;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean isFixed()
	{
		return fixed;
	}

	public Calendar getPeriod()
	{
		return period;
	}

	public Calendar getLastPayDate()
	{
		return lastPayDate;
	}

	public List<DebtRow> getRows()
	{
		return rows;
	}

	public void setLastPayDate(Calendar lastPayDate)
	{
		this.lastPayDate = lastPayDate;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}

	public void setPeriod(Calendar period)
	{
		this.period = period;
	}

	public void setRows(List<DebtRow> rows)
	{
		this.rows = rows;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}
}