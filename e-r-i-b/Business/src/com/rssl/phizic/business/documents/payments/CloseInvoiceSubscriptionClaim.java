package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import org.apache.commons.lang.BooleanUtils;

/**
 * @author osminin
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Заявка на закрытие подписки на задолженности
 */
public class CloseInvoiceSubscriptionClaim extends InvoiceSubscriptionOperationClaim implements CloseInvoiceSubscription
{
	private static final String RECOVER_AUTO_SUBSCRIPTION_FLAG = "recover-auto-subscription";

	@Override
	public Class<? extends GateDocument> getType()
	{
		return CloseInvoiceSubscription.class;
	}

	public boolean isRecoverAutoSubscription()
	{
		return BooleanUtils.isTrue(getNullSaveAttributeBooleanValue(RECOVER_AUTO_SUBSCRIPTION_FLAG));
	}

	public void setRecoverAutoSubscription(boolean recover)
	{
		setNullSaveAttributeBooleanValue(RECOVER_AUTO_SUBSCRIPTION_FLAG, recover);
	}
}
