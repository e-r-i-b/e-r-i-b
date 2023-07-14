package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.basket.invoiceSubscription.LightInvoiceSubscription;
import com.rssl.phizic.business.documents.payments.InvoiceSubscriptionOperationClaim;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author osminin
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ’эндлер, провер€ющий возможность подтвердить за€вку на операцию по автопоиску
 */
public abstract class CheckInvoiceSubscriptionOperationHandler extends BusinessDocumentHandlerBase
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof InvoiceSubscriptionOperationClaim))
		{
			throw new DocumentException("ќжидаетс€ InvoiceSubscriptionOperationClaim");
		}
		try
		{
			InvoiceSubscriptionOperationClaim claim = (InvoiceSubscriptionOperationClaim) document;
			LightInvoiceSubscription subscription = invoiceSubscriptionService.getLightSubscriptionById(claim.getInvoiceSubscriptionId());

			if (subscription == null)
			{
				throw new DocumentException("Ќе найден авопоиск счетов по id " + claim.getInvoiceSubscriptionId());
			}

			if (!check(subscription.getState()))
			{
				throw new DocumentLogicException(getMessage());
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	protected abstract String getMessage();

	protected abstract boolean check(InvoiceSubscriptionState state);
}
