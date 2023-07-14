package com.rssl.phizic.web.common.socialApi.payments.forms;

import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;

/**
 * @author Erkin
 * @ created 21.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileServicePaymentForm extends EditServicePaymentForm
{
    private Long billing;
    private BillingServiceProviderBase serviceProvider;
    private Long trustedRecipientId;

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

    public Long getTrustedRecipientId()
    {
        return trustedRecipientId;
    }

    public void setTrustedRecipientId(Long trustedRecipientId)
    {
        this.trustedRecipientId = trustedRecipientId;
    }
}
