package com.rssl.phizic.business.ext.sbrf.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.shop.Order;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.crypto.CryptoConstants;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XmlExplorer;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;

/**
 * ������ ��� ������ � ����������� ���
 * @author Rtischeva
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class UECFormatHelper extends PaymentsFormatHelper
{
	/**
	 * ����� XSD-����� ��� ��������� ��������� ���������
	 */
	private static final String UEC_XSD_FILE = "com/rssl/phizic/business/ext/sbrf/payments/uec/cbr_packetepd.xsd";

	// ��� ��� � xml ���������� ���������
	private static final String SIGNATURE_TAG = "dsig:SigValue";

	/**
	 * ������ � ���������� ����� �������� ��������� �� XSD-����� ���
	 * @param payOrdersXml - ������ � XML-��������� ������ �������� ��������� (PacketEPD)
	 * @return DOM-�������� � ������� �������� ��������� (PacketEPD)
	 * @throws BusinessException
	 */
	public static Document parsePayOrdersXml(String payOrdersXml) throws BusinessException, ValidateException
	{
		return parsePayOrdersXml(payOrdersXml, UEC_XSD_FILE);
	}

	/**
	 * �������� �� ������ �������� ��������� (������)
	 * @param payOrderXML - ������ � XML-��������� ������ �������� ��������� (ED101)
	 * @return XML-�������� ��������� ��������� (ED101)
	 */
	public static String expandPayOrderPack(String payOrderXML)
	{
		if (StringHelper.isEmpty(payOrderXML))
			throw new IllegalArgumentException("�������� 'payOrderXML' �� ����� ���� ������");

		Scanner scanner = new Scanner(payOrderXML);
		return scanner.next("<ED101", "</ED101>");

	}

	/**
	 * ��������� ��� ��������� ���������
	 * @param payOrderTag - ������ � XML-��������� ��������� ��������� (��� ED101)
	 * @return true, ���� �������� ��������� ������������� �������, false �����
	 */
	public static boolean checkPayOrdersPackDigitalSignature(String payOrderTag)
	{
		return checkPayOrdersPackDigitalSignature(payOrderTag, CryptoConstants.UEC_JCP_PUBLIC_SERTIFICATE_ID);
	}

	/**
	 * ����������� �������� ��������� � ����� �� ������
	 * @param person - ������-�������� �������
	 * @param packetEPDDoc - DOM-�������� � �������� ���������� (ED101)
	 * @return �����
	 */
	public static Order buildOrderInfo(Person person, Document packetEPDDoc) throws BusinessException
	{
		if (packetEPDDoc == null)
			throw new NullPointerException("�������� 'packetEPDDoc' �� ����� ���� null");
		if (person == null)
			throw new NullPointerException("�������� 'person' �� ����� ���� null");

		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency rub = currencyService.findByAlphabeticCode("RUR");

			XmlExplorer xmlExplorer = new XmlExplorer();
			xmlExplorer.registerNamespace("ed", "urn:cbr-ru:ed:v2.0");

			NodeList nodeList = xmlExplorer.selectNodeList(packetEPDDoc.getDocumentElement(), "ed:ED101");
			int nodesCount = nodeList.getLength();
			if (nodesCount == 0)
				return null;

			Element ed101Tag = (Element) nodeList.item(0);
			String docUID = ed101Tag.getAttribute("DocUID");
			Element payeeTag = (Element) xmlExplorer.selectSingleNode(ed101Tag, "ed:Payee");
			Money docAmount     = Money.fromCents(Long.parseLong(ed101Tag.getAttribute("Sum")), rub);
			String receiverAccountNum = payeeTag.getAttribute("PersonalAcc");
			String receiverINN  = payeeTag.getAttribute("INN");

			Element payeeBankTag = (Element) xmlExplorer.selectSingleNode(payeeTag, "ed:Bank");
			String receiverBankBIC = payeeBankTag.getAttribute("BIC");

			Order order = new Order();
			order.setDate(Calendar.getInstance());
			order.setExtendedId(docUID);
			order.setAmount(docAmount);
			order.setUuid(new RandomGUID().getStringValue());
			order.setPerson(person);
			order.setReceiverAccount(receiverAccountNum);
			order.setINN(receiverINN);
			order.setBIC(receiverBankBIC);
			order.setSystemName(PaymentsSystemNameConstants.SYSTEM_NAME_UEC);

            Map<String, String> additionalFieldsMap = new HashMap <String, String>();
			getChildNodes(ed101Tag, additionalFieldsMap, "");
            String additionalFieldsJsonString = serializeAdditionalFields(additionalFieldsMap);
            order.setAdditionalFields(additionalFieldsJsonString);

			return order;
		}
		catch (XPathExpressionException ex)
		{
			// ����������� ��������� XPath-��������� (������ ������������)
			throw new RuntimeException(ex);
		}
		catch (GateException ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * ����� �������� �� ���� �������� ��������� xml � �������� � ���� ��� ��������
	 * @param xmlNode - �������, ��� �������� �������� ����
	 * @param additionalFieldsMap - ����, � ������� ������: ��� �������� - ��������
	 * @param nodeName - ������ ��� ���� (� ����������� ������� ����� ������������� � ��������� ������) 
	 */
	private static void getChildNodes(Node xmlNode, Map<String, String> additionalFieldsMap, String nodeName)
	{
		NamedNodeMap attributes = xmlNode.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++)
		{
			Node attribute = attributes.item(i);
			String attributeName = attribute.getNodeName();
			if (StringHelper.isNotEmpty(nodeName))
				attributeName = nodeName + "." + attributeName;
			additionalFieldsMap.put(attributeName, attribute.getTextContent());
		}

		NodeList childNodes = xmlNode.getChildNodes();
		StringBuilder textContent = new StringBuilder();

		for (int i = 0; i < childNodes.getLength(); i++)
		{
			Node childNode = childNodes.item(i);
			String childNodeName = childNode.getNodeName();

			if(!SIGNATURE_TAG.equals(childNodeName))
			{
				if(childNode.getNodeType() == 3)
					textContent.append(childNode.getTextContent());
				else
				{
					if (StringHelper.isNotEmpty(nodeName))
						childNodeName = nodeName + "." + childNodeName;
					getChildNodes(childNode, additionalFieldsMap, childNodeName);
				}
			}
		}

		String text = textContent.toString();
		if(StringHelper.isNotEmpty(text))
			additionalFieldsMap.put(nodeName, text);	
	}
}
