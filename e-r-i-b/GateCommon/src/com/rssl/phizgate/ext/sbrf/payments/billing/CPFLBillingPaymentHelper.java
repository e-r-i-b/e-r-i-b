package com.rssl.phizgate.ext.sbrf.payments.billing;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.ListField;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 21.02.2011
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ������� ��� ������ � �������� NplatRequisites.
 * ������ ������ ���������� ��� �������� �������� �� ���������� ������� � ���� � �� ���������� � ���
 */
public class CPFLBillingPaymentHelper extends BillingPaymentHelper
{
	//��������� ������������� ���������� ����.
	public static final Comparable NOT_CONTRACTUAL_RECEIVER_CODE = "$NCRC180804$";

	//������������ ����� ��� �������� �������� ��� ���� (�������� ���� ������� � ������ - ��� 4000 ���� ��� varchar2)
	private static final int MAX_FIELD_VALUE_SIZE = 4000;//���������� ���� �������� ��� varchar2 ������

	private static final String NPLAT_REQUISITES_FIELD_NAME = "NplatRequisites";
	protected static final String SPECAL_CLIENT_FIELD_PEFIX = "SpecalClient-";
	private static final String ACK_CLIENT_BANK_CAN_CHANGE_SUMM_FIELD_NAME = "AckClientBankCanChangeSumm";
	private static final String COMMISSION_LABEL_FIELD_NAME = "commissionLabel";
	private static final String SERVICE_CODE_FIELD_NAME = "serviceCode";
	private static final String SERVICE_KIND_FIELD_NAME = "ServiceKind";
	private static final String TARIFF_VALUE_FIELD_NAME = "tariffValue";
	public static final String RECEIVER_NAME_FIELD_NAME = "temporaryReceiverName";

	/**
	 * �������� (��� ������� ��� ���������) ������ � ����������� � ���������� ��� �������� ��������
	 * @param payment ������, � ������� ���� ��������� ������
	 * @param element ������� nplatRequisites
	 */
	public static void updateDocumentNplatRequisitesInfo(AbstractPaymentSystemPayment payment, Element element) throws GateException, GateLogicException
	{
		storeDocumentHiddenFieldValue(payment, NPLAT_REQUISITES_FIELD_NAME, encodeBase64(element));
	}

	/**
	 * �������� ���� ������ NplatRequisites �� ���������
	 * @param payment ��������
	 * @return NplatRequisites. ����� ������������(� ������ ������� ������� � ���� �� ���������� �������)
	 */
	public static Document getNplatRequisites(AbstractPaymentSystemPayment payment) throws GateException
	{
		String value = restoreDocumentHiddenFieldValue(payment, NPLAT_REQUISITES_FIELD_NAME);
		return parseXml(decodeBase64(value));
	}

