package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер платежа "Перевод физическому лицу c карты на счет в другой банк внутри Сбербанка России."
 */

public class CardToAccountPhizOurBankTransferSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public CardToAccountPhizOurBankTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new CardToAccountPhizOurBankTransferProcessor(getFactory(), (CardIntraBankPayment) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка платежа \"Перевод физическому лицу c карты на счет в другой банк через платежную систему России.\" не поддерживается");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("Подготовка платежа \"Перевод физическому лицу c карты на счет в другой банк через платежную систему России.\" не поддерживается");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("Подтверждение платежа \"Перевод физическому лицу c карты на счет в другой банк через платежную систему России.\" не поддерживается");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("Валидация платежа \"Перевод физическому лицу c карты на счет в другой банк через платежную систему России.\" не поддерживается");
	}
}
