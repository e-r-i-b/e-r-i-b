package com.rssl.phizicgate.esberibgate.mock.rate;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.XSDSchemeValidator;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.mock.rate.generated.EribRates_PortType;
import com.rssl.phizicgate.esberibgate.mock.rate.generated.EribRates_ServiceLocator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.xml.transform.TransformerException;
import javax.xml.validation.Schema;

/**
 * @author gulov
 * @ created 01.10.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Сервис для запуска клиентской части вебсервиса обновления курсов валют
 */
public class EribRatesMessagingService
{
	/**
	 * база для расчета курсов евро
	 */
	private static final int BASE_EUR = 35;

	/**
	 * база для расчета курсов доллара
	 */
	private static final int BASE_USD = 30;

	/**
	 * база для расчета курса золота (за грамм)
	 */
	private static final int BASE_A98 = 1350;

	/**
	 * база для расчета курса серебра (за грамм)
	 */
	private static final int BASE_A99 = 34;

	/**
	 * база для расчета курса платина (за грамм)
	 */
	private static final int BASE_A76 = 1699;

	/**
	 * база для расчета курса палладий (за грамм)
	 */
	private static final int BASE_A33 = 738;

	/**
	 * количество минут от текущего времени, для определения времени смены курсов
	 */
	private static final int START_MINUTE = 15;

	/**
	 * Файл xml
	 */
	private static final String XML_FILE_NAME = "com/rssl/phizicgate/esberibgate/mock/rate/xml/CurListInqRq.xml";

	/**
	 * Файл2 xml
	 */
	private static final String XML_FILE2_NAME = "com/rssl/phizicgate/esberibgate/mock/rate/xml/CurListInqRq2.xml";

	/**
	 * Файл схемы валидации xml
	 */
	private static final String SCHEMA_FILE_NAME = "com/rssl/phizicgate/esberibgate/mock/rate/xml/rates.xsd";

	/**
	 * Верхняя граница рандома для определения номера приказа
	 */
	private static final int UPPER_BOUND_FOR_RANDOM = 1000000;

	/**
	 * Верхняя граница рандома для определения кода тарифного плана
	 */
	private static final int UPPER_TARIF_PLAN_CODE_FOR_RANDOM = 4;

	/**
	 * адреса для тестирования курсов
	 */
	private static final String RATES_TEST_URL = "http://localhost:8888/WebCurrency/axis-services/eribRates";
	private static final String RATES_PROXY_TEST_URL = "http://localhost:8888/ProxyWebCurrency/axis-services/eribRates";

	/**
	 * Рандом для генерации значений запроса
	 */
	private Random random = new Random();

	private EribRates_PortType getStub(boolean isProxy) throws Exception
	{
		EribRates_ServiceLocator locator = new EribRates_ServiceLocator();
		locator.seteribRatesEndpointAddress(isProxy ? RATES_PROXY_TEST_URL : RATES_TEST_URL);

		return locator.geteribRates();
	}

