package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;

/**
 * @author Jatsky
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class AutoPaymentTypesListsApiSource extends AutoPaymentTypesListsSource
{
	@Override protected boolean checkAutoPaymentSupported(BillingServiceProvider billingServiceProvider)
	{
		return billingServiceProvider.isAutoPaymentSupportedInApi();
	}
}
