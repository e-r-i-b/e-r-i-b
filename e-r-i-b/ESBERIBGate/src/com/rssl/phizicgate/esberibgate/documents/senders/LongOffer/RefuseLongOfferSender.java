package com.rssl.phizicgate.esberibgate.documents.senders.LongOffer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.RefuseLongOffer;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер заявки "Отмена выполнения регулярного платежа"
 */

public class RefuseLongOfferSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public RefuseLongOfferSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new RefuseLongOfferProcessor(getFactory(), (RefuseLongOffer) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка заявки на отмену выполнения регулярного платежа не поддерживается");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("Подготовка заявки на отмену выполнения регулярного платежа не поддерживается");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("Подтверждение заявки на отмену выполнения регулярного платежа не поддерживается");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("Валидация заявки на отмену выполнения регулярного платежа не поддерживается");
	}
}