	public void makeRequest(boolean isProxy) throws Exception
	{
		boolean isCrossRateEnable = random.nextBoolean(); //true - новый формат, false - старый
		Document document = XmlHelper.loadDocumentFromResource(isCrossRateEnable ? XML_FILE2_NAME : XML_FILE_NAME);

		// Идентификатор запроса
		findAndSetElementText(document, "RqUID", new RandomGUID().getStringValue());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		// Дата и время передачи сообщений
		findAndSetElementText(document, "RqTm", simpleDateFormat.format(Calendar.getInstance().getTime()));

		// Идентификатор операции
		findAndSetElementText(document, "OperUID", new RandomGUID().getStringValue());

		// Номер приказа
		findAndSetElementText(document, "OrderId", Integer.toString(random.nextInt(UPPER_BOUND_FOR_RANDOM)));

		// Дата приказа
		findAndSetElementText(document, "OrderDt", simpleDateFormat.format(Calendar.getInstance().getTime()));

		// Дата/время ввода в действие
		findAndSetElementText(document, "CurListRec/EffDt", simpleDateFormat.format(getStartTime()));

		//Неформализованное текстовое сообщение
		findAndSetElementText(document, "MsText", "");

		MockCurrencyRate rateEUR = new MockCurrencyRate(BASE_EUR, "EUR");
		MockCurrencyRate rateUSD = new MockCurrencyRate(BASE_USD, "USD");
		MockCurrencyRate rateA98 = new MockCurrencyRate(BASE_A98, "A98"); //золото
		MockCurrencyRate rateA99 = new MockCurrencyRate(BASE_A99, "A99"); //серебро
		MockCurrencyRate rateA76 = new MockCurrencyRate(BASE_A76, "A76"); //платина
		MockCurrencyRate rateA33 = new MockCurrencyRate(BASE_A33, "A33"); //палладий

		String[] items;
		if(!isCrossRateEnable)
		{
			String tarifPlanCode = String.valueOf(random.nextInt(UPPER_TARIF_PLAN_CODE_FOR_RANDOM));
			items = new String[] {tarifPlanCode, tarifPlanCode, tarifPlanCode,
								  tarifPlanCode, tarifPlanCode, tarifPlanCode};

			makeCurrencyItem(document, items, "CurListRec/CurListItem/Tarif_plan");

			items = new String[] {rateEUR.getCurrencyCode(), rateUSD.getCurrencyCode(), rateA98.getCurrencyCode(), rateA99.getCurrencyCode(), rateA76.getCurrencyCode(), rateA33.getCurrencyCode()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_code");

			items = new String[] {rateEUR.getQuotient().toPlainString(),
				rateUSD.getQuotient().toPlainString(), rateA98.getQuotient().toPlainString(), rateA99.getQuotient().toPlainString(), rateA76.getQuotient().toPlainString(), rateA33.getQuotient().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_quot");

			items = new String[] {rateEUR.getCBRate().toPlainString(),
				rateUSD.getCBRate().toPlainString(), rateA98.getCBRate().toPlainString(), rateA99.getCBRate().toPlainString(), rateA76.getCBRate().toPlainString(), rateA33.getCBRate().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_cb");

			items = new String[] {rateEUR.getBuyRate().toPlainString(),
				rateUSD.getBuyRate().toPlainString(), rateA98.getBuyRate().toPlainString(), rateA99.getBuyRate().toPlainString(), rateA76.getBuyRate().toPlainString(), rateA33.getBuyRate().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_buy");

			items = new String[] {rateEUR.getSaleRate().toPlainString(),
				rateUSD.getSaleRate().toPlainString(), rateA98.getSaleRate().toPlainString(), rateA99.getSaleRate().toPlainString(), rateA76.getSaleRate().toPlainString(), rateA33.getSaleRate().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_sale");

			items = new String[] {rateEUR.getBuyRemoteRate().toPlainString(),
				rateUSD.getBuyRemoteRate().toPlainString(), rateA98.getBuyRemoteRate().toPlainString(), rateA99.getBuyRemoteRate().toPlainString(), rateA76.getBuyRemoteRate().toPlainString(), rateA33.getBuyRemoteRate().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_buy3");

			items = new String[] {rateEUR.getSaleRemoteRate().toPlainString(),
				rateUSD.getSaleRemoteRate().toPlainString(), rateA98.getSaleRemoteRate().toPlainString(), rateA99.getSaleRemoteRate().toPlainString(), rateA76.getSaleRemoteRate().toPlainString(), rateA33.getSaleRemoteRate().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_sale3");
		}
		else
		{
			String tarifPlanCode = String.valueOf(random.nextInt(UPPER_TARIF_PLAN_CODE_FOR_RANDOM));
			items = new String[] {tarifPlanCode, tarifPlanCode};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Tarif_plan");
			
			items = new String[] {rateEUR.getCurrencyCode(), rateUSD.getCurrencyCode()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_code");

			items = new String[] {rateUSD.getCurrencyCode(), rateEUR.getCurrencyCode()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_code2");

			items = new String[] {rateEUR.getQuotient().toPlainString(), rateUSD.getQuotient().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_quot");

			items = new String[] {rateEUR.getCBRate().divide(rateUSD.getCBRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString(),
					rateUSD.getCBRate().divide(rateEUR.getCBRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_cb");

			items = new String[] {rateEUR.getBuyRate().divide(rateUSD.getBuyRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString(),
					rateUSD.getBuyRate().divide(rateEUR.getBuyRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_buy");

			items = new String[] {rateEUR.getSaleRate().divide(rateUSD.getSaleRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString(),
					rateUSD.getSaleRate().divide(rateEUR.getSaleRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_sale");

			items = new String[] {rateEUR.getBuyRemoteRate().divide(rateUSD.getBuyRemoteRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString(),
					rateUSD.getBuyRemoteRate().divide(rateEUR.getBuyRemoteRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_buy3");

			items = new String[] {rateEUR.getSaleRemoteRate().divide(rateUSD.getSaleRemoteRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString(),
					rateUSD.getSaleRemoteRate().divide(rateEUR.getSaleRemoteRate(), 4, BigDecimal.ROUND_HALF_UP).toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_sale3");

			items = new String[] {new Boolean(random.nextBoolean()).toString(),
				new Boolean(random.nextBoolean()).toString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_straight");

			items = new String[] {rateEUR.getDelta().toPlainString(),
				rateUSD.getDelta().toPlainString(), rateA98.getDelta().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_cb_delta");

			items = new String[] {rateEUR.getDelta().toPlainString(),
				rateUSD.getDelta().toPlainString(), rateA98.getDelta().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_buy_delta");

			items = new String[] {rateEUR.getDelta().toPlainString(),
				rateUSD.getDelta().toPlainString(), rateA98.getDelta().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_sale_delta");

			items = new String[] {rateEUR.getDelta().toPlainString(),
				rateUSD.getDelta().toPlainString(), rateA98.getDelta().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_buy3_delta");

			items = new String[] {rateEUR.getDelta().toPlainString(),
				rateUSD.getDelta().toPlainString(), rateA98.getDelta().toPlainString()};
			makeCurrencyItem(document, items, "CurListRec/CurListItem/Pr_sale3_delta");
		}

		validate(document);

		getStub(isProxy).doIFX(XmlHelper.convertDomToText(document));
	}

	private void makeCurrencyItem(Document document, String[] list, String tagName) throws TransformerException
	{
		// если тега с именем tagName в сообщении нет, то nodeList.getLength() вернет 0 (ошибки не будет)
		NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), tagName);

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);
			if (list.length - 1 >= i)
				setElementText(element, list[i]);
		}
	}

	private Date getStartTime()
	{
		Calendar result = Calendar.getInstance();

		result.add(Calendar.MINUTE, random.nextInt(START_MINUTE));

		return result.getTime();
	}

	private void findAndSetElementText(Document document, String elementName, String text) throws TransformerException
	{
		Element element = XmlHelper.selectSingleNode(document.getDocumentElement(), elementName);

		if (element != null)
			setElementText(element, text);
	}

	private void setElementText(Element element, String text)
	{
		String tagText = element.getTextContent();

		if (tagText.equals(""))
			element.setTextContent(text);
	}

	private void validate(Document document) throws SAXException, ValidateException
	{
		Schema schema = XmlHelper.schemaByFileName(SCHEMA_FILE_NAME);

		XSDSchemeValidator.validate(schema, document);
	}
}
