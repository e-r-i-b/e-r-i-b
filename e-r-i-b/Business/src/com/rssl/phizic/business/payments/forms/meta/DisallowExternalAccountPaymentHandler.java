package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.RurPayment;

/**
 * Хендлер на проверку доступности внешнего платежа со счета при подтверждении.
 *
 * @author bogdanov
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class DisallowExternalAccountPaymentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof RurPayment))
			return;
		RurPayment payment = (RurPayment)document;
		if (!DocumentHelper.isPaymentDisallowedFromAccount(payment))
			return;
		throw new DocumentLogicException(DocumentHelper.getDisallowedExternalAccountMessage(payment));
	}
}
