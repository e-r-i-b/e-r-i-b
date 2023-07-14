package com.rssl.phizicgate.cpfl.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.cpfl.messaging.RequestHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 * ������ ������������ ������� AccountPaymentSystemPayment � ����
 */
public class BillingPaymentSender extends AbstractDocumentSenderBase
{
	private RequestHelper requestHelper;

	public BillingPaymentSender(GateFactory factory)
	{
		super(factory);
		requestHelper = new RequestHelper(factory);
	}

	/**
	 * ����������� ��������
	 * @param document ��������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document == null)
		{
			throw new GateException("�� ������� �������� ��� ����������");
		}
		if (!AbstractPaymentSystemPayment.class.isAssignableFrom(document.getType()))
			throw new GateException("�������� ��� ������� - ��������� ��������� AbstractPaymentSystemPayment");

		AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) document;

		//��� ���� ������ � ������� ������ ������ ��� ����� ���������� ������� � ����.
		//������� ������ ������� ������������ �� �������� ����
		boolean isLongOffer = payment.getType() == CardPaymentSystemPaymentLongOffer.class;
		String idFromPaymentSystem = RequestHelper.getHiddenIdFromPaymentSystem(payment);
		if (isLongOffer && idFromPaymentSystem != null)
		{
			payment.setIdFromPaymentSystem(idFromPaymentSystem);
			return;
		}

		//��������� � ���������� ������ �� ���������� �������
		Document responce = sendPrepareMessage(payment);
		// ������������ �����.
		processPrepeaResponce(responce, payment);
	}

	/**
	 * ��������� � ������� ��������� �� ���������� �������
	 * @param payment ������, ������� ����� �����������
	 * @return ����� �� ������ ���������� �������
	 */
	private Document sendPrepareMessage(AbstractPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		boolean isLongOffer = payment.getType() == CardPaymentSystemPaymentLongOffer.class;
		String receiverUID = payment.getReceiverPointCode();
		if (StringHelper.isEmpty(receiverUID))
		{
			throw new GateException("������������ �������� " + payment.getId() + ": �� ����� ���������� ��� ������");
		}
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		//��������� ���������
		GateMessage gateMessage = service.createRequest("preparePaymentDemand_q");
		gateMessage.addParameter("clientId", "0");//������������ ����������� - ������ 0
		if (isLongOffer)
		{
			gateMessage.addParameter("isForm190", "true");
		}
		if (!CPFLBillingPaymentHelper.isContractual(receiverUID))
		{
			String account = payment.getReceiverAccount();
			gateMessage.addParameter("account", account);//���� �������������
			gateMessage.addParameter("inn", payment.getReceiverINN());//��� �������������
			ResidentBank bank = payment.getReceiverBank();
			gateMessage.addParameter("bankBIC", bank.getBIC());//��� ����� �������������
			if (!StringHelper.isEmpty(bank.getAccount()))
			{
				gateMessage.addParameter("bankAccount", bank.getAccount());
			}
			else if (account.startsWith("40101"))
			{
				gateMessage.addParameter("bankAccount", "0");//��� ��������� ����������� ��������� 0 ��� ������������� ���������
			}
		}
		else
		{
			gateMessage.addParameter("uniqueNumber", receiverUID);//������������ ����������
		}
		//��� �������� � � ������ ��������� ����� ���������� "0.00" � ���� �����
		gateMessage.addParameter("sum", payment.isTemplate() || payment.getDestinationAmount() == null ? "0.00" : payment.getDestinationAmount().getDecimal());
		gateMessage.addParameter("isShablon", payment.isTemplate());
		//��������� ���� � ��� �����.
		Document request = gateMessage.getDocument();
		Element requestRoot = request.getDocumentElement();

		//��������� ������ � ���� �������(�����) �� �������(���� ����).
		List<Document> specialClient = requestHelper.getSpecialClient(payment);
		if (specialClient != null)
		{
			for (Document node : specialClient)
			{
				requestRoot.appendChild(request.importNode(node.getDocumentElement(), true));
			}
		}
		//��������� ��������� ��� �������� �������� �� �������� ���� ���������(���� ����).
		Document nplatRequisites = CPFLBillingPaymentHelper.getNplatRequisites(payment);
		if (nplatRequisites != null)
		{
			requestRoot.appendChild(request.importNode(nplatRequisites.getDocumentElement(), true));
		}
		//��������
		return service.sendOnlineMessage(gateMessage, null);
	}

