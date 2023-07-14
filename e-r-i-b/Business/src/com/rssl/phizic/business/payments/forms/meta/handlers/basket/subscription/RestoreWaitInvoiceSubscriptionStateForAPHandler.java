package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.InvoiceSubscriptionOperationClaim;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.common.types.basket.Constants;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author muhin
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 *
 * ¬осстановить статус, до статуса ожидани€
 */
public class RestoreWaitInvoiceSubscriptionStateForAPHandler extends BusinessDocumentHandlerBase
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(((JurPayment) document).getAttribute("isAutoInvoiceSubscriptionOperation") != null && "true".equals(((JurPayment) document).getAttribute("isAutoInvoiceSubscriptionOperation").getStringValue())))
		{
			return;
		}
		try
		{
			InvoiceSubscription invoiceSubscription = invoiceSubscriptionService.findById(((JurPayment)document).getInvoiceSubscriptionId());
			String statusValue = invoiceSubscription.getBaseState();
			int pos = statusValue.indexOf(Constants.STATE_DELIMITER);
			if (pos != 0)
			{
				statusValue  = statusValue.substring(0, pos);
			}
			invoiceSubscriptionService.updateState(InvoiceSubscriptionState.valueOf(statusValue), ((JurPayment)document).getInvoiceSubscriptionId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
