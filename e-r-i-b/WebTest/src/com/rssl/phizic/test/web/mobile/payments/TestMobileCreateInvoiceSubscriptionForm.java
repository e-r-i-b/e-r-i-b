package com.rssl.phizic.test.web.mobile.payments;

/**
 * @author Balovtsev
 * @since 30.10.14.
 */
public class TestMobileCreateInvoiceSubscriptionForm extends TestMobileDocumentForm
{
	private Long accountingEntityId;
	private Long serviceId;

	public Long getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}

	public Long getAccountingEntityId()
	{
		return accountingEntityId;
	}

	public void setAccountingEntityId(Long accountingEntityId)
	{
		this.accountingEntityId = accountingEntityId;
	}
}
