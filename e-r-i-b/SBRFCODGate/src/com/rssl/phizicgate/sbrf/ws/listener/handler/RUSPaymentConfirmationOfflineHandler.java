package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * @author krenev
 * @ created 07.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class RUSPaymentConfirmationOfflineHandler extends PaymentConfirmationOfflineHandlerBase
{
	protected boolean isConvertionPayment(AbstractAccountTransfer document)
	{
		return false;
	}

	protected String getDestinationAccount(AbstractAccountTransfer document)
	{
		AbstractRUSPayment payment = (AbstractRUSPayment) document;
		return payment.getReceiverAccount();
	}
}
