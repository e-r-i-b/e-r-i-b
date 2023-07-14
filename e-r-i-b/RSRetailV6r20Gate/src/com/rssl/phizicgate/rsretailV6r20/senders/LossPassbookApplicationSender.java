package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;

/**
 * @author Egorova
 * @ created 25.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LossPassbookApplicationSender extends AbstractDocumentSender
{
	private int TRANSFER_TO_ACCOUNT = 2;

	public LossPassbookApplicationSender(GateFactory factory)
	{
		super(factory);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return "savingsBookLoss_q";
	}

	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof LossPassbookApplicationClaim))
			throw new GateException("Неверный тип платежа, должен быть - LossPassbookApplicationClaim.");

		LossPassbookApplicationClaim lossPassbookApplicationClaim = (LossPassbookApplicationClaim) gateDocument;

		gateMessage.addParameter("accountNumber",lossPassbookApplicationClaim.getDepositAccount());		
		gateMessage.addParameter("action", lossPassbookApplicationClaim.getAccountAction());
		if(lossPassbookApplicationClaim.getAccountAction()== TRANSFER_TO_ACCOUNT)
			gateMessage.addParameter("receiverAccount", lossPassbookApplicationClaim.getReceiverAccount());
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof LossPassbookApplicationClaim))
			throw new GateException("Неверный тип платежа, должен быть - LossPassbookApplicationClaim.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("savingsBookLoss_q");
	}

}
