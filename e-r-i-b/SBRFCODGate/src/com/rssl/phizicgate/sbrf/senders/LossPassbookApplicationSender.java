package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;

/**
 * @author Egorova
 * @ created 25.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LossPassbookApplicationSender extends AbstractDocumentSender
{
	public LossPassbookApplicationSender(GateFactory factory)
	{
		super(factory);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return "lossingPassbook_q";
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof LossPassbookApplicationClaim))
			throw new GateException("Неверный тип платежа, должен быть - LossPassbookApplicationClaim.");

		LossPassbookApplicationClaim lossPassbookApplicationClaim = (LossPassbookApplicationClaim) gateDocument;

		gateMessage.addParameter("clientId", getClientId(gateDocument.getExternalOwnerId()));
		gateMessage.addParameter("account", lossPassbookApplicationClaim.getDepositAccount());
	}
}
