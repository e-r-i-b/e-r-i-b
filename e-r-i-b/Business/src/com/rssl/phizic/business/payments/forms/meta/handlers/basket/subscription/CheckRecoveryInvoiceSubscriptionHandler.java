package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author osminin
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ’эндлер, провер€ющий возможность подтверждени€ за€вки на возобновление автопоиска
 */
public class CheckRecoveryInvoiceSubscriptionHandler extends CheckInvoiceSubscriptionOperationHandler
{
	@Override
	protected String getMessage()
	{
		return "«а€вка на возобновление автопоиска счетов доступна только дл€ приостановленной услуги. ѕожалуйста, создайте новую за€вку.";
	}

	@Override
	protected boolean check(InvoiceSubscriptionState state)
	{
		return InvoiceSubscriptionState.STOPPED == state;
	}
}
