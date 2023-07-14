package com.rssl.phizicgate.iqwave.listener;

import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import org.w3c.dom.Document;

/**
 * Обработчик сообщений для автоплатежей
 * @author niculichev
 * @ created 26.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentExecutionResultProcessor extends ExecutionResultProcessorBase
{

	public void fillPaymentData(SynchronizableDocument payment, Document bodyContent) throws GateException
	{
		if (!(payment instanceof AutoPayment))
		{
			throw new GateException("Документ " + payment.getExternalId() + " имеет неверный тип");
		}
	}
}
