package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;

import java.io.Serializable;

/**
 * @author lukina
 * @ created 27.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class BillingProviderService  implements MultiBlockDictionaryRecord, Serializable
{
	private BillingServiceProvider serviceProvider;
	private PaymentService paymentService;
	public BillingProviderService(){}

	public BillingProviderService(BillingServiceProvider serviceProvider,PaymentService paymentService )
	{
		 this.serviceProvider = serviceProvider;
		 this.paymentService = paymentService;
	}

	public BillingServiceProvider getServiceProvider()
	{
		return serviceProvider;
	}

	public void setServiceProvider(BillingServiceProvider serviceProvider)
	{
		this.serviceProvider = serviceProvider;
	}

	public PaymentService getPaymentService()
	{
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService)
	{
		this.paymentService = paymentService;
	}

	public String getMultiBlockRecordId()
	{
		return serviceProvider.getMultiBlockRecordId() + "^" + paymentService.getMultiBlockRecordId();
	}
}
