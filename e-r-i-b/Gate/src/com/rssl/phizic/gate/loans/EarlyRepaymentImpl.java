package com.rssl.phizic.gate.loans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Досрочное погашение кредита
 * @author basharin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */

public class EarlyRepaymentImpl implements EarlyRepayment
{
	private BigDecimal amount;
	private Date date;
	private String status;
	private String account;
	private String repaymentChannel;
	private long terminationRequestId;

	public BigDecimal getAmount()
	{
		return amount;
	}

	public Date getDate()
	{
		return date;
	}

	public String getStatus()
	{
		return status;
	}

	public String getAccount()
	{
		return account;
	}

	public String getRepaymentChannel()
	{
		return repaymentChannel;
	}

	public long getTerminationRequestId()
	{
		return terminationRequestId;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public void setRepaymentChannel(String repaymentChannel)
	{
		this.repaymentChannel = repaymentChannel;
	}

	public void setTerminationRequestId(long terminationRequestId)
	{
		this.terminationRequestId = terminationRequestId;
	}
}