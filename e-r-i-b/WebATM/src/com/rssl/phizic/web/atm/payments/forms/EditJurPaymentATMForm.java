package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.web.actions.payments.forms.EditJurPaymentForm;

import java.util.List;

/**
 * @author serkin
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditJurPaymentATMForm extends EditJurPaymentForm
{
    private List<BillingServiceProvider> serviceProviderList;

    public List<BillingServiceProvider> getServiceProviderList()
	{
		return serviceProviderList;
	}

    public void setServiceProviderList(List<BillingServiceProvider> serviceProviderList)
	{
		this.serviceProviderList = serviceProviderList;
	}
}
