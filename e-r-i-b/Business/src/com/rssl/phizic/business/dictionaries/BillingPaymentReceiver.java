package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;

/**
 * @author akrenev
 * @ created 15.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class BillingPaymentReceiver extends PaymentReceiverJur
{
	private String serviceProviderKey;
	private BillingServiceProvider serviceProvider;

	public String getServiceProviderKey()
	{
		return serviceProviderKey;
	}

	public void setServiceProviderKey(String serviceProviderKey)
	{
		this.serviceProviderKey = serviceProviderKey;
	}

	public BillingServiceProvider getServiceProvider()
	{
		return serviceProvider;
	}

	public void setServiceProvider(BillingServiceProvider serviceProvider)
	{
		this.serviceProvider = serviceProvider;
	}
}
