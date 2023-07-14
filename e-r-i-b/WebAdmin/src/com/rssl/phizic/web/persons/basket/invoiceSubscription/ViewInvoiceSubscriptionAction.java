package com.rssl.phizic.web.persons.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.invoiceSubscription.ViewInvoiceSubscriptionOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author osminin
 * @ created 14.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен просмотра подписки (услуги)
 */
public class ViewInvoiceSubscriptionAction extends ViewActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewInvoiceSubscriptionOperation operation = createOperation(ViewInvoiceSubscriptionOperation.class);
		operation.initialize(frm.getId(), true);
		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewInvoiceSubscriptionForm frm = (ViewInvoiceSubscriptionForm) form;
		ViewInvoiceSubscriptionOperation op = (ViewInvoiceSubscriptionOperation) operation;
		InvoiceSubscription subscription = op.getEntity();

		frm.setInvoiceSubscription(subscription);
		frm.setAccountingEntityName(op.getAccountingEntityName());
		frm.setProviderIcon(BasketHelper.getProviderIcon(subscription.getRecipientId()));
	}
}
