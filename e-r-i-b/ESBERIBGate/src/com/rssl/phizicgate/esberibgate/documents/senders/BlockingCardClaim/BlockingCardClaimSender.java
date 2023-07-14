package com.rssl.phizicgate.esberibgate.documents.senders.BlockingCardClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер заявки "Заявка на блокировку карты"
 */

public class BlockingCardClaimSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public BlockingCardClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new BlockingCardClaimProcessor(getFactory(), (CardBlockingClaim) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка заявки на блокировку карты не поддерживается");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("Подготовка заявки на блокировку карты не поддерживается");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("Подтверждение заявки на блокировку карты не поддерживается");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("Валидация заявки на блокировку карты не поддерживается");
	}
}
