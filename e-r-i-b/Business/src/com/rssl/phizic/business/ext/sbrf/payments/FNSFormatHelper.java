package com.rssl.phizic.business.ext.sbrf.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.shop.FNS;
import com.rssl.phizic.business.shop.Order;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.crypto.CryptoConstants;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.xml.XmlExplorer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.xpath.XPathExpressionException;

/**
 * @author Erkin
 * @ created 21.01.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � ����������� ���
 */
public class FNSFormatHelper extends PaymentsFormatHelper
{
	/**
	 * ����� XSD-����� ��� ��������� ��������� ���������
	 */
	private static final String FNS_XSD_FILE = "com/rssl/phizic/business/ext/sbrf/payments/fns/cbr_packetepd.xsd";

	/**
	 * ������ ���� CCYY-MM-DD
	 * CC = ��� ������ ����� ����
	 * YY = ��� ��������� ����� ����
	 * MM = ����� � ���� ����������� �����
	 * DD = ���� ������ � ���� ����������� �����
	 */
	private static final String DATE_FORMAT_GOST_ISO_8601_2001 = "yyyy-MM-dd";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������ � ���������� ����� �������� ��������� �� XSD-����� ���
	 * @param payOrdersXml - ������ � XML-��������� ������ �������� ��������� (PacketEPD)
	 * @return DOM-�������� � ������� �������� ��������� (PacketEPD)
	 * @throws BusinessException
	 */
	public static Document parsePayOrdersXml(String payOrdersXml) throws BusinessException, ValidateException
	{
		return parsePayOrdersXml(payOrdersXml, FNS_XSD_FILE);
	}

	/**
	 * �������� �� ������ �������� ��������� � ������
	 * @param payOrdersPackXML - ������ � XML-��������� ������ �������� ��������� (ED101)
	 * @return ������ XML-�������� �������� ��������� (ED101)
	 */
	public static List<String> expandPayOrderPack(String payOrdersPackXML)
	{
		if (StringHelper.isEmpty(payOrdersPackXML))
			throw new IllegalArgumentException("�������� 'payOrdersPackXML' �� ����� ���� ������");

		List<String> orders = new LinkedList<String>();
		Scanner scanner = new Scanner(payOrdersPackXML);
		while (true)
		{
			try
			{
				orders.add(scanner.next("<ED101", "</ED101>"));
			}
			catch (NoSuchElementException ignored)
			{
				break;
			}
		}
		return orders;
	}

	/**
	 * ��������� ��� ��������� ���������
	 * @param payOrderTag - ������ � XML-��������� ��������� ��������� (��� ED101)
	 * @return true, ���� �������� ��������� ������������� �������, false �����
	 */
	public static boolean checkPayOrdersPackDigitalSignature(String payOrderTag)
	{
		return checkPayOrdersPackDigitalSignature(payOrderTag, CryptoConstants.FNS_JCP_PUBLIC_SERTIFICATE_ID);
	}

	/**
	 * ����������� ����� �������� ��������� � ������ ������� (���-�����) �� ������
	 * @param person - ������-�������� �������
	 * @param packetEPDDoc - DOM-�������� � ������� �������� ��������� (ED101)
	 * @return ������ �������
	 */
	@SuppressWarnings({"OverlyLongMethod"})
	public static List<FNS> buildOrderInfos(Person person, Document packetEPDDoc) throws BusinessException
	{
		if (packetEPDDoc == null)
			throw new NullPointerException("�������� 'packetEPDDoc' �� ����� ���� null");
		if (person == null)
			throw new NullPointerException("�������� 'person' �� ����� ���� null");

		try
		{
			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_GOST_ISO_8601_2001);
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency rub = currencyService.findByAlphabeticCode("RUR");

			XmlExplorer xmlExplorer = new XmlExplorer();
			xmlExplorer.registerNamespace("ed", "urn:cbr-ru:ed:v2.0");

			NodeList nodeList = xmlExplorer.selectNodeList(packetEPDDoc.getDocumentElement(), "ed:ED101");
			int nodesCount = nodeList.getLength();
			if (nodesCount == 0)
				return Collections.emptyList();
			List<FNS> orderInfos = new ArrayList<FNS>(nodesCount);
			for (int i=0; i< nodesCount; i++)
			{
				Element ed101Tag = (Element) nodeList.item(i);

				Element departmentalInfoTag = (Element) xmlExplorer.selectSingleNode(ed101Tag, "ed:DepartmentalInfo");
				Element payeeTag = (Element) xmlExplorer.selectSingleNode(ed101Tag, "ed:Payee");
				Element payeeBankTag = (Element) xmlExplorer.selectSingleNode(payeeTag, "ed:Bank");
				Element payerTag = (Element) xmlExplorer.selectSingleNode(ed101Tag, "ed:Payer");

				String docNo        = departmentalInfoTag.getAttribute("DocNo");
				Date docDate        = dateFormat.parse(departmentalInfoTag.getAttribute("DocDate"));
				Money docAmount     = Money.fromCents(Long.parseLong(ed101Tag.getAttribute("Sum")), rub);
				String docPurpose   = xmlExplorer.selectSingleNode(ed101Tag, "ed:Purpose").getTextContent();
				String docKBK       = departmentalInfoTag.getAttribute("CBC");
				String docOKATO     = departmentalInfoTag.getAttribute("OKATO");
				String paymentReason = departmentalInfoTag.getAttribute("PaytReason");
				String paymentType  = departmentalInfoTag.getAttribute("TaxPaytKind");
				String drawerStatus = departmentalInfoTag.getAttribute("DrawerStatus");
				String taxPeriod    = departmentalInfoTag.getAttribute("TaxPeriod");
				String receiverName = xmlExplorer.selectSingleNode(payeeTag, "ed:Name").getTextContent();
				String receiverAccountNum = payeeTag.getAttribute("PersonalAcc");
				String receiverINN  = payeeTag.getAttribute("INN");
				String receiverKPP  = payeeTag.getAttribute("KPP");
				String receiverBankBIC = payeeBankTag.getAttribute("BIC");
				String receiverBankCorAccountNum = payeeBankTag.getAttribute("CorrespAcc");
				String payerINN = payerTag.getAttribute("INN");

				Order order = new Order();
				order.setDate(Calendar.getInstance());
				order.setSystemName(PaymentsSystemNameConstants.SYSTEM_NAME_FNS);
				order.setExtendedId(docNo);
				order.setAmount(docAmount);
				order.setDescription(docPurpose);
				order.setReceiverAccount(receiverAccountNum);
				order.setBIC(receiverBankBIC);
				order.setCorrespondentAccount(receiverBankCorAccountNum);
				order.setINN(receiverINN);
				order.setKPP(receiverKPP);
				order.setReceiverName(receiverName);
				order.setPerson(person);
				order.setUuid(new RandomGUID().getStringValue());

				FNS orderInfo = new FNS();
				orderInfo.setKBK(docKBK);
				orderInfo.setOKATO(docOKATO);
				orderInfo.setIndexTaxationDocument(docNo);
				orderInfo.setPaymentGround(paymentReason);
				orderInfo.setPaymentType(paymentType);
				orderInfo.setTaxStatus(drawerStatus);
				orderInfo.setOrder(order);
				orderInfo.setPeriod(taxPeriod);
				orderInfo.setPayerINN(payerINN);

				orderInfos.add(orderInfo);
			}

			return orderInfos;
		}
		catch (ParseException ex)
		{
			throw new BusinessException(ex);
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
		catch (NumberFormatException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
