package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.dictionaries.providers.BillingProviderService;
import java.util.Set;

/**
 * @author khudyakov
 * @ created 18.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditGroupPaymentServiceServiceProviderForm extends EditServiceProviderFormBase
{
	private String newIds;
	private Set<BillingProviderService> billingProviderServices;
	public String getNewIds()
	{
		return newIds;
	}

	public void setNewIds(String newIds)
	{
		this.newIds = newIds;
	}

	public Set <BillingProviderService> getBillingProviderServices()
	{
		return billingProviderServices;
	}

	public void setBillingProviderServices(Set<BillingProviderService> billingProviderServices)
	{
		this.billingProviderServices = billingProviderServices;
	}
}
