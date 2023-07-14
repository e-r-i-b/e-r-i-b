package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.sender.CardToCardTransferSenderBase;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер платежа "Перевод с карты на карту Сбербанку (не мою)"
 */

public class CardToCardOurBankTransferSender extends CardToCardTransferSenderBase<ExternalCardsTransferToOurBank>
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public CardToCardOurBankTransferSender(GateFactory factory)
	{
		super(factory);
	}
}
