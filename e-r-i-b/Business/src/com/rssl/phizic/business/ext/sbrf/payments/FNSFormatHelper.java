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
 * Методы для работы с документами ФНС
 */
public class FNSFormatHelper extends PaymentsFormatHelper
{
	/**
	 * Файлы XSD-схемы для валидации платёжного поручения
	 */
	private static final String FNS_XSD_FILE = "com/rssl/phizic/business/ext/sbrf/payments/fns/cbr_packetepd.xsd";

	/**
	 * Формат даты CCYY-MM-DD
	 * CC = две первые цифры года
	 * YY = две последние цифры года
	 * MM = месяц в виде двузначного числа
	 * DD = день месяца в виде двузначного числа
	 */
	private static final String DATE_FORMAT_GOST_ISO_8601_2001 = "yyyy-MM-dd";

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Читает и валидирует пакет платёжных поручений по XSD-схеме ФНС
	 * @param payOrdersXml - строка с XML-описанием пакета платёжных поручений (PacketEPD)
	 * @return DOM-документ с пакетом платёжных поручений (PacketEPD)
	 * @throws BusinessException
	 */
	public static Document parsePayOrdersXml(String payOrdersXml) throws BusinessException, ValidateException
	{
		return parsePayOrdersXml(payOrdersXml, FNS_XSD_FILE);
	}

	/**
	 * Выбирает из пакета платёжные поручения в список
	 * @param payOrdersPackXML - строка с XML-описанием пакета платёжных поручений (ED101)
	 * @return список XML-описаний платёжных поручений (ED101)
	 */
	public static List<String> expandPayOrderPack(String payOrdersPackXML)
	{
		if (StringHelper.isEmpty(payOrdersPackXML))
			throw new IllegalArgumentException("Аргумент 'payOrdersPackXML' не может быть пустым");

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
	 * Проверяет ЭЦП платёжного поручения
	 * @param payOrderTag - строка с XML-описанием платёжного поручения (тег ED101)
	 * @return true, если платёжное поручение соответствует подписи, false иначе
	 */
	public static boolean checkPayOrdersPackDigitalSignature(String payOrderTag)
	{
		return checkPayOrdersPackDigitalSignature(payOrderTag, CryptoConstants.FNS_JCP_PUBLIC_SERTIFICATE_ID);
	}

	/**
	 * Преобразует пакет платёжных поручений в список заказов (ФНС-услуг) на оплату
	 * @param person - клиент-владелец платежа
	 * @param packetEPDDoc - DOM-документ с пакетом платёжных поручений (ED101)
	 * @return список заказов
	 */
	@SuppressWarnings({"OverlyLongMethod"})
	public static List<FNS> buildOrderInfos(Person person, Document packetEPDDoc) throws BusinessException
	{
		if (packetEPDDoc == null)
			throw new NullPointerException("Аргумент 'packetEPDDoc' не может быть null");
		if (person == null)
			throw new NullPointerException("Аргумент 'person' не может быть null");

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
			// Неправильно построено XPath-выражение (ошибка программиста)
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
