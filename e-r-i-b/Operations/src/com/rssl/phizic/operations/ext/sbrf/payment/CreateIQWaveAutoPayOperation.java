package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;

/**
 * ќпераци€ дл€ создани€ iqw автоплатежа с первого шага
 * @author lukina
 * @ created 05.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class CreateIQWaveAutoPayOperation  extends EditServicePaymentOperation
{
	protected boolean validateProvider(BillingServiceProviderBase providerBase)
	{
		BillingServiceProvider provider  = (BillingServiceProvider) providerBase;
		return (provider.getState() == ServiceProviderState.ACTIVE || provider.getState() == ServiceProviderState.MIGRATION) && AutoPaymentHelper.checkAutoPaymentSupport(provider);
	}
}
