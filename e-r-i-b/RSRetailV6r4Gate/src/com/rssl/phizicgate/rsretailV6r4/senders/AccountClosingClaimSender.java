package com.rssl.phizicgate.rsretailV6r4.senders;

import com.rssl.phizgate.common.payments.PaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.AccountClosingClaim;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.AccountRUSPayment;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;

public class AccountClosingClaimSender extends AbstractDocumentSender
{
	public AccountClosingClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof AccountClosingClaim))
			throw new GateException("Неверный тип платежа, должен быть - AccountClosingClaim.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("closeDeposit_q");
	}
	
	void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(gateMessage,gateDocument);
		if (!(gateDocument instanceof AccountClosingClaim))
			throw new GateException("Неверный тип платежа, должен быть - AccountClosingClaim.");
		AccountClosingClaim claim = (AccountClosingClaim) gateDocument;

		gateMessage.addParameter("chargeOffAccount",claim.getClosedAccount());

		GateDocument transferPayment = claim.getTransferPayment();

		if (transferPayment instanceof ClientAccountsTransfer)
		{
			ClientAccountsTransfer accountsTransfer = (ClientAccountsTransfer)transferPayment;
			gateMessage.addParameter("receiverAccount",accountsTransfer.getReceiverAccount());
			if (accountsTransfer.getCommission() != null)
				gateMessage.addParameter("commission", accountsTransfer.getCommission().getDecimal().toString());
			if(accountsTransfer.getGround()!= null)
			{
				gateMessage.addParameter("ground", accountsTransfer.getGround());
			}
		}
		else if (transferPayment instanceof AbstractRUSPayment)
		{
			AccountRUSPayment rusPayment = (AccountRUSPayment)transferPayment;

			gateMessage.addParameter("receiverName", PaymentHelper.getReceiverName(rusPayment));
			ResidentBank residentBank = rusPayment.getReceiverBank();
			gateMessage.addParameter("receiverBIC",residentBank.getBIC());
			gateMessage.addParameter("receiverAccount",rusPayment.getReceiverAccount());
			gateMessage.addParameter("receiverCorAccount",residentBank.getAccount());
			gateMessage.addParameter("receiverINN",rusPayment.getReceiverINN());
			if (rusPayment instanceof AbstractJurTransfer)
			{
				AbstractJurTransfer jurTransfer = (AbstractJurTransfer) rusPayment;
				gateMessage.addParameter("receiverKPP", jurTransfer.getReceiverKPP());
			}
			gateMessage.addParameter("receiverBankName",residentBank.getName());
			if (rusPayment.getCommission() != null)
				gateMessage.addParameter("commission", rusPayment.getCommission().getDecimal().toString());
			if(rusPayment.getGround() != null)
			{
				gateMessage.addParameter("ground", rusPayment.getGround());
			}
		}
		else
			throw new GateException("Не поддерживается тип платежа: " + transferPayment.getType());
	}
}