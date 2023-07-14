package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.basket.invoiceSubscription.ErrorInfo;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionErrorType;

/**
 * @author niculichev
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdateErrorInvoiceSubscriptionEntityAction extends BusinessDocumentHandlerBase
{
	private static final String REFUSE_CREATE_INVOICE_SUB_MESSAGE = "Ќе удалось настроить автопоиск по этой услуге";
	private static final String REFUSE_CLAIM_INVOICE_SUB_MESSAGE = "Ќужно обновить параметры";
	private static final InvoiceService invoiceService = new InvoiceService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			if(document instanceof CreateInvoiceSubscriptionPayment)
			{
				CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) document;
				invoiceService.updateInvoiceSubErrorByOperUID(payment.getOperationUID(), InvoiceSubscriptionErrorType.CRITICAL, ErrorInfo.buildListErrorInfo(REFUSE_CREATE_INVOICE_SUB_MESSAGE));
			}
			else if(document instanceof InvoiceSubscriptionOperationClaim)
			{
				InvoiceSubscriptionOperationClaim claim = (InvoiceSubscriptionOperationClaim) document;
				invoiceService.updateInvoiceSubErrorById(claim.getInvoiceSubscriptionId(), InvoiceSubscriptionErrorType.NOT_CRITICAL, ErrorInfo.buildListErrorInfo(REFUSE_CLAIM_INVOICE_SUB_MESSAGE));
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
