package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractJMSCommissionCalculator;

/**
 * Отправка jms сообщения на получение комиссии автоплатежа p2p через синхронную очередь.
 *
 * @author bogdanov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */

public class P2PAutoSubscriptionCommissionCalculator extends AbstractJMSCommissionCalculator
{
	private final P2PAutoSubscriptionCommissionListener commissionListener;
	public P2PAutoSubscriptionCommissionCalculator(GateFactory factory, P2PAutoSubscriptionCommissionListener commissionListener)
	{
		super(factory);
		this.commissionListener = commissionListener;
	}

	public final void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		process(new P2PAutosubscriptionCommissionMessageProcessor(getFactory(), (AutoSubscriptionClaim) document, getServiceName(document), commissionListener));
	}
}
