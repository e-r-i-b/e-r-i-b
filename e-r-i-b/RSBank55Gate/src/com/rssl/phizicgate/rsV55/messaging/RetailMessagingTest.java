package com.rssl.phizicgate.rsV55.messaging;

import com.rssl.phizgate.common.messaging.retail.RetailMessagingConfig;
import com.rssl.phizgate.common.messaging.retail.RetailResponseHandler;
import com.rssl.phizgate.messaging.RetailMessageGeneratorB;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.MessageData;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsV55.test.RSRetaileGateTestCaselBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Roshka
 * @ created 16.11.2006
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"JavaDoc"})
public class RetailMessagingTest extends RSRetaileGateTestCaselBase
{
	public static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	private MessagingConfig  messagingConfig;
	private SAXParserFactory saxParserFactory;
	private ClassLoader      classLoader;

	protected void setUp() throws Exception
	{
		super.setUp();

		RetailMessagingConfig retailMessagingConfig = ConfigFactory.getConfig(RetailMessagingConfig.class);
		messagingConfig  = ConfigurationLoader.load(retailMessagingConfig.getConfigFilePath());
		saxParserFactory = SAXParserFactory.newInstance();
		classLoader      = Thread.currentThread().getContextClassLoader();
	}

	protected void tearDown() throws Exception
	{
		messagingConfig  = null;
		saxParserFactory = null;
		classLoader      = null;
		super.tearDown();
	}

	public void testGenerator() throws Exception
	{
		String testXml = "com/rssl/phizicgate/rsV55/messaging/mock/xml/getRateCurrency_q_body.xml";

		Document document = XmlHelper.loadDocumentFromResource(testXml);

		RetailMessageGeneratorB rmg = new RetailMessageGeneratorB();

		MessageData data = rmg.buildMessage(document, null, null);

		assertNotNull(data);
		assertNotNull(data.getId());
		assertNull(data.getEntityId());
		assertTrue(data.getId().matches("[0-9a-fA-F]{32}"));
		assertNotNull(data.getBody());
		assertNotNull(data.getBodyAsString("cp866"));
	}

	public void testResponseParser() throws Exception
	{
		MessageInfo messageInfo = messagingConfig.getMessageInfo("commissionPay_q");

		RetailResponseHandler handler = new RetailResponseHandler(messageInfo);

		parseFile("com/rssl/phizicgate/rsV55/messaging/mock/xml/commissionPay_a.xml", handler);

		assertNotNull(handler.getMessageId());
		assertNotNull(handler.getBody());
		assertTrue(handler.isSuccess());
		assertFalse(handler.isVoid());

		parseFile("com/rssl/phizicgate/rsV55/messaging/mock/xml/error_a.xml", handler);

		assertNotNull(handler.getMessageId());
		assertNull(handler.getBody());
		assertFalse(handler.isSuccess());
		assertFalse(handler.isVoid());

		try
		{
			handler.throwException();
			fail("Должна быть ашипка адака");
		}
		catch (Exception e)
		{
			assertNotNull(e);
		}
	}

	private void parseFile(String testFile, RetailResponseHandler handler) throws Exception
	{
		handler.reset();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		InputStream stream = classLoader.getResourceAsStream(testFile);
		assertNotNull("Файл не найден: " + testFile, stream);
		System.out.println("Обработка файла: " + testFile);
		saxParser.parse(stream, handler);
	}

	public void testgetRateCurrency_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getRateCurrency_q");
		message.addParameter("date",         "20.04.2010");
		message.addParameter("time",         "14:48");
		message.addParameter("buySum",       "100.00");
		message.addParameter("buyCurrency",  "0");
		message.addParameter("saleCurrency", "1"); // на RUB падает TODO проверить

		Document document = service.sendOnlineMessage(message, null);

