package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.SWIFTPayment;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author Krenev //v620
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class SWIFTPaymentSender extends AbstractDocumentSender
{
	private static final String PARAMETER_OPERATION_TYPE = "operation-type";

	public SWIFTPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof SWIFTPayment))
			throw new GateException("Неверный тип платежа, должен быть - SWIFTPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("externalTransfer_q");
	}

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(message, gateDocument);
		if (!(gateDocument instanceof SWIFTPayment))
			throw new GateException("Неверный тип платежа, должен быть - SWIFTPayment.");

		SWIFTPayment CurrencyPaymentDocument = (SWIFTPayment) gateDocument;

		message.addParameter("chargeOffAccount",CurrencyPaymentDocument.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",CurrencyPaymentDocument.getChargeOffAmount().getDecimal().toString());

		message.addParameter("receiverName",CurrencyPaymentDocument.getReceiverName());
		message.addParameter("receiverBIC",CurrencyPaymentDocument.getReceiverSWIFT());
		//if (getParameter(PARAMETER_OPERATION_TYPE) != null)
		//	message.addParameter("type",getParameter(PARAMETER_OPERATION_TYPE));
		//message.addParameter("receiverCorAccount",CurrencyPaymentDocument.getReceiverCorAccount());
		message.addParameter("receiverCorAccount",0);
		message.addParameter("receiverAccount",CurrencyPaymentDocument.getReceiverAccount());
		message.addParameter("receiverBankName",CurrencyPaymentDocument.getReceiverBankName());
		message.addParameter("ground",CurrencyPaymentDocument.getGround());		
	}
}
/*
    public class SWIFTPaymentSender extends AbstractPaymentSender
	{
		private static final long BANK_CODE = 6L;
		private static final String PARAMETER_SUBOPERATION_TYPE_SPOT_NAME = "suboperation-type-spot";
		private static final String PARAMETER_SUBOPERATION_TYPE_TOM_NAME = "suboperation-type-tom";
		private static final String PARAMETER_SUBOPERATION_TYPE_TOD_NAME = "suboperation-type-tod";

		/**
		 * создать заявку
		 * @return заявка
		 *
		protected PaymentDemandBase createDemand()
		{
			return new ExpandedPaymentDemand();
		}

		protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
		{
			if (!(document instanceof SWIFTPayment))
				throw new GateException("Неверный тип платежа, должен быть - SWIFTPayment.");
			SWIFTPayment swiftPayment = (SWIFTPayment) document;


заполняем поля
	Account, ClientCode, DocumentDate, IsResident,
	CurrencyCode, IsCur, SummaOut,
	OperationType, OperationKind,

		super.fillDemand(demand, swiftPayment);

		Client client = getOwner(document);
		ExpandedPaymentDemand expandedPaymentDemand = (ExpandedPaymentDemand) demand;

//todo вынести Ground в gate-documents-config.xml
		expandedPaymentDemand.setGround("Списание средств");

		expandedPaymentDemand.setReceiverAccount(swiftPayment.getReceiverAccount());
//TODO		expandedPaymentDemand.setReceiverBic()

		Remittee receiver = new Remittee();
		receiver.setPayerInn(client.getINN());
		receiver.setBank(swiftPayment.getReceiverBankName());
		receiver.setBankCode(BANK_CODE);
		receiver.setReceiverName(swiftPayment.getReceiverName());
		expandedPaymentDemand.setReceiver(receiver);
		receiver.setGround(swiftPayment.getGround());
		expandedPaymentDemand.setDestination(receiver);
		if (swiftPayment.getConditions() == SWIFTPaymentConditions.tod)
			expandedPaymentDemand.setApplType(Long.valueOf(getCurrencyTransferSuboperationTOD()));
		if (swiftPayment.getConditions() == SWIFTPaymentConditions.tom)
			expandedPaymentDemand.setApplType(Long.valueOf(getCurrencyTransferSuboperationTOM()));
		if (swiftPayment.getConditions() == SWIFTPaymentConditions.spot)
			expandedPaymentDemand.setApplType(Long.valueOf(getCurrencyTransferSuboperationSPOT()));
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("не реализовано");
	}
	private String getCurrencyTransferSuboperationSPOT()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_SPOT_NAME);
	}

	private String getCurrencyTransferSuboperationTOM()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOM_NAME);
	}

	private String getCurrencyTransferSuboperationTOD()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOD_NAME);
	}
}*/
