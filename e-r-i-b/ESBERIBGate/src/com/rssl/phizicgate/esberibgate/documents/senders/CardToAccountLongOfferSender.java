package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizicgate.esberibgate.payment.MoneyBoxHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionModRq_Type;

/**
 * @author vagin
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 * Сендер заявки на создание копилки
 */
public class CardToAccountLongOfferSender extends CardAutoSubscriptionSenderBase
{
	private MoneyBoxHelper moneyBoxHelper;

	public CardToAccountLongOfferSender(GateFactory factory)
	{
		super(factory);

		moneyBoxHelper = new MoneyBoxHelper(factory);
	}

	@Override
	public AutoSubscriptionModRq_Type createAutoSubscriptionModRq(AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		return moneyBoxHelper.createAutoSubscriptionModRqBase(payment);
	}
}