	/**
	 * ���������� ����� �� ������ ���������� ���������.
	 * @param responce �����
	 * @param payment ������, ��� ���������� ��������� �������(����� ����, ������� ������������....).
	 */
	private void processPrepeaResponce(Document responce, AbstractPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		boolean isLongOffer = payment.getType() == CardPaymentSystemPaymentLongOffer.class;
		Element node = responce.getDocumentElement();
		try
		{
			//�������� specialClient �������� ���������� specialClient � ������� ���� ��� ����������� �������� �� ���������� �������.
			NodeList specialClientNodes = XmlHelper.selectNodeList(node, "specialClient");
			RequestHelper.updateDocumentSpecalClientInfo(payment, specialClientNodes);

			//�������� ��������� ��� �������� ��������(nplatRequisites). ������ ���� ������ ������������
			// ��� ��������� � ����������� ��� ������ ������� �� ���������� �������.
			// ������������ ������ ��� ����� ������� � ������� �� ���������� ������� � ���.
			CPFLBillingPaymentHelper.updateDocumentNplatRequisitesInfo(payment, XmlHelper.selectSingleNode(node, "nplatRequisites"));

			List<Field> documentExtendedFields = payment.getExtendedFields();
			List<Field> newFields = new ArrayList<Field>(documentExtendedFields);

			//��� ������������ �����������
			Field receiverNameField = BillingPaymentHelper.getFieldById(payment, CPFLBillingPaymentHelper.RECEIVER_NAME_FIELD_NAME);
			if (!CPFLBillingPaymentHelper.isContractual(payment.getReceiverPointCode()))
			{
				if (receiverNameField == null)
				{
					//������������ ���������� ������� ������ ������, ��������� ��� ��� ���. ����
					receiverNameField = CPFLBillingPaymentHelper.createReceiverNameField();
					newFields.add(receiverNameField);
				}
				if (!StringHelper.isEmpty(payment.getReceiverName()))
				{
					//��� �������������� ������� ���������� ��� ���������� ������� � ��� ����, ����� ������������ ��� ��� �������������.
					((CommonField)receiverNameField).setDefaultValue(payment.getReceiverName());
					payment.setReceiverName(null);
				}
			}

			//���� ���� paymentType � ��� ��������
			Document nplatRequisite = CPFLBillingPaymentHelper.getNplatRequisites(payment);
			String paymentTypeValue = XmlHelper.getSimpleElementValue(nplatRequisite.getDocumentElement(),"paymentType");
			//������� ��� ���� �� ��������� specialClient
			List<Field> responceFields = requestHelper.parseSpecialClients(specialClientNodes, paymentTypeValue);

			//��������� ������ ����� �����.
			for (Field responceField : responceFields)
			{
				if (!CPFLBillingPaymentHelper.hasField(payment, responceField))
				{
					//���� ���� � ������� �� ���� - ��������� � ������ ����� �����
					newFields.add(responceField);
				}
			}

			//�������. ���� �� � ������� ���� ��� ������� �����
			Field amountField = BillingPaymentHelper.getMainSumField(payment);
			if (amountField == null)
			{
				//���� ��� - ������� � ��������� � ������ ����� �����.
				newFields.add(BillingPaymentHelper.createAmountField());
			}

			//�������. ���� �� ��������
			String commission = XmlHelper.getSimpleElementValue(node, "commision");
			if (commission != null && StringHelper.isEmpty(XmlHelper.getElementValueByPath(node, "nplatRequisites/specialClientCodeOrg")))
			{
				throw new GateLogicException("� ����� ������� ���������� ������� �������� �� �����������. ��������� ������� �����."); 
			}
			if (commission != null && (payment.isTemplate() || payment.getDestinationAmount() != null)) //����� ��������� ��� ������ � �������� ����. ���������� ��������� ���������.
			{
				if (!payment.isTemplate())// � �������� �������� �� �������������
				{
					//������ �� ��������
					payment.setCommission(new Money(new BigDecimal(commission), payment.getDestinationAmount().getCurrency()));
				}
				String billingDocumentId = XmlHelper.getSimpleElementValue(node, "messageId");
				XmlHelper.getSimpleElementValue(node, "messageId");

				boolean needClientData = false; // ������� ����, ��� ��������� ��� ������ �� �������
				if (isLongOffer)
				{
					//��� ���������� ��������� ������(���� ���� ��������) ������ ��� ���� � �������.
					needClientData = processAckClientBankCanChangeSumm(newFields, responce) | processTariff(newFields, responce);
					newFields.add(CPFLBillingPaymentHelper.createHiddenCommissionLabel(XmlHelper.getSimpleElementValue(node, "commisionLabel")));
					newFields.add(CPFLBillingPaymentHelper.createHiddenServiceCode(XmlHelper.getSimpleElementValue(node, "serviceCode")));
				}

				if (needClientData)
				{
					//������ ������ ��� ���-�� �������. ����� �������� - ������� ������������ ��������� �������� � ��� ����.
					//��� ����������� ������� �� ������� �� �� ����� ��������� � ����, � ������ �������� IdFromPaymentSystem.
					newFields.add(RequestHelper.createHiddenIdFromPaymentSystem(billingDocumentId));
				}
				else
				{
					//�� ������ ����� ����������� ��������� �������� � �������� ��������� �������, ���. ���� �������
					if (receiverNameField != null && !CPFLBillingPaymentHelper.isContractual(payment.getReceiverPointCode()))
					{
						payment.setReceiverName((String) (StringHelper.isEmpty((String)receiverNameField.getValue()) ? receiverNameField.getDefaultValue() : receiverNameField.getValue()));
						newFields.remove(receiverNameField);
					}
					//������������� ������� ������������� ���������
					payment.setIdFromPaymentSystem(billingDocumentId);
				}
			}
			//��������� ���� � �������
			payment.setExtendedFields(newFields);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private boolean processTariff(List<Field> newFields, Document responce) throws GateException
	{
		List<Field> fields = CPFLBillingPaymentHelper.createTariffFields(responce);
		if (fields == null)
		{
			return false;
		}
		newFields.addAll(fields);
		return true;
	}

	/**
	 * ���������� ������� AckClientBankCanChangeSumm.
	 * @param fields - ������ ����� � ������� ��������� �������� ����� ����.
	 * @param responce - ����� ����
	 * @return ������� ��������� �� ����� ������� ��� ����� ����.
	 */
	private boolean processAckClientBankCanChangeSumm(List<Field> fields, Document responce) throws GateException
	{
		boolean ackClientBankCanChangeSumm = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(responce.getDocumentElement(), "ackClientBankCanChangeSumm"));
		if (ackClientBankCanChangeSumm)
		{
			fields.add(CPFLBillingPaymentHelper.createAckClientBankCanChangeSumm());
		}
		return ackClientBankCanChangeSumm;
	}

	/**
	 * ������� ��������
	 * @param document ��������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		//� ���� ���������� ������ ���������� ���������(prepea). ������� �������������� � ���
	}

	/**
	 * �������� ��������
	 * @param document ��������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		//������ � ���� ���.
	}

	/**
	 * ����������� ������
	 * @param document ������ ��� �������������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//������������� � ���� ���
		//BUG028971.�������� �������� unp ��� ����, �� ����� ���������� � ����.
		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;
		payment.setIdFromPaymentSystem(null);
	}

	/**
	 * ��������� �������
	 * @param document  ������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//��������� ���������� �� ����� ���������� �������(prepea)
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}
}