		assertNotNull(document);
	}

	public void testGetApplicationKey_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getApplicationKey_q");
		message.addParameter("appKind", "5");
		Document document = service.sendOnlineMessage(message, null);

		assertNotNull(document);
	}

	public void testGetClientDepositInfo_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getClientDepositInfo_q");
		message.addParameter("clientId", "50");
		message.addParameter("date", "05.01.2006");
		Document doc = message.getDocument();
		Element deposit = XmlHelper.appendSimpleElement(doc.getDocumentElement(), "deposit");
	    XmlHelper.appendSimpleElement(deposit, "referenc", "10000042");

		Document document = service.sendOnlineMessage(doc, null);

		assertNotNull(document);
	}

	public void testgetDepositList_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);
		GateMessage depositListQ = service.createRequest("getDepositList_q");
		//TODO depositListQ.addParameter("filial", "0");в текущеёй версии в rtxml.mac по умолчанию проставляется нулевой филиал

		log.info("Start getDepositList_q");
		Document depositListA = service.sendOnlineMessage(depositListQ, null);
		log.info("End getDepositList_q");
		NodeList list = depositListA.getDocumentElement().getChildNodes();
		assertNotNull(list);
		assertTrue(list.getLength() > 0);

		Set<String> accountTypes = new HashSet<String>();
		for(int i = 0; i < list.getLength(); i++)
		{
			Element paramElem = (Element) list.item(i);
			String accountType = paramElem.getElementsByTagName("accountTypeId").item(0).getTextContent();
			accountTypes.add(accountType);
		}

		GateMessage depositProductQMessage = service.createRequest("depositProduct_q");
		//depositProductQMessage.addParameter("filial", "0");//TODO в текущеёй версии в rtxml.mac по умолчанию проставляется нулевой филиал
		Document depositProductQ = depositProductQMessage.getDocument();
		for (String accountType : accountTypes)
		{
			Element deposit = XmlHelper.appendSimpleElement(depositProductQ.getDocumentElement(), "deposit");
			XmlHelper.appendSimpleElement(deposit, "accountTypeId", accountType);
		}

		log.info("Start depositProduct_q");
		Document depositProductA = service.sendOnlineMessage(depositProductQ, null);
		log.info("End depositProduct_q");
		assertNotNull(depositProductA);
	}
	public void testgetcommissionContact_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("commissionContact_q");
		message.addParameter("senderFnCash",   "DOMO");
	    message.addParameter("senderPsNum", "2");
		message.addParameter("receiverPointCode", "DTMO");
		message.addParameter("currency",          "0");
		message.addParameter("sum",               "12.20");
		Document document = service.sendOnlineMessage(message, null);

		assertNotNull(document);
	}
	public void testgetcommissionPay_q() throws GateException, GateLogicException
	{
/*
import rtxml;

var globalstr:string = "";

macro MyOutHandler( str )
   globalstr = globalstr + str;
end;

setouthandler( @MyOutHandler );
[<?xml version="1.0" encoding="UTF-8"?>
<request>
    <id>cd6d3f9df9dba76f79e5c8461cbb99cc</id>
<commissionPay_q>
<account>42301643000000100000</account>
<filial>1</filial>
<date>Tue Jun 26 00:00:00 MSD 2007</date>
<sum>123.00</sum>
<clientId>45</clientId>
<operationType>65</operationType>
<operationSubspecies>0</operationSubspecies>
<currency>RUR</currency>
</commissionPay_q>
</request>
];

setouthandler;

println( globalstr );

println( XML_Dispatch( globalstr ) );
 */
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("commissionPay_q");
		message.addParameter("account",             "42301643000000100000");
		message.addParameter("filial",              "2");
		message.addParameter("date",                "Tue Jun 26 00:00:00 MSD 2007");
		message.addParameter("sum",                 "12.20");
		message.addParameter("clientId",            "45");
		message.addParameter("operationType",       "63");
		message.addParameter("operationSubspecies", "0");
		message.addParameter("currency",            "RUR");

		Document document = service.sendOnlineMessage(message, null);

		assertNotNull(document);
	}
	public void testgetcommissionContactCP_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("commissionPayCP_q");
		message.addParameter("account", "42301643000000100000");
		message.addParameter("filial", "1");
		message.addParameter("date", "01.01.2007");
		message.addParameter("sum", "100");
		message.addParameter("currency", "RUR");                               
		message.addParameter("clientId", "45");
		message.addParameter("kindCP", "1");
		message.addParameter("recipCP", "1");

		Document document = service.sendOnlineMessage(message, null);

		assertNotNull(document);                                          
	}

	public void testOnLineCarry_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("onLinePaymentTransact_q");
		message.addParameter("appKind", "5");       
		message.addParameter("applicationKey", "01002802131520180001300003");

		Document document = service.sendOnlineMessage(message, null);

		assertNotNull(document);
	}

	public void testAbstract_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getAccountTransactions_q");
		message.addParameter("fromDate", "01.01.20015");
		message.addParameter("toDate", "01.01.2008");

		Document document = service.sendOnlineMessage(message, null);

		assertNotNull(document);
	}
}
