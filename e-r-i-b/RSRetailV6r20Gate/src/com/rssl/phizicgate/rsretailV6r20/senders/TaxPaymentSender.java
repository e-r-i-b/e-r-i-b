package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.AccountRUSTaxPayment;
import com.rssl.phizic.gate.payments.RUSTaxPayment;

/**
 * @author // v620
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */

public class TaxPaymentSender extends RUSPaymentSender
{
	public TaxPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof RUSTaxPayment))
			throw new GateException("Ќеверный тип платежа, должен быть - RUSTaxPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("externalTransfer_q");
	}

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(message, gateDocument);
		if (!(gateDocument instanceof AccountRUSTaxPayment))
			throw new GateException("Ќеверный тип платежа, должен быть - AccountRUSTaxPayment.");
		AccountRUSTaxPayment rusTaxPayment = (AccountRUSTaxPayment) gateDocument;

		message.addParameter("chargeOffAccount",rusTaxPayment.getChargeOffAccount());
		//Element chargeOffAmount = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "chargeOffAmount");
		//XmlHelper.appendSimpleElement(chargeOffAmount, "value",rusTaxPayment.getChargeOffAmount().getDecimal().toString());

		ResidentBank residentBank = rusTaxPayment.getReceiverBank();
		message.addParameter("receiverName",        rusTaxPayment.getReceiverName());
		message.addParameter("receiverBIC",         residentBank.getBIC());
		message.addParameter("receiverCorAccount",  residentBank.getAccount());
		message.addParameter("receiverINN",         rusTaxPayment.getReceiverINN());
		if (rusTaxPayment.getReceiverKPP()!= null)
			message.addParameter("receiverKPP",     rusTaxPayment.getReceiverKPP());
		message.addParameter("receiverBankName",    residentBank.getName());
		message.addParameter("receiverAccount",     rusTaxPayment.getReceiverAccount());
		message.addParameter("ground",              rusTaxPayment.getGround());
	}
}

/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$

public class TaxPaymentSender extends RUSPaymentSender
{
	private static final String PARAMETER_PRIORITY_NAME = "priority";

	protected Remittee createReceiver(RUSPayment rusPayment) throws GateLogicException, GateException
	{
		Remittee receiver = super.createReceiver(rusPayment);
		RUSTaxPayment rusTaxPayment = (RUSTaxPayment) rusPayment;
		receiver.setKBK(rusTaxPayment.getTaxKBK());
		receiver.setTaxDocumentDate((rusTaxPayment.getTaxDocumentDate() != null)?rusTaxPayment.getTaxDocumentDate().getTime():null);
		receiver.setTaxDocumentNumber(rusTaxPayment.getTaxDocumentNumber());
		receiver.setTaxAssignment(rusTaxPayment.getTaxGround());
		receiver.setOKATO(rusTaxPayment.getTaxOKATO());
		receiver.setTaxPeriod(rusTaxPayment.getTaxPeriod());
		receiver.setPriority(Long.valueOf(getPaymentPriority()));
//TODO		receiver.setTaxAuthorState(getAttribute("tax-status"));
//TODO		receiver.setTaxPaymentType(getAttribute("tax-type"));
		/*
		 налоговые суммы устанавливаем равными 0. Ќужно дл€ правильного подсчета коммиссии 

		receiver.setSumASh(new BigDecimal(0.0000));
		receiver.setSumAV(new BigDecimal(0.0000));
		receiver.setSumISh(new BigDecimal(0.0000));
		receiver.setSumNS(new BigDecimal(0.0000));
		receiver.setSumPC(new BigDecimal(0.0000));
		receiver.setSumPE(new BigDecimal(0.0000));
		receiver.setSumSA(new BigDecimal(0.0000));
		receiver.setGround("п/п по за€влению клиента от " + DateHelper.toString(rusTaxPayment.getDateCreated().getTime()));
		return receiver;
	}

	private String getPaymentPriority()
	{
		return (String) getParameter(PARAMETER_PRIORITY_NAME);
	}
}
*/