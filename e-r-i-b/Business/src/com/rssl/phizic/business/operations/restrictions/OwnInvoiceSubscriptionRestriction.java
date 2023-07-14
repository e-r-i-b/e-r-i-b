package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;

/**
 * Рестрикшн для проверки принадлежности подписки на инвойсы клиенту
 * @author niculichev
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */
public class OwnInvoiceSubscriptionRestriction implements InvoiceSubscriptionRestriction
{
	public boolean accept(InvoiceSubscription invoiceSubscription)
	{
		CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();
		return invoiceSubscription.getLoginId() == login.getId();
	}
}
