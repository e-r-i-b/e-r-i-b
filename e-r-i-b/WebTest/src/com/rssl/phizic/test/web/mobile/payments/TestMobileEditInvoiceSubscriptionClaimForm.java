package com.rssl.phizic.test.web.mobile.payments;

/**
 * @author saharnova
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 */

public class TestMobileEditInvoiceSubscriptionClaimForm extends TestMobileDocumentForm
{
	private Long subscriptionId;
	private String subscriptionName;
	private String fromResource;
	private Long accountingEntityId;
	private String eventType;
	private String dayPay;

	public Long getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	public String getSubscriptionName()
	{
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName)
	{
		this.subscriptionName = subscriptionName;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public Long getAccountingEntityId()
	{
		return accountingEntityId;
	}

	public void setAccountingEntityId(Long accountingEntityId)
	{
		this.accountingEntityId = accountingEntityId;
	}

	public String getEventType()
	{
		return eventType;
	}

	public void setEventType(String eventType)
	{
		this.eventType = eventType;
	}

	public String getDayPay()
	{
		return dayPay;
	}

	public void setDayPay(String dayPay)
	{
		this.dayPay = dayPay;
	}
}
