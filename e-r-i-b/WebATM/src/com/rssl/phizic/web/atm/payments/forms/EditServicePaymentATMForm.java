package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;

/**
 * @author Erkin
 * @ created 21.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditServicePaymentATMForm extends EditServicePaymentForm
{
    private Long billing;
    private BillingServiceProviderBase serviceProvider;
	private String serviceGuid;
	private String providerGuid;

    /**
	 * @return ID биллинга
	 */
	public Long getBilling()
	{
		return billing;
	}

	public void setBilling(Long billing)
	{
		this.billing = billing;
	}

	/**
	 * @return ID поставщика
	 */
	public Long getProvider()
	{
		// то же самое, что получатель
		return getRecipient();
	}

	public void setProvider(Long provider)
	{
		setRecipient(provider);
	}

	public BillingServiceProviderBase getServiceProvider()
	{
		return serviceProvider;
	}

	public void setServiceProvider(BillingServiceProviderBase serviceProvider)
	{
		this.serviceProvider = serviceProvider;
	}

	public String getServiceGuid()
	{
		return serviceGuid;
	}

	public void setServiceGuid(String serviceGuid)
	{
		this.serviceGuid = serviceGuid;
	}

	public String getProviderGuid()
	{
		return providerGuid;
	}

	public void setProviderGuid(String providerGuid)
	{
		this.providerGuid = providerGuid;
	}
}
