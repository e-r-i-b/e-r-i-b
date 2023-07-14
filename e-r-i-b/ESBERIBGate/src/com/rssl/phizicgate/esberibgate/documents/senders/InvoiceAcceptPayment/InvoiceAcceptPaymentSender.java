package com.rssl.phizicgate.esberibgate.documents.senders.InvoiceAcceptPayment;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * Сендер для отравки сообщения об акцепта инвойса в очердь шины
 * @author niculichev
 * @ created 01.06.15
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceAcceptPaymentSender extends AbstractOnlineJMSDocumentSender
{
	public InvoiceAcceptPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException {}

	public void prepare(GateDocument document) throws GateException, GateLogicException {}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException {}

	public void confirm(GateDocument document) throws GateException, GateLogicException {}

	public void validate(GateDocument document) throws GateException, GateLogicException {}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new InvoiceAcceptPaymentProcessor(getFactory(), (InvoiceAcceptPayment) document, getServiceName(document)));
	}
}
