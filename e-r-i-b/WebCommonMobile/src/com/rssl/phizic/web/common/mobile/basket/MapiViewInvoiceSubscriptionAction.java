package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.invoiceSubscription.ViewInvoiceSubscriptionOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author sergunin
 * @since 28.04.15.
 */
public class MapiViewInvoiceSubscriptionAction extends ViewActionBase
{
    @Override
  	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
  	{
  		ViewInvoiceSubscriptionOperation operation = createOperation(ViewInvoiceSubscriptionOperation.class);
  		operation.initialize(frm.getId());
  		return operation;
  	}

  	@Override
  	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
  	{
        MapiViewInvoiceSubscriptionForm frm = (MapiViewInvoiceSubscriptionForm) form;
  		ViewInvoiceSubscriptionOperation op = (ViewInvoiceSubscriptionOperation) operation;
  		InvoiceSubscription subscription = op.getEntity();

  		frm.setInvoiceSubscription(subscription);
  		frm.setAccountingEntityName(op.getAccountingEntityName());
  		frm.setProviderIcon(BasketHelper.getProviderIcon(subscription.getRecipientId()));
  	}
}
