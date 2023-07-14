package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.claims.DepositClosingClaim;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;

public class DepositClosingClaimSender extends AbstractDocumentSender
{
	public DepositClosingClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof DepositClosingClaim))
			throw new GateException("Неверный тип платежа, должен быть - DepositClosingClaim.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("closeDeposit_q");
	}

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(message, gateDocument);
		if (!(gateDocument instanceof DepositClosingClaim))
			throw new GateException("Неверный тип платежа, должен быть - DepositClosingClaim.");

		DepositClosingClaim claim = (DepositClosingClaim) gateDocument;

		DepositService depositService = GateSingleton.getFactory().service(DepositService.class);
		Deposit deposit = depositService.getDepositById(claim.getExternalDepositId().toString());
		DepositInfo depositInfo = depositService.getDepositInfo(deposit);
		message.addParameter("chargeOffAccount",depositInfo.getAccount().getNumber());

		message.addParameter("receiverAccount",claim.getDestinationAccount());
	}
}