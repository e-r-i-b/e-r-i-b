package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.basket.RecoveryInvoiceSubscription;

/**
 * @author osminin
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Заявка на восстановление подписки на задолженности
 */
public class RecoveryInvoiceSubscriptionClaim extends InvoiceSubscriptionOperationClaim implements RecoveryInvoiceSubscription
{
	@Override
	public Class<? extends GateDocument> getType()
	{
		return RecoveryInvoiceSubscription.class;
	}
}
