package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.InvoiceSubscriptionOperationClaim;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author osminin
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ’ендлер, обновл€ющий статус подписки (услуги) при смене статуса на Executed
 */
public class UpdateInvoiceSubscriptionStateHandler extends BusinessDocumentHandlerBase
{
	private static final String STATUS_PARAMETER = "status";

	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof InvoiceSubscriptionOperationClaim))
		{
			throw new DocumentException("ќжидаетс€ InvoiceSubscriptionOperationClaim");
		}
		try
		{
			InvoiceSubscriptionOperationClaim claim = (InvoiceSubscriptionOperationClaim) document;
			String statusValue = getParameter(STATUS_PARAMETER);

			invoiceSubscriptionService.updateState(InvoiceSubscriptionState.valueOf(statusValue), claim.getInvoiceSubscriptionId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