	/**
	 * ������� ������� ��� ����
	 * @param name ��� ����
	 * @return ���. ����
	 * @throws GateException
	 */
	public static CommonField createHiddenField(String name) throws GateException
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(name);
		field.setName(name);
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(false);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(false);
		return field;
	}

	/**
	 * ��������� � ��������� � ������� ����(�����) ��������
	 * �������� ����� ���� ��������� � ���������� ����� (���� ��������� MAX_FIELD_VALUE_SIZE).
	 * ����������� ����� ������� �������� ��������� �������� ������� restoreDocumentHiddenFieldValue.
	 * @param payment ��������, � ������� ��������� ��������� ��������
	 * @param name ��� ��� ������� ����������� �������� � ��� ������� �������� ����� ��������������.
	 * ���� � ������� ��� ������ �������� � ����� ������, �� ������ �������� ����� ��������
	 * @param value �������� ��� ����������.
	 */
	protected static void storeDocumentHiddenFieldValue(AbstractPaymentSystemPayment payment, String name, String value) throws GateException, GateLogicException
	{
		//�������� ��������� ��������� �������� �� MAX_FIELD_VALUE_SIZE � ������� ����� � ������� name_i, ��� i - ����� ������(�����).
		try
		{
			String fieldNamePrefix = name + "_";
			List<Field> fields = payment.getExtendedFields();
			if (fields == null)
			{
				fields = new ArrayList<Field>();
			}
			//������� ������� ��� ������ ����
			for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();)
			{
				Field field = iterator.next();
				if (field.getName().startsWith(fieldNamePrefix) && field.getExternalId().startsWith(fieldNamePrefix))
				{
					iterator.remove();
				}
			}
			//������ ������� ����
			int partsCount = value.length() / MAX_FIELD_VALUE_SIZE + 1; //���������� �����(������), � ������� ����� ��������� ��������.
			for (int i = 0; i < partsCount; i++)
			{
				CommonField field = createHiddenField(fieldNamePrefix + i);
				String part = value.substring(i * MAX_FIELD_VALUE_SIZE, Math.min((i + 1) * MAX_FIELD_VALUE_SIZE, value.length()));
				field.setDefaultValue(part);
				fields.add(field);
			}
			payment.setExtendedFields(fields);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ��������, ����������� � ������� ���� ��������� ������� storeDocumentHiddenFieldValue
	 * @param payment ��������
	 * @param name ���, ��� ������� ��������� ��������
	 * @return ����������� ��������
	 */
	protected static String restoreDocumentHiddenFieldValue(AbstractPaymentSystemPayment payment, String name) throws GateException
	{
		StringBuilder result = new StringBuilder();
		for (int i = 0; ; i++)
		{
			Field field = getFieldById(payment, name + "_" + i);
			if (field == null)
			{
				if (i == 0)
				{
					return null;
				}
				return result.toString();
			}
			result.append(field.getDefaultValue());
		}
	}

	protected static String decodeBase64(String value)
	{
		if (value == null)
		{
			return null;
		}
		return new String(Base64.decodeBase64(value.getBytes()));
	}

	protected static String encodeBase64(Node element) throws GateException
	{
		if (element == null)
		{
			return null;
		}
		try
		{
			return new String(encodeBase64(XmlHelper.convertDomToText(element)));
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private static String encodeBase64(String value)
	{
		if (value == null)
		{
			return null;
		}
		return new String(Base64.encodeBase64(value.getBytes()));
	}

	protected static Document parseXml(String value) throws GateException
	{
		if (value == null)
		{
			return null;
		}
		try
		{
			return XmlHelper.parse(value);
		}
		catch (ParserConfigurationException e)
		{
			throw new GateException(e);
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ��� �����������:
	 * 1) ����� �������� NplatRequisites/specialClientCodeOrg, ���� ����
	 * 2) ���� ��� - �������� ��������  specialClient/specialClientNplat/specialClientCode � ������������ ���������  vvodNumber
	 * @param payment ������
	 * @return ��� ����������� ��� null, ���� ��� ���.
	 * @throws GateException
	 */
	public static String getSpecClientCode(CardPaymentSystemPayment payment) throws GateException
	{
		//�������� ����� NplatRequisites/specialClientCodeOrg
		String specialClientCodeOrg = XmlHelper.getSimpleElementValue(getNplatRequisites(payment).getDocumentElement(), "specialClientCodeOrg");
		if (!StringHelper.isEmpty(specialClientCodeOrg))
		{
			return specialClientCodeOrg;
		}
		//������� ��������� ������ specialClient
		String specalClient = null;
		for (int i = 0; ; i++)
		{
			String tmpspecalClient = restoreDocumentHiddenFieldValue(payment, SPECAL_CLIENT_FIELD_PEFIX + (i + 1));//vvodNumber ���������� � 1
			if (tmpspecalClient == null)
			{
				break;
			}
			specalClient = tmpspecalClient;
		}
		//�� ����� - ���� ���� ������� ���.
		if (specalClient == null)
		{
			return null;
		}
		try
		{
			return XmlHelper.getElementValueByPath(parseXml(decodeBase64(specalClient)).getDocumentElement(), "/specialClient/specialClientNplat/specialClientCode");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ������� ���� ��� �������� �������� ackClientBankCanChangeSumm.
	 * @return ����.
	 */
	public static Field createAckClientBankCanChangeSumm() throws GateException
	{
		//������ �� ������� ��������� - ������� ����-������.
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(ACK_CLIENT_BANK_CAN_CHANGE_SUMM_FIELD_NAME);
		field.setName("��������� ��������� ����� ������� ��� ��������� ������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(true);
		field.setRequiredForConformation(true);
		List<ListValue> values = new ArrayList<ListValue>();
		values.add(new ListValue("��", "true"));
		values.add(new ListValue("���", "false"));
		field.setValues(values);
		return field;
	}

	/**
	 * �������� �������� ���� ackClientBankCanChangeSumm
	 * @param transfer ������
	 * @return �������� ����.
	 */
	public static String getAckClientBankCanChangeSumm(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, ACK_CLIENT_BANK_CAN_CHANGE_SUMM_FIELD_NAME);
		if (field == null)
		{
			return "false";
		}
		return (String) field.getValue();
	}

	/**
	 * ������� ������� ���� ��� ��������� serviceCode
	 * @param serviceCode -serviceCode
	 * @return ����
	 * @throws GateException
	 */
	public static Field createHiddenServiceCode(String serviceCode) throws GateException
	{
		CommonField result = createHiddenField(SERVICE_CODE_FIELD_NAME);
		result.setDefaultValue(serviceCode);
		result.setValue(serviceCode);
		return result;
	}

	/**
	 * �������� �� ������� �������� ServiceCode
	 * @param transfer ������
	 * @return ������� ��� null, ���� ����������
	 * @throws GateException
	 */
	public static String getServiceCode(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, SERVICE_CODE_FIELD_NAME);
		if (field == null)
		{
			return null;
		}
		return (String) field.getValue();
	}

	/**
	 * ������� ������� ���� ��� ��������� commissionLabel
	 * @param commissionLabel -commissionLabel
	 * @return ����
	 * @throws GateException
	 */
	public static Field createHiddenCommissionLabel(String commissionLabel) throws GateException
	{
		CommonField result = createHiddenField(COMMISSION_LABEL_FIELD_NAME);
		result.setDefaultValue(commissionLabel);
		result.setValue(commissionLabel);
		return result;
	}

	/**
	 * �������� �� ������� �������� CommissionLabel
	 * @param transfer ������
	 * @return ������� ��� null, ���� ����������
	 * @throws GateException
	 */
	public static String getCommissionLabel(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, COMMISSION_LABEL_FIELD_NAME);
		if (field == null)
		{
			return null;
		}
		return (String) field.getValue();
	}

	/**
	 * ������� ���� ��� �������. ����� � ���� ����� 3 �������� ���, ������������, � ��������.
	 * ������������ ������������ �������.
	 * ��������� 2 ����-������:  ���-������������(������� ����), ���-��������(������� ����)
	 * ������� ���� ����� ������������ ��� �������� ��������.
	 * @param responce ����� ����
	 * @return ���� ��� null, ���� ������� � ������ ���.
	 * @throws GateException
	 */
	public static List<Field> createTariffFields(Document responce) throws GateException
	{
		try
		{
			NodeList tariffs = XmlHelper.selectNodeList(responce.getDocumentElement(), "tariff");
			if (tariffs.getLength() == 0)
			{
				//������� ���
				return null;
			}
			List<Field> result = new ArrayList<Field>();
			//������� ���� ��� ������ ��������
			result.add(createServiceKind(tariffs));
			//������� ������� ����
			result.add(createHiddenTariffField(tariffs));
			return result;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private static Field createHiddenTariffField(NodeList tariffs)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(TARIFF_VALUE_FIELD_NAME);
		field.setName("�������� ������");
		field.setRequired(false);
		field.setEditable(false);
		field.setVisible(false);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(false);
		field.setRequiredForConformation(false);

		List<ListValue> values = new ArrayList<ListValue>();
		for (int i = 0; i < tariffs.getLength(); i++)
		{
			Element tariff = (Element) tariffs.item(i);
			values.add(new ListValue(XmlHelper.getSimpleElementValue(tariff, "tariffValue"), XmlHelper.getSimpleElementValue(tariff, "tariffCode")));
		}
		field.setValues(values);

		return field;
	}

	private static Field createServiceKind(NodeList tariffs)
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.list);
		field.setExternalId(SERVICE_KIND_FIELD_NAME);
		field.setName("�����");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(true);
		field.setRequiredForConformation(true);

		List<ListValue> values = new ArrayList<ListValue>();
		for (int i = 0; i < tariffs.getLength(); i++)
		{
			Element tariff = (Element) tariffs.item(i);
			values.add(new ListValue(XmlHelper.getSimpleElementValue(tariff, "tariffName"), XmlHelper.getSimpleElementValue(tariff, "tariffCode")));
		}
		field.setValues(values);

		return field;
	}

	/**
	 * �������� ��� ������ �� �������
	 * @param transfer �������
	 * @return ��� ���� ��� nul  � ������ ����������.
	 * @throws GateException
	 */
	public static String getServiceKind(CardPaymentSystemPayment transfer) throws GateException
	{
		Field field = getFieldById(transfer, SERVICE_KIND_FIELD_NAME);
		if (field == null)
		{
			return null;
		}
		return (String) field.getValue();
	}

	/**
	 * �������� �������� ������ �� �������
	 * @param transfer �������
	 * @return �������� ���� ��� nul  � ������ ����������.
	 * @throws GateException
	 */
	public static String getTariff(CardPaymentSystemPayment transfer) throws GateException
	{
		//������ ���� �� ���� �� ���������� �������
		ListField tariffValuefield = (ListField) getFieldById(transfer, TARIFF_VALUE_FIELD_NAME);
		if (tariffValuefield == null)
		{
			return null;
		}
		//�������� ����, ������� ������� ������
		Field serviceKindField = getFieldById(transfer, SERVICE_KIND_FIELD_NAME);
		if (tariffValuefield == null)
		{
			throw new GateException("��������������� ��������� ���������: ���� � �������� ����, � �� ���������� ���. ������������ �������: " + transfer.getId());
		}
		//���� � ������� ���� �������� ������ ��������������, ���� ������� ������ ������ �� �����������... �����...))
		String tariffCode = (String) serviceKindField.getValue();
		for (ListValue listValue : tariffValuefield.getValues())
		{
			if (tariffCode.equals(listValue.getId()))
			{
				return listValue.getValue();
			}
		}
		throw new GateException("��������������� ��������� ���������: �� ������� �������� ������ ��� ����� ������ " + tariffCode + ". ������������ �������: " + transfer.getId());
	}

	/**
	 * ��������� �������� �� ���������� ������������ ������������
	 * @param receiverPointCode ������������ ����������
	 * @return ��/���.
	 */
	public static boolean isContractual(String receiverPointCode) {
		if (StringHelper.isEmpty(receiverPointCode))
		{
			return false;
		}
		if (receiverPointCode.startsWith("-"))
		{
			//�� ������������ '-' ��� �������� �� ����-�� �������������� � �������� ����������� �������������� ��������-��� ��� �������������� � ��, �.�. ����������� ������ ��������� �������������.
			return false;
		}
		return !CPFLBillingPaymentHelper.NOT_CONTRACTUAL_RECEIVER_CODE.equals(receiverPointCode);
	}

	/**
	 * ������� ��������� ���� ��� �������� �������� ����������.
	 * @return
	 */
	public static Field createReceiverNameField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(RECEIVER_NAME_FIELD_NAME);
		field.setName("������������ ����������");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(false);
		field.setKey(false);
		field.setRequiredForBill(false);
		field.setRequiredForConformation(false);
		return field;
	}
}
