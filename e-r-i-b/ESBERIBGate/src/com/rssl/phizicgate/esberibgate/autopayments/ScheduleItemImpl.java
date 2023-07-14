package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.gate.autopayments.PaymentStatus;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author bogdanov
 * @ created 06.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * информация о платеже, совершенном по автоплатежу.
 */

public class ScheduleItemImpl implements ScheduleItem
{
	private String externalId;
	private Long number;
	private Calendar date;
	private PaymentStatus status;
	private String rejectionCause;
	private BigDecimal commission;
	private BigDecimal summ;
	private String RecipientName;

	public ScheduleItemImpl()
	{
	}

	public ScheduleItemImpl(String externalId,
	                        Long number,
	                        Calendar date,
	                        PaymentStatus status,
	                        String rejectionCause,
	                        BigDecimal commission,
	                        BigDecimal summ,
	                        String recipientName)
	{
		this.externalId = externalId;
		this.number = number;
		this.date = date;
		this.status = status;
		this.rejectionCause = rejectionCause;
		this.commission = commission;
		this.summ = summ;
		RecipientName = recipientName;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public Long getNumber()
	{
		return number;
	}

	public void setNumber(Long number)
	{
		this.number = number;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public PaymentStatus getStatus()
	{
		return status;
	}

	public void setStatus(PaymentStatus status)
	{
		this.status = status;
	}

	public String getRejectionCause()
	{
		return rejectionCause;
	}

	public void setRejectionCause(String rejectionCause)
	{
		this.rejectionCause = rejectionCause;
	}

	public BigDecimal getCommission()
	{
		return commission;
	}

	public void setCommission(BigDecimal commission)
	{
		this.commission = commission;
	}

	public BigDecimal getSumm()
	{
		return summ;
	}

	public void setSumm(BigDecimal summ)
	{
		this.summ = summ;
	}

	public String getRecipientName()
	{
		return RecipientName;
	}

	public void setRecipientName(String recipientName)
	{
		RecipientName = recipientName;
	}
}
