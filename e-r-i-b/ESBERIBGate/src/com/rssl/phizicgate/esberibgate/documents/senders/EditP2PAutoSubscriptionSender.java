package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizicgate.esberibgate.payment.EditAutoTransferHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.AutoSubscriptionModRq_Type;

/**
 * @author khudyakov
 * @ created 09.11.14
 * @ $Author$
 * @ $Revision$
 */
public class EditP2PAutoSubscriptionSender extends CardAutoSubscriptionSenderBase
{
	private EditAutoTransferHelper autoTransferHelper;

	public EditP2PAutoSubscriptionSender(GateFactory factory)
	{
		super(factory);

		autoTransferHelper = new EditAutoTransferHelper(factory);
	}

	@Override
	protected AutoSubscriptionModRq_Type createAutoSubscriptionModRq(AutoSubscriptionClaim payment) throws GateException, GateLogicException
	{
		return autoTransferHelper.createAutoSubscriptionModRqBase(payment);
	}
}
