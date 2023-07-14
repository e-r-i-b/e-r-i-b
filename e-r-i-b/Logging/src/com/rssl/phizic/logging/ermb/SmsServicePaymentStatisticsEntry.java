package com.rssl.phizic.logging.ermb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Запись об успешном или неуспешном исполнении платежа ПУ в смс-канале
 * @author Rtischeva
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */
public class SmsServicePaymentStatisticsEntry implements Serializable
{
	private Long paymentId;  //идентификатор платежа

	private Long serviceProviderId;  //идентификатор ПУ

	private String serviceProviderName; //название ПУ

	private ErmbServicePaymentState paymentState; //статус платежа

	private BigDecimal amount; //сумма

	private String currency; //валюта

	private String tb; //ТБ

	private Calendar finalStatusDate; //дата финального статуса платежа

	public Long getPaymentId()
	{
		return paymentId;
	}

	public void setPaymentId(Long paymentId)
	{
		this.paymentId = paymentId;
	}

	public Long getServiceProviderId()
	{
		return serviceProviderId;
	}

	public void setServiceProviderId(Long serviceProviderId)
	{
		this.serviceProviderId = serviceProviderId;
	}

	public String getServiceProviderName()
	{
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName)
	{
		this.serviceProviderName = serviceProviderName;
	}

	public ErmbServicePaymentState getPaymentState()
	{
		return paymentState;
	}

	public void setPaymentState(ErmbServicePaymentState paymentState)
	{
		this.paymentState = paymentState;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public Calendar getFinalStatusDate()
	{
		return finalStatusDate;
	}

	public void setFinalStatusDate(Calendar finalStatusDate)
	{
		this.finalStatusDate = finalStatusDate;
	}
}
