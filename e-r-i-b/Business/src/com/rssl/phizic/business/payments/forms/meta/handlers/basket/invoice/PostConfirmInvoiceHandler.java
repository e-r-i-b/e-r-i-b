package com.rssl.phizic.business.payments.forms.meta.handlers.basket.invoice;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.documents.payments.InvoicePayment;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;

/**
 * @author vagin
 * @ created 19.06.14
 * @ $Author$
 * @ $Revision$
 * Хендлер обновления подтвержденного к оплате инвойса идентификатором привязанного платежа
 */
public class PostConfirmInvoiceHandler extends BusinessDocumentHandlerBase
{
	private static InvoiceService invoiceService = new InvoiceService();
	private static SimpleService simpleService = new SimpleService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof InvoiceAcceptPayment))
		{
			throw new DocumentException("Ожидается InvoiceAcceptPayment");
		}
		try
		{
			InvoicePayment payment = (InvoicePayment) document;
			//обновляем в сессии документ. Нужен id для обновления инвойса.
			simpleService.addOrUpdate(document);
			invoiceService.payInvoice(payment.getInvoiceId(), payment.getId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
