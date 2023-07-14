package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author osminin
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ’эндлер, провер€ющий возможность подтверждени€ за€вки на приостановку автопоиска
 */
public class CheckDelayInvoiceSubscriptionHandler extends CheckInvoiceSubscriptionOperationHandler
{
	@Override
	protected String getMessage()
	{
		return "«а€вка на приостановку автопоиска счетов доступна только дл€ активной услуги. ѕожалуйста, создайте новую за€вку.";
	}

	@Override
	protected boolean check(InvoiceSubscriptionState state)
	{
		return InvoiceSubscriptionState.ACTIVE == state;
	}
}
