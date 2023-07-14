package com.rssl.phizic.web.common.mobile.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.operations.basket.invoiceSubscription.ConfirmCreateInvoiceSubscriptionOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.web.common.mobile.payments.forms.ConfirmMobilePaymentAction;

import java.math.BigDecimal;

/**
 * Подтверждение заявки на создания подписки на инвойсы
 * @ author: Gololobov
 * @ created: 23.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileConfirmInvoiceSubscriptionPaymentAction extends ConfirmMobilePaymentAction
{
	protected ConfirmFormPaymentOperation createConfirmOperation(ExistingSource source) throws BusinessException, BusinessLogicException
	{
		return createOperation(ConfirmCreateInvoiceSubscriptionOperation.class, getServiceName(source));
	}

	protected String getDefaultAvailableLimitAmountMessage(BigDecimal groupRiskAvailableAmount, BigDecimal obstructionAvailableAmount)
	{
		return null;
	}
}
