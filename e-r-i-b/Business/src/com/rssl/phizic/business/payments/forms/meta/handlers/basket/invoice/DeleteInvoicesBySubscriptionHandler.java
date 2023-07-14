package com.rssl.phizic.business.payments.forms.meta.handlers.basket.invoice;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.documents.payments.CloseInvoiceSubscriptionClaim;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;

/**
 * @author osminin
 * @ created 31.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Хэндлер, удалающий счета по идентификатору подписки (услуги)
 */
public class DeleteInvoicesBySubscriptionHandler extends BusinessDocumentHandlerBase
{
	private static InvoiceService invoiceService = new InvoiceService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof CloseInvoiceSubscription))
		{
			throw new DocumentException("Ожидается CloseInvoiceSubscription");
		}
		try
		{
			CloseInvoiceSubscriptionClaim claim = (CloseInvoiceSubscriptionClaim) document;
			invoiceService.inactivateAllByInvoiceSubId(claim.getInvoiceSubscriptionId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
