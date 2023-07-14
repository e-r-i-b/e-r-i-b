package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.InvoiceSubscriptionOperationClaim;

/**
 * @author osminin
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Установить статус ожидания
 */
public class SetWaitInvoiceSubscriptionStateHandler extends BusinessDocumentHandlerBase
{
	private static final String STATUS_PARAMETER = "status";

	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof InvoiceSubscriptionOperationClaim))
		{
			throw new DocumentException("Ожидается InvoiceSubscriptionOperationClaim");
		}
		try
		{
			InvoiceSubscriptionOperationClaim claim = (InvoiceSubscriptionOperationClaim) document;
			String statusValue = getParameter(STATUS_PARAMETER);

			invoiceSubscriptionService.setWaitState(statusValue, claim.getInvoiceSubscriptionId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
