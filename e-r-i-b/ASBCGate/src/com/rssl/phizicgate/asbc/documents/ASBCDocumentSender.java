package com.rssl.phizicgate.asbc.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.asbc.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import javax.xml.transform.TransformerException;

/**
 * @author Krenev
 * @ created 01.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCDocumentSender extends AbstractService implements DocumentSender
{
	private Map<String, ?> parameters = new HashMap<String, Object>();
	private static final int MAX_LENGTH_ADDITIONAL_REQUISITES = 256; //����������� ��������� ����� ���� additionalRequisites
	private static final String DELIMITER = "@";                     //����������� �������� �������������� ����������

	public ASBCDocumentSender(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		parameters = params;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	/**
	 * ��� ������ ���������� ���� ����� 4 ��� ����:
	 * 1) ������� ����. ������ ����� � ��.
	 * 2) transactionId - ������� ����. �������� �������� � findCredits
	 * 3) �������������
	 * 4) �����
	 * ������� ���������� ��������� �������� �� �������.
	 * 1) ������ ������ ������� ����.
	 * 2) ���������� ������ findCredits, ���������� ������������ ���������� � ������� ���� �������.
	 * 3) � ������ ����� ������ 1 ��� ��������� ��������� result_element � ����������� � ���������� � �������������
	 * 4) ������������ �� ����������� ����������� ���� �������� "������������ �������������" � "������ ���������� ������".
	 * 5) ���� ������ ���� ������� result_element (1 �������������), ��
	 *     a) ���� � ���������� ��������� ������� "������ ���������� ������", �� � ��������� ����������� ��������� ��������� ���������� (����, ����, ���, ���) ���������� �� ������
	 *     b) ������������ ��������� ����: ������� ���� � ����� ������������� (creditId), ��������������� ���� ������������� �� ��������� ������������� (���� � ���������� ��������� ������� "������������ �������������", ��� ������� ������� ���� ������������� �� ���������),
	 *        ������������� ���� ����� � ���������������� ��������� ������ ������� �������������(��� ������������� �������������) � ������� ���� �� ��������� transactionId
	 *     �) ���� ������������� ������� � ������� ���������� �������������
	 * 6) ���� ������ ��������� ��������� result_element (��������� ��������������), ��
	 *     a) ���� � ���������� �� ��������� ������� "������������ �������������", �� �������� ����������, �� ���������� ������� ������������� ����������
	 *     b) ������������ ���� ���������� ������ � ������� ������������� ��� ������������ �������. (��� ������� ������� ����� ������ ������������� �� ������, ���� ����� ������������� �� ���������)
	 *     �) ����� ������ ������������� �������� ���������� ������ ��� ���������� ���������.
	 *          1. ���������� ��������� ������ findCredits, ���������� ������������ ���������� � ������� ���� �������.
	 *          2. � ������ ��������� ������ result_element, ��������������� ��������� �������������(����������� �� ���� creditId)
	 *          3. ���� � ���������� ��������� ������� "������ ���������� ������", �� � ��������� ����������� ��������� ��������� ���������� (����, ����, ���, ���) ���������� �� ��������� ������
	 *          4. ������������  ������������� ���� ����� � ���������������� ��������� ������ ������� �������������(��� ������������� �������������) � ������� ���� �� ��������� transactionId
	 *          5. ���� ������������� ������� � ������� ���������� �������������
	 * @param document ��������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//��������� ���� � ��������� ���������� �� ������ �� ������ �������������
		if (!(document instanceof AccountPaymentSystemPayment))
			throw new GateException("��������� AccountPaymentSystemPayment");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;

		try
		{
			List<Field> extendedFields = payment.getExtendedFields();

			ASBCRequestHelper requestHepler = new ASBCRequestHelper(getFactory());
			//�������� �������� ����.
			Field keyField = ASBCRequestHelper.findKeyFields(payment);
			if (keyField == null)
			{
				extendedFields.add(requestHepler.createKeyField());// ��������� �������� ���� � ������
				return;//��� ��������� ���� ������ ������, ���� ����� ������ ��� ������.
			}
			else
			{
				//�������� ���� ������ ���������������
				((CommonField) keyField).setEditable(false);
			}
			//������ ����� ������ ���� �� ���� transactionId. ��� ������� ������� � ���, ��� ������ ������ ������������� ��� ������������� 1.
			Field transactionId = BillingPaymentHelper.getFieldById(extendedFields, Constants.TRANSACTIONAL_ID_FIELD_NAME);
			if (transactionId != null)
			{
				//transactionId ���� ������ ��� ����������..
				BillingPaymentHelper.prepareFields(payment);
				//� ������� �� ���������� ������� ���� �������������
				if (payment.isTemplate())
				{
					//���� ������������� ����� ���� ��� �������, ��� � ���������
					Field debtField = BillingPaymentHelper.getFieldById(extendedFields, Constants.DISPLAY_DEBT_FIELD_NAME);
					if (debtField == null)
					{
						debtField = BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
					}
					extendedFields.remove(debtField);
				}

				payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
				return;//����������� ������� ����������.
			}

			//���� � ����������� ���. ������ ���� �� �������� �������������, ���� ��������, �� ������ ���������.
			//������ findCredits � ����� ������ �����.
			Document result = requestHepler.makeFindCreditsRequest(payment.getReceiverPointCode(), ASBCRequestHelper.findKeyFields(payment));
			//������� ���� �� ���� �������������
			Field debtField = BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
			if (debtField != null)
			{
				//������������� � ��������� ��� ����. ������ ��� ������ ���� ��������� ����� � ������ ������ ��������.. �������� ��� �������� � ���� ������ ������� � ������
				Element resultElement = getResultElement(result, (String) debtField.getValue());//����� ������ result_element, �������������� ��������� �������������...
				// ������� ���� TransactionId
				extendedFields.add(requestHepler.createTransactionIdField(XmlHelper.getSimpleElementValue(resultElement, Constants.TRANSACTIONAL_ID_FIELD_NAME)));
				// ������� ���� ������� �����
				extendedFields.add(requestHepler.createAmountField(requestHepler.getDebt(resultElement)));
				//���������, ���� ����� ��������� ����������
				refreshRecipientInfo(payment, resultElement);
				//������ ������������� ���������������
				((CommonField) debtField).setEditable(false);
				//��������� �������� �����
				requestHepler.updateFieldsValue(payment, result);
				return; //������ ������ ������. ����� ������ ����������� � ������ ��� ������ ����
			}
			//������������� ������ ��� � ���������... ����� ��������� � ��������...
			//�� ��� ������ ������� ���� �� ������� � ���������
			BusinessRecipientInfo businessRecipientInfo = getBusinessResipietnInfo(payment);
			NodeList nodeList = XmlHelper.selectNodeList(result.getDocumentElement(), "//result/result_element");//������� ��� �������������� ���������?
			if (nodeList.getLength() == 1) //1 �������������... ����� ���� �������������, ����� � ������� ���� ��� ����������
			{
				Element element = (Element) nodeList.item(0);
				// ������� ���� TransactionId
				extendedFields.add(requestHepler.createTransactionIdField(XmlHelper.getSimpleElementValue(element, Constants.TRANSACTIONAL_ID_FIELD_NAME)));
				//��������� �������������
				BigDecimal debt = requestHepler.getDebt(element);
				//���� � ����� ������������� ������� ������
				extendedFields.add(requestHepler.createHiddenDeptField(XmlHelper.getSimpleElementValue(element, "creditId")));
				//�� � ������ ������������� ���������, �� ���� ���������� � ����������� �� ��������
				//��� ������� ������� ���� ������������� �� ���������
				if (businessRecipientInfo.isDeptAvailable() && !payment.isTemplate())
				{
					extendedFields.add(requestHepler.createSingleDebtField(debt));
				}
				//������� ����� , � ������ ���� ������������ ���������������
				extendedFields.add(requestHepler.createAmountField(businessRecipientInfo.isDeptAvailable() && (BigDecimal.ZERO.compareTo(debt) == -1) ? debt : null));
				//���������, ���� ����� ��������� ����������
				refreshRecipientInfo(payment, element);
				//��������� �������� �����
				requestHepler.updateFieldsValue(payment, result);
				return; //������ ������ ������. ����� ������ ����������� � ������ ��� ������ ����
			}
			//������ ��������� ��������������
			if (!businessRecipientInfo.isDeptAvailable())
			{
				//���� �� �������������� ��������� �������������, � ������ ��������� ����� ������, ������� �� ������ �� �����..
				throw new GateLogicException("� ������ ������ �� �� ������ �������� ������ ��������� �����������. ��������� �������� �����.");
			}

			//��� �������: ������������� ������� ������ ������� ������������ �������������
			if (!payment.isTemplate())
			{
				//������� ������ ���� (���������� ������) �������������
				extendedFields.add(requestHepler.createDebtListField(nodeList));
				//���.
			}
			else
			//��� ������� �������: ����� ������ �������� �������������
			{
				Element element = (Element) nodeList.item(0);
				// ������� ���� TransactionId
				extendedFields.add(requestHepler.createTransactionIdField(XmlHelper.getSimpleElementValue(element, Constants.TRANSACTIONAL_ID_FIELD_NAME)));
				//��������� �������������
				BigDecimal debt = requestHepler.getDebt(element);
				//���� � ����� ������������� ������� ������
				extendedFields.add(requestHepler.createHiddenDeptField(XmlHelper.getSimpleElementValue(element, "creditId")));
				//������� �����
				extendedFields.add(requestHepler.createAmountField(businessRecipientInfo.isDeptAvailable() && (BigDecimal.ZERO.compareTo(debt) == -1) ? debt : null));
				//���������, ���� ����� ��������� ����������
				refreshRecipientInfo(payment, element);
				//��������� �������� �����
				requestHepler.updateFieldsValue(payment, result);
			}
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

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
//		GateMessage message = serviceFacade.createRequest("reversePayment");
		//....TODO
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
		{
			throw new GateException("��������� AccountPaymentSystemPayment");
		}
		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;

		try
		{
			Document result = makeMakePaymentRequest(payment);
			String externalId = XmlHelper.getElementText((Element)result.getElementsByTagName("makePayment").item(0));
			payment.setIdFromPaymentSystem(externalId);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	private Document makeMakePaymentRequest(AccountPaymentSystemPayment payment) throws GateLogicException, GateException, DocumentException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		String receiverId = payment.getReceiverPointCode();
		ResidentBank receiverBank = payment.getReceiverBank();
		List<Field> extendedFields = payment.getExtendedFields();

		//�������� �������� ���� ���������� �������
		Date executionDate = DateHelper.toDate(payment.getExecutionDate());

		//�������� �������� additionalsRequisites
		StringBuilder additionalsRequisites = new StringBuilder();
		for (Field field : extendedFields)
		{
			Object value = field.getValue();
			String additionalParameter = StringHelper.getEmptyIfNull(value);
			//���� ����� ���� additionalsRequisites ������ 256, �� ���� ������ ������, ������� �������� ������ �� ���������� �����������
			if (additionalsRequisites.length() + additionalParameter.length() >= MAX_LENGTH_ADDITIONAL_REQUISITES)
			{
				break;
			}
			additionalsRequisites.append(additionalParameter);
			additionalsRequisites.append(DELIMITER);
		}

		GateMessage message = serviceFacade.createRequest("makePayment");
		Field creditId = BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
		message.addParameter("creditId", creditId.getValue());  // ������������� ����������	 ���� ����� �������� �� ������ �� ��ֻ �� ������ FindCreditsExt (�������� �������������)
		message.addParameter("paymentDate", DateHelper.toXMLDateFormat(executionDate));//���� ���������� ������� � ��� ����� � ������� yyyy-mm-dd
		message.addParameter("paymentTime", paymentTimeFormat(executionDate));//����� ���������� ������� � ��� ����� � ������� hh:mm:ss

		BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
		Office office = backRefBankrollService.getAccountOffice(payment.getInternalOwnerId(), payment.getChargeOffAccount());

		message.addParameter("osb", office.getCode().getFields().get("branch"));//���������, ��������� ������	����� ���, ��� ������ ���� �����������
		message.addParameter("filial", "88888");//������, ��������� ������	���� �������� �������� �88888�. ������������ �������� � ���� ���� ������ ���� ����� ��, ��� ��� �������� ���.
		message.addParameter("paymentType", "8");//��� �������	��� �������. ������ 8 - ������ ������ �� ������� ����/�������� ���@��
		message.addParameter("docNumber", payment.getDocumentNumber());  //����� ���������� ���������	����� ��������� � ����
		message.addParameter("sum", payment.getDestinationAmount().getAsCents());//���� �������� ��������� �������� ����� ������� � ����� �������� � ������� �� ���������� �������
		message.addParameter("commission", "0"); //�������� � ����������	���� �������� �������� = 0.
		message.addParameter("payerCommission", "0"); //R������� � ������� �� ���������� �������	�������� � ������� �� ���������� �������. ���� �������� ����, ��� ��� �� �� ��ֻ �� ������������ �������� �� ������� �������.
		message.addParameter("sumPu", payment.getDestinationAmount().getAsCents()); //����� ���������� �� ������	���� ����������� �������� ����� ������� �� ������� ����� �������� �� ���������� ������� �������	/**
		//message.addParameter("sumSt", "??"); //����� ���������, ���� �� ������������.	���� ������ �������� �� ��������
		message.addParameter("additionalsRequisites", additionalsRequisites); //�������������� ��������� �������     ������� ���������� ��������� � ����������� ��������� � �������� ���������� � ������������, ������������ ��� �������� �������������� ���������� ��� ���. ������������ �������� �������������� ���������� � ��������� �������� ������ �������� ������ �@�. � ������� �� ��� � ������ ���� ���������� ������ �������� ��������� � �����������.
		message.addParameter("cashierId", "88888"); //����� �������	� �� ��ֻ ��� ���� �� ������������, �� �� ������������� ������� ���������� �������� ���� ������� �� ������ �������. ���� ������ �������� �������� �88888�.
		message.addParameter("serviceId", "0"); //������������� ������ � �� ��ֻ	���� �������� �������� �0�.
		message.addParameter("bic", receiverBank.getBIC()); //��� ����� ����������
		message.addParameter("correspondentAccount", StringHelper.isEmpty(receiverBank.getAccount()) ? "0" : receiverBank.getAccount()); //������� ����� ����������
		message.addParameter("settlementAccount", payment.getReceiverAccount()); //��������� ���� ����������
		message.addParameter("inn", payment.getReceiverINN()); //���� ������ �������� ��� ����������
		if (!StringHelper.isEmpty(payment.getReceiverKPP()))
		{
			message.addParameter("kpp", payment.getReceiverKPP()); //���� ���� ��������, �� ��� ����������, ����� ������.
		}
		//message.addParameter("kbk", "??"); //��� ��������� �������������	���� ���� �������� ��� ��������� �������������, ����� ������
		//message.addParameter("okato", "??"); //��� �� �����	���� ���� ��������, �� �����, ����� ������.
		message.addParameter("providerId", receiverId); //������������� ���������� �����	����� ���������� ����� � ����������� �������

		String transactionId = (String) BillingPaymentHelper.getFieldById(extendedFields, Constants.TRANSACTIONAL_ID_FIELD_NAME).getValue();
		if (!StringHelper.isEmpty(transactionId))
		{
			message.addParameter("transactionId", transactionId); //������������� ����������	���� �������� �������� �� ������ �� ��ֻ ������ FindCreditsExt. �� ��ֻ ��������� ������������� ���������� ��� �������������� � ������������ ����� � ������ on-line.
		}
		//message.addParameter("providerInfo", "??"); //������������ ���������� �������	������������ ���������� �������. ���� �������� �� ��������

		return serviceFacade.sendOnlineMessage(message, null);
	}

	private String paymentTimeFormat(Date date)
	{
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	/**
	 * ��������� ��������� ���������� �������, ���� � ���� ����������� �������� "������ ���������� ������"
	 * @param payment ������
	 * @param result ������ result_element, � ����� � ����������.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private void refreshRecipientInfo(AccountPaymentSystemPayment payment, Element result) throws GateException, GateLogicException
	{
		BusinessRecipientInfo businessRecipientInfo = getBusinessResipietnInfo(payment);

		if (!businessRecipientInfo.isPropsOnline())
		{
			return; //��������� ��������� �� ���������.
		}

		BackRefBankInfoService refBankInfoService = getFactory().service(BackRefBankInfoService.class);
		ResidentBank residentBank = refBankInfoService.findByBIC(XmlHelper.getSimpleElementValue(result, "bic"));
		//��������� ������, ������� � ��� ���
		if (residentBank == null)
			throw new GateLogicException("� ������ ������ �� �� ������ �������� ������ ��������� �����������. ��������� �������� �����.");

		payment.setReceiverBank(residentBank);
		payment.setReceiverAccount(XmlHelper.getSimpleElementValue(result, "settlementAccount"));
		payment.setReceiverINN(XmlHelper.getSimpleElementValue(result, "inn"));
		payment.setReceiverKPP(XmlHelper.getSimpleElementValue(result, "kpp"));
	}

	private BusinessRecipientInfo getBusinessResipietnInfo(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		BackRefReceiverInfoService receiverInfoService = getFactory().service(BackRefReceiverInfoService.class);
		BusinessRecipientInfo businessRecipientInfo = receiverInfoService.getRecipientInfo(payment.getReceiverPointCode(), payment.getService().getCode());
		if (businessRecipientInfo == null)
			throw new GateException("�� ������� �������������� ���������� �� ���������� �����, ��� ���������� = " + payment.getReceiverPointCode() + ", ��� ������ = " + payment.getService().getCode());
		return businessRecipientInfo;
	}

	private Element getResultElement(Document document, String creditId) throws GateException
	{
		try
		{
			NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), "//result/result_element");
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);
				if (creditId.equals(XmlHelper.getSimpleElementValue(node, "creditId")))
					return node;
			}

			//���� ������ �������� ������, ���� creditId ������� � ������
			throw new GateException("������������ ������������ �������� ��� ���� creditId = " + creditId);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}
}
