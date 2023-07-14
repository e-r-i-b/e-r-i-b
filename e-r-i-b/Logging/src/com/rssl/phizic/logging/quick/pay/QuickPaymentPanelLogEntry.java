package com.rssl.phizic.logging.quick.pay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author komarov
 * @ created 19.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class QuickPaymentPanelLogEntry implements Serializable
{
	private Long id; //Идентификатор записи
	private Long quickPaymentPanelBlokId; //Идентификатор Блока в ПБО
	private String tbNumber; //Идентификатор ТБ для которого сделана запись
	private PanelLogEntryType type;//Тип события
	private Calendar time;//Дата события
	private BigDecimal amount;//сумма

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getQuickPaymentPanelBlokId()
	{
		return quickPaymentPanelBlokId;
	}

	public void setQuickPaymentPanelBlokId(Long quickPaymentPanelBlokId)
	{
		this.quickPaymentPanelBlokId = quickPaymentPanelBlokId;
	}

	public String getTbNumber()
	{
		return tbNumber;
	}

	public void setTbNumber(String tbNumber)
	{
		this.tbNumber = tbNumber;
	}

	public PanelLogEntryType getType()
	{
		return type;
	}

	public void setType(PanelLogEntryType type)
	{
		this.type = type;
	}

	public Calendar getTime()
	{
		return time;
	}

	public void setTime(Calendar time)
	{
		this.time = time;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}
