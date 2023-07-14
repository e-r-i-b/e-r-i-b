package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.sender.CardToCardTransferSenderBase;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сендер платежа "Перевод с карты на карту стороннего банка (по отношению к Сбербанку)."
 */

public class CardToCardIntraBankTransferSender extends CardToCardTransferSenderBase<ExternalCardsTransferToOtherBank>
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public CardToCardIntraBankTransferSender(GateFactory factory)
	{
		super(factory);
	}
}
