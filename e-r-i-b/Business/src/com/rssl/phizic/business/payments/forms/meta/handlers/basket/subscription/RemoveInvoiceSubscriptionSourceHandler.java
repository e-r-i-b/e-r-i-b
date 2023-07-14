package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.InvoiceSubscriptionOperationClaim;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * Удаление подписки, на основе которой идет создание новой
 * @author niculichev
 * @ created 19.11.14
 * @ $Author$
 * @ $Revision$
 */
public class RemoveInvoiceSubscriptionSourceHandler extends BusinessDocumentHandlerBase<CreateInvoiceSubscriptionPayment>
{
	private static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(CreateInvoiceSubscriptionPayment document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			if(document.getInvoiceSubscriptionId() != null)
				invoiceSubscriptionService.removeGeneratedSubscription(document.getInvoiceSubscriptionId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
