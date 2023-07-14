package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizicgate.esberibgate.payment.AutoSubscriptionHelper;
import com.rssl.phizicgate.esberibgate.payment.AutoTransferHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * Сендер автоплатежа карта-карта
 *
 *
 * @author khudyakov
 * @ created 08.09.14
 * @ $Author$
 * @ $Revision$
 */
public class CardToCardAutoSubscriptionSender extends CardAutoSubscriptionSenderBase
{
	private AutoTransferHelper autoTransferHelper;

	public CardToCardAutoSubscriptionSender(GateFactory factory)
	{
		super(factory);

		autoTransferHelper = new AutoTransferHelper(factory);
	}

	@Override
	public AutoSubscriptionModRq_Type createAutoSubscriptionModRq(AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		return autoTransferHelper.createAutoSubscriptionModRqBase(payment);
	}
}
