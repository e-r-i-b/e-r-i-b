package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.basket.DelayInvoiceSubscription;

/**
 * @author osminin
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ������������ �������� �� �������������
 */
public class DelayInvoiceSubscriptionClaim extends InvoiceSubscriptionOperationClaim implements DelayInvoiceSubscription
{
	@Override
	public Class<? extends GateDocument> getType()
	{
		return DelayInvoiceSubscription.class;
	}
}
