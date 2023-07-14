package com.rssl.phizic.business.payments.forms.meta.handlers.basket.invoice;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.documents.payments.InvoicePayment;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;

/**
 * @author osminin
 * @ created 08.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ’эндлер, обновл€ющий статус счета
 */
public class UpdateInvoiceStatusHandler extends BusinessDocumentHandlerBase
{
	private static final String STATUS_PARAMETER = "status";

	private static InvoiceService invoiceService = new InvoiceService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof InvoiceAcceptPayment))
		{
			throw new DocumentException("ќжидаетс€ InvoiceAcceptPayment");
		}
		try
		{
			InvoicePayment payment = (InvoicePayment) document;
			String statusValue = getParameter(STATUS_PARAMETER);

			invoiceService.changeStateById(InvoiceStatus.valueOf(statusValue), payment.getInvoiceId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
