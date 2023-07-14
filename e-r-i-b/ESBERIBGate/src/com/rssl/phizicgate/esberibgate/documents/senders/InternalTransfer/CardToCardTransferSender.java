package com.rssl.phizicgate.esberibgate.documents.senders.InternalTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.sender.CardToCardTransferSenderBase;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * —ендер платежа "ѕеревод между картами (моими картами)."
 */

public class CardToCardTransferSender extends CardToCardTransferSenderBase<InternalCardsTransfer>
{
	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public CardToCardTransferSender(GateFactory factory)
	{
		super(factory);
	}
}
