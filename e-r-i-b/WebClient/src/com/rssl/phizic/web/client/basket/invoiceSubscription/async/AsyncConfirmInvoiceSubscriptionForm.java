package com.rssl.phizic.web.client.basket.invoiceSubscription.async;

import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма подтверждения подписки на инвойсы
 * @author niculichev
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */
public class AsyncConfirmInvoiceSubscriptionForm extends EditFormBase
{
	private InvoiceSubscription subscription;

	public InvoiceSubscription getSubscription()
	{
		return subscription;
	}

	public void setSubscription(InvoiceSubscription subscription)
	{
		this.subscription = subscription;
	}
}
