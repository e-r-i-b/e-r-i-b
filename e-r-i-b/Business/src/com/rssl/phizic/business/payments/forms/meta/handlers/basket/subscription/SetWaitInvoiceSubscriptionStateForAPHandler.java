package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.InvoiceSubscriptionOperationClaim;
import com.rssl.phizic.business.documents.payments.JurPayment;

/**
 * @author muhin
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 *
 * Установить статус ожидания
 */
public class SetWaitInvoiceSubscriptionStateForAPHandler extends BusinessDocumentHandlerBase
{
	private static final String STATUS_PARAMETER = "status";

	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(((JurPayment) document).getAttribute("isAutoInvoiceSubscriptionOperation") != null && "true".equals(((JurPayment) document).getAttribute("isAutoInvoiceSubscriptionOperation").getStringValue())))
		{
			return;
		}
		try
		{
			String statusValue = getParameter(STATUS_PARAMETER);

			invoiceSubscriptionService.setWaitState(statusValue, ((JurPayment)document).getInvoiceSubscriptionId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
