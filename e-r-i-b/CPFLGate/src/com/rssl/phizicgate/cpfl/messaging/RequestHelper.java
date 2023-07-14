package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class RequestHelper extends CPFLBillingPaymentHelper
{
	private static final String HIDDEN_ID_FROM_PAYMENT_SYSTEM_FIELD_NAME = "HiddenIdFromPaymentSystem";

	private static final Set<String> redundantFieldsNames = new HashSet<String>();//��������� ���� ���������� ������� �� ������� ����������� �������

	static
	{
		redundantFieldsNames.add("��� ����������:");
	}

	private GateFactory factory;

	public RequestHelper(GateFactory factory)
	{
		this.factory = factory;
	}

	/**
	 * ���������� �������� ����� �� ������ ������ specialClient
	 * @param specialClientNodes  - ������ ����� specialClient
	 * @return ������ �����. ���� ���������� - ������ ������
	 */
	public List<Field> parseSpecialClients(NodeList specialClientNodes, String paymentTypeValue) throws GateException, GateLogicException
	{
		List<Field> result = new ArrayList<Field>();
		if (specialClientNodes == null)
		{
			return result;
		}
		for (int i = 0; i < specialClientNodes.getLength(); i++)
		{
			result.addAll(parseSpecialClient(specialClientNodes.item(i), paymentTypeValue));
		}
		return result;
	}

	/**
	 * ���������� �������� ����� �� ������ specialClient
	 * @param specialClient  ������ specialClient
	 * @return ������ �����. ���� ���������� - ������ ������;
	 */
	public List<Field> parseSpecialClient(Node specialClient,String paymentTypeValue) throws GateException, GateLogicException
	{
		List<Field> result = new ArrayList<Field>();
		if (specialClient == null)
		{
			return result;
		}
		int vvodNumber = getVvodNumber(specialClient);
		try
		{
			NodeList requisites = XmlHelper.selectNodeList((Element) specialClient, "requisites");
			for (int i = 0; i < requisites.getLength(); i++)
			{
				Field field = parseRequisite((Element) requisites.item(i), i, vvodNumber, paymentTypeValue);
				if (field != null)
				{
					result.add(field);
				}
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}

		return result;
	}

	/**
	 * ���������� �������� ���� �� ������ Requisites
	 * ��� ����� ������ requisites ��������� 1 ������ Field(�������� �������������),
	 * �� ������ �� ��, ��� �� ����������� �������� ������� �� ���������� �����.
	 * ������ ��������� ���������� ����� � �������� ��������������� � CPFLRequisiteProcessor
	 * @param requisites  ������ Requisites
	 * @param number ����� ��������� � ������ SpecialClient.
	 * @param vvodNumber �������� vvodNumber ������������� ����.
	 * @return ���� ��� null, ���� ���� ���������(�� ������� ������������ �������).
	 */
	private Field parseRequisite(Element requisites, int number, int vvodNumber, String paymentTypeValue) throws GateException, GateLogicException
	{
		CPFLRequisiteProcessor cpflRequisiteProcessor = new CPFLRequisiteProcessor(factory, requisites, number, vvodNumber);
		CPFLRequisiteField field =  cpflRequisiteProcessor.getField();
		CommonField result = new CommonField();
		String name = XmlHelper.getSimpleElementValue(requisites, "name");
		if (redundantFieldsNames.contains(name))
		{
			return null;
		}
		result.setName(name);
		result.setExternalId(cpflRequisiteProcessor.getExternalId());
		result.setHint(cpflRequisiteProcessor.getHint());
		result.setRequired(cpflRequisiteProcessor.isRequired());
		result.setMainSum(false);
		result.setKey(false);
		result.setVisible(true);
		result.setRequiredForBill(true);
		result.setRequiredForConformation(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(requisites, "isForSms")));
		result.setSaveInTemplate(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(requisites, "isMandatory")));
		result.setHideInConfirmation(false);
		result.setMaxLength(cpflRequisiteProcessor.getMaxLength());
		result.setMinLength(cpflRequisiteProcessor.getMinLength());
		result.setFieldValidationRules(cpflRequisiteProcessor.getValidationRules());
		if(field!=null && "T".equals(field.getTemplate()))
		{
			fillPaymentTypeField(result, paymentTypeValue, cpflRequisiteProcessor);
			return result;
		}
		result.setValues(cpflRequisiteProcessor.getMenu());
		result.setType(cpflRequisiteProcessor.getType());
		result.setEditable(true);
		return result;
	}

	/**
	 * �������� �� ������� ������� ���� �� ��������� ������ SpecalClient ������������� ��������� vvodNumber
	 * @param payment ������
	 * @param vvodNumber �������� ���� vvodNumber
	 * @return ������ SpecalClient, �������������� vvodNumber.
	 */
	private static Document getSpecalClient(AbstractPaymentSystemPayment payment, int vvodNumber) throws GateException
	{
		String value = restoreDocumentHiddenFieldValue(payment, SPECAL_CLIENT_FIELD_PEFIX + vvodNumber);
		return parseXml(decodeBase64(value));
	}

	/**
	 * �������� ���� ������ SpecialClient �� ���������
	 * ���� �������� � ������� ����.
	 * �������� ��������� enteredData ����� � ������������ vvodNumber ����������� �������, ���������� ��������
	 * @param payment ��������
	 * @return specialClient. ����� ������������(� ������ ������� ������� � ���� �� ���������� �������)
	 */
	public List<Document> getSpecialClient(AbstractPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		List<Document> result = new ArrayList<Document>();
		for (int i = 0; ; i++)
		{
			Document specalClient = getSpecalClient(payment, i + 1);//vvodNumber ���������� � 1
			if (specalClient == null)
			{
				//���� ���� ��������������� vvodNumner == i+1 ��� -
				// 1) ��������� ��������� ������(���� ��� ����) ������� �� �������.
				if (!result.isEmpty())
				{
					fillEnteredData(result.get(i - 1), payment);
				}
				// 2) ����������, �� ��� �������� � result
				return result;
			}
			result.add(specalClient);
		}
	}

	/**
	 * �������� (��� ������� ��� ���������) ������ � ����������� � ���� �������
	 * @param payment ������, � ������� ���� ��������� ������
	 * @param nodeList ������ ����� SpecalClient.
	 */
	public static void updateDocumentSpecalClientInfo(AbstractPaymentSystemPayment payment, NodeList nodeList) throws GateException, GateLogicException
	{
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			int vvodNumber = getVvodNumber(node);
			storeDocumentHiddenFieldValue(payment, SPECAL_CLIENT_FIELD_PEFIX + vvodNumber, encodeBase64(node));
		}
	}

	/**
	 * ��������� ������ specalClient �������, ���������� ������������� (enteredData)
	 * @param specalClientNode ���� specalClient ��� ���������� ������ enteredData
	 * @param payment ������ � �������.
	 */
	private void fillEnteredData(Document specalClientNode, AbstractPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		if (specalClientNode == null)
		{
			return;
		}
		int vvodNumber = getVvodNumber(specalClientNode.getDocumentElement());
		try
		{
			NodeList requisites = XmlHelper.selectNodeList(specalClientNode.getDocumentElement(), "requisites");
			for (int i = 0; i < requisites.getLength(); i++)
			{
				fillEnteredData((Element) requisites.item(i), i, vvodNumber, payment);
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ��������� ������ requisites �������, ���������� ������������� (enteredData)
	 * @param requisites ������ requisites
	 * @param number ����� ��������� � ������ SpecialClient.
	 * @param vvodNumber �������� vvodNumber ������������� ����.
	 * @param payment ������ � �������.
	 */
	private void fillEnteredData(Element requisites, int number, int vvodNumber, AbstractPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		if (requisites == null)
		{
			return;
		}
		CPFLRequisiteProcessor cpflRequisiteProcessor = new CPFLRequisiteProcessor(factory, requisites, number, vvodNumber);
		try
		{
			NodeList fields = XmlHelper.selectNodeList(requisites, "field");
			for (int i = 0; i < fields.getLength(); i++)
			{
				String value = cpflRequisiteProcessor.getRequisiteFieldValue(payment, i);
				//�������� ��� ���� ���� - ��������� ������ enteredData.
				if (value != null)
				{
					XmlHelper.appendSimpleElement((Element) fields.item(i), "enteredData", value);
				}
			}
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� �������� ���� vvodNumber ��� ���� specalClient
	 * @param specalClientNode ��e� specalClient
	 * @return �������� specalClient
	 */
	private static int getVvodNumber(Node specalClientNode)
	{
		return Integer.parseInt(XmlHelper.getSimpleElementValue((Element) specalClientNode, "vvodNumber"));
	}

	/**
	 * ������� ������� ���� ��� �������� ������������ ������� � ��������
	 * @param billingDocumentId �������� ����
	 * @return ���� �� ���������
	 * @throws GateException
	 */
	public static Field createHiddenIdFromPaymentSystem(String billingDocumentId) throws GateException
	{
		CommonField result = createHiddenField(HIDDEN_ID_FROM_PAYMENT_SYSTEM_FIELD_NAME);
		result.setDefaultValue(billingDocumentId);
		result.setValue(billingDocumentId);
		return result;
	}

	/**
	 * ������� �������� �������� ���� ��� �������� ������������ ������� � ��������
	 * @param payment ������
	 * @return �������� ��� null ���� ���������� ��� �� ������
	 * @throws GateException
	 */
	public static String getHiddenIdFromPaymentSystem(AbstractPaymentSystemPayment payment) throws GateException
	{
		Field field = getFieldById(payment, HIDDEN_ID_FROM_PAYMENT_SYSTEM_FIELD_NAME);
		if (field == null)
		{
			return null;
		}
		return (String) field.getValue();
	}

	/**
	 * ��������� ���� � ������ ���� ��� ��� �������. �������� � nplatRequisites.
	 * ���� �������� � ���������� �� ������� ���-������������� ������������ ����� ��� ����, ����� ������ �� ����������
	 * @param result - ����������� ����
	 * @param paymentTypeValue - �������� paymentType �� �������
	 * @param cpflRequisiteProcessor - �������� ���������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public static void fillPaymentTypeField(CommonField result, String paymentTypeValue, CPFLRequisiteProcessor cpflRequisiteProcessor) throws GateLogicException, GateException
	{
		List<ListValue> listValues = cpflRequisiteProcessor.getMenu();
		if (paymentTypeValue != null)
		{
			for (ListValue value : listValues)
			{
				if (value.getId().equals(paymentTypeValue))
				{
					result.setEditable(false);
					result.setValue(value.getValue());
					result.setType(FieldDataType.string);
					return;
				}
			}
		}
		result.setEditable(true);
		result.setType(cpflRequisiteProcessor.getType());
		result.setValues(listValues);
	}
}
