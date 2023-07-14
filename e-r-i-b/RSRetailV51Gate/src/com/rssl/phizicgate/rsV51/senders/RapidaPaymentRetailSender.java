package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.ErrorProcessor;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.rsV51.demand.PaymentDemandBase;
import com.rssl.phizicgate.rsV51.demand.Remittee;
import com.rssl.phizicgate.rsV51.demand.ExpandedPaymentDemand;

/**
 * @author Krenev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */
//������ 
public class RapidaPaymentRetailSender extends AbstractPaymentSender
{
	private static final String CODE_TAG_NAME = "code";
	private static final String RESULT_OK = "0";
	private static final String PARAMETER_SUBOPERATION_TYPE = "retail-suboperation-type";
	private static final ErrorProcessor errorProcessor = new ErrorProcessor(ErrorSystem.Retail);
	private static final String PARAMETER_RECEIVER_CORRACCOUNT = "receiver-corraccount";
	private static final String PARAMETER_RECEIVER_BIK = "receiver-bic";
	private static final String PARAMETER_RECEIVER_NAME = "receiver-name";
	private static final String PARAMETER_RECEIVER_BANK_NAME = "receiver-bank-name";

	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{
/*
��������� ����
	Account, ClientCode, DocumentDate, IsResident,
	CurrencyCode, IsCur, SummaOut,
	OperationType, OperationKind,
*/
		super.fillDemand(demand, document);
//todo ������� Ground � gate-documents-config.xml

		if(!(demand instanceof ExpandedPaymentDemand))
			throw new  GateException("�������� PaymentDemand");
		AccountPaymentSystemPayment PaymentSystemPayment = (AccountPaymentSystemPayment)document;
		if(!(document instanceof AccountPaymentSystemPayment))
			throw new  GateException("�������� PaymentSystemPayment");

		ExpandedPaymentDemand paymentDemand = (ExpandedPaymentDemand) demand;
		paymentDemand.setReceiverAccount(PaymentSystemPayment.getReceiverAccount());
		paymentDemand.setReceiverCorAccount((String) getParameter(PARAMETER_RECEIVER_CORRACCOUNT));
		paymentDemand.setReceiverBic((String) getParameter(PARAMETER_RECEIVER_BIK));
		
		paymentDemand.setApplType(Long.parseLong((String)getParameter(PARAMETER_SUBOPERATION_TYPE)));

		Remittee receiver = new Remittee();
		receiver.setApplicationKey(paymentDemand.getApplicationKey());
		receiver.setApplicationKind(paymentDemand.getApplicationKind());
		String destination = PaymentSystemPayment.getGround();
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
			throw new GateException("�������� ��� ��������� " + document.getClass().getReceiverSurName() + ". ��������� SynchronizableDocument");
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
*/
}
