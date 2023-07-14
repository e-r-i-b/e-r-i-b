package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.operations.access.NullConfirmStrategySource;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * Подтверждение создания заявки на подписку получения инвойсов
 * @author basharin
 * @ created 08.08.14
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmCreateInvoiceSubscriptionOperation extends ConfirmFormPaymentOperation
{
	public ConfirmStrategySource getStrategy()
	{
		//В mAPI стратегия без подтверждения
		if (ApplicationUtil.isMobileApi())
			return new NullConfirmStrategySource();
		return super.getStrategy();
	}
}
