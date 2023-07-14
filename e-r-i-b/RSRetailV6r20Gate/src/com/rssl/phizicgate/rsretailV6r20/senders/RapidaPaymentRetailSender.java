package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.ErrorProcessor;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author //v620
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */
//������ 
public class RapidaPaymentRetailSender extends AbstractDocumentSender
{
	//private static final String CODE_TAG_NAME = "code";
	//private static final String RESULT_OK = "0";
	private static final String PARAMETER_RECEIVER_ACCOUNT = "receiver-account";
	private static final String PARAMETER_OPERATION_TYPE = "retail-operation-type";	
	private static final String PARAMETER_SUBOPERATION_TYPE = "retail-suboperation-type";
	private static final ErrorProcessor errorProcessor = new ErrorProcessor(ErrorSystem.Retail);
	private static final String PARAMETER_RECEIVER_CORRACCOUNT = "receiver-corraccount";
	private static final String PARAMETER_RECEIVER_BIK = "receiver-bic";
	private static final String PARAMETER_RECEIVER_NAME = "receiver-name";
	private static final String PARAMETER_RECEIVER_BANK_NAME = "receiver-bank-name";

	public RapidaPaymentRetailSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateLogicException, GateException
	{
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("�������� ��� �������, ������ ���� - AccountPaymentSystemPayment.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("externalTransfer_q");
	}

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(message, gateDocument);
		if (!(gateDocument instanceof AccountPaymentSystemPayment))
			throw new GateException("�������� ��� �������, ������ ���� - PaymentSystemPayment.");

		AccountPaymentSystemPayment RapidaPaymentDocument = (AccountPaymentSystemPayment) gateDocument;

		message.addParameter("chargeOffAccount",RapidaPaymentDocument.getChargeOffAccount());
		Element chargeOffAmount = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmount, "value",RapidaPaymentDocument.getChargeOffAmount().getDecimal().toString());
		
		message.addParameter("receiverAccount",getParameter(PARAMETER_RECEIVER_ACCOUNT));
		message.addParameter("receiverCorAccount",getParameter(PARAMETER_RECEIVER_CORRACCOUNT));
		message.addParameter("receiverBIC",getParameter(PARAMETER_RECEIVER_BIK));
		message.addParameter("receiverName",getParameter(PARAMETER_RECEIVER_NAME));
		message.addParameter("receiverBankName",getParameter(PARAMETER_RECEIVER_BANK_NAME));
		//message.addParameter("type",getParameter(PARAMETER_OPERATION_TYPE));
		//message.addParameter("type",67);
		message.addParameter("subType",getParameter(PARAMETER_SUBOPERATION_TYPE));
		message.addParameter("ground",RapidaPaymentDocument.getGround());

	    //RapidaPaymentDocument.getExtendedFields();
	}
}
/*
������ �� V51 - ������
	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{

��������� ����
	Account, ClientCode, DocumentDate, IsResident,
	CurrencyCode, IsCur, SummaOut,
	OperationType, OperationKind,

		super.fillDemand(demand, document);
//todo ������� Ground � gate-documents-config.xml

		if(!(demand instanceof ExpandedPaymentDemand))
			throw new  GateException("�������� PaymentDemand");
		PaymentSystemPayment PaymentSystemPayment = (PaymentSystemPayment)document;
		if(!(document instanceof PaymentSystemPayment))
			throw new  GateException("�������� PaymentSystemPayment");

		ExpandedPaymentDemand paymentDemand = (ExpandedPaymentDemand) demand;
		paymentDemand.setReceiverAccount(PaymentSystemPayment.getReceiverAccount());
		paymentDemand.setReceiverCorAccount((String) getParameter(PARAMETER_RECEIVER_CORRACCOUNT));
		paymentDemand.setReceiverBic((String) getParameter(PARAMETER_RECEIVER_BIK));
		
		paymentDemand.setApplType(Long.parseLong((String)getParameter(PARAMETER_SUBOPERATION_TYPE)));

		Remittee receiver = new Remittee();
		receiver.setApplicationKey(paymentDemand.getApplicationKey());
		receiver.setApplicationKind(paymentDemand.getApplicationKind());
		String destination = PaymentSystemPayment.getDestination();
		if(destination != null && destination.indexOf("!@date@!") != -1)
		{
			String curDate = DateHelper.toString(DateHelper.getCurrentDate().getTime());
			destination = destination.replace("!@date@!",curDate);
		}
		receiver.setGround(destination);
		receiver.setReceiverName((String) getParameter(PARAMETER_RECEIVER_NAME));
		receiver.setBank((String) getParameter(PARAMETER_RECEIVER_BANK_NAME));

		paymentDemand.setGround(PaymentSystemPayment.getGround());
		receiver.setAttributID("����_����");
		paymentDemand.setReceiver(receiver);
		paymentDemand.setReceiver(receiver);
		paymentDemand.setDestination(receiver);
	}

	protected PaymentDemandBase createDemand()
	{
		return new ExpandedPaymentDemand();
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("�� �����������");
	}

	/**
	 * ������� ��������
	 * @param document ��������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
/*TODO ���������������� ����� ����������� ������ � ������� (90871 � ������� �������)
	public void send(GateDocument document) throws GateLogicException, GateException
	{
		if (!(document instanceof SynchronizableDocument))
		{
			throw new GateException("�������� ��� ��������� " + document.getClass().getName() + ". ��������� SynchronizableDocument");
		}
		super.send(document);

		SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
		Document response = documentService.transact(synchronizableDocument.getExternalId());
		Element root = response.getDocumentElement();
		String result = XmlHelper.getSimpleElementValue(root, CODE_TAG_NAME);
		if (!RESULT_OK.equals(result))
		{
			rollbackDelayed(synchronizableDocument.getExternalId());
			errorProcessor.setDocument(document);
			errorProcessor.parseError(result);
		}
		else
		{
			synchronizableDocument.setState(new State(StateCategory.processed, "Z"));//"�� ����������"
		}
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		//������� ������ �� �����������
		documentService.removeTransact(document.getWithdrawExternalId());
	}
	private void rollbackDelayed(String externalId) throws GateException
	{
		PaymentDemandBase demand = documentService.findById(externalId);
		if (demand == null)
		{
			throw new GateException("�������� �� ������ � ������ ���������� ID=" + externalId);
		}
		documentService.remove(demand);
	}
}
*/
