package com.rssl.phizicgate.rsretailV6r20.messaging;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.deposit.DepositProductService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;
import com.rssl.phizicgate.rsretailV6r20.senders.ExternalKeyCreator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashSet;
import java.util.Set;
import javax.xml.transform.TransformerException;

@SuppressWarnings({"JavaDoc"})
public class RetailMessagingTest extends RSRetailV6r20GateTestCaseBase
{
	//проверяет getDepositList_q
	public void testGetDepositsInfo() throws GateException, GateLogicException
	{

		DepositProductService depProdService = GateSingleton.getFactory().service(DepositProductService.class);

		Document document = depProdService.getDepositsInfo(null);
		NodeList nodeList = null;
		try
		{
			nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), "deposit");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}

		Set<String> deposits = new HashSet<String>();

		for (int i = 0; i < nodeList.getLength(); ++i)
			deposits.add(nodeList.item(i).getFirstChild().getFirstChild().getNodeValue());

		assertFalse("Список депозитных продуктов пуст!", deposits.isEmpty());
	}

	public void testCloseDeposit() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("closeDeposit_q");
		message.addParameter("dateCreated","07.04.2008");
		message.addParameter("chargeOffAccount","42306810600001200004");
		message.addParameter("receiverAccount","42301810300005000615");

		Document answerFromRetail = service.sendOnlineMessage(message, null);
		assertNotNull(answerFromRetail);
	}

	public void testInternalTransfer() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		//проверка комиссии на платёж, который хотим вставить
		{
			GateMessage commissionGateMessage = service.createRequest("commissionPay_q");
			Element internalTransferNode = XmlHelper.appendSimpleElement(commissionGateMessage.getDocument().getDocumentElement(), "internalTransfer_q");
			XmlHelper.appendSimpleElement(internalTransferNode, "dateCreated", "03.04.2008");
			XmlHelper.appendSimpleElement(internalTransferNode, "chargeOffAccount", "42301810100001200114");
			Element chargeOffAmountNode = XmlHelper.appendSimpleElement(internalTransferNode, "chargeOffAmount");
			XmlHelper.appendSimpleElement(chargeOffAmountNode, "value", "4000.00");
			XmlHelper.appendSimpleElement(internalTransferNode, "receiverAccount", "42301810300005000615");

			Document document = service.sendOnlineMessage(commissionGateMessage, null);
			assertNotNull(document);
		}
		//вставляем платёж
		{
			GateMessage internalTransferGateMessage = service.createRequest("internalTransfer_q");
			internalTransferGateMessage.addParameter("dateCreated", "03.04.2008");
			internalTransferGateMessage.addParameter("chargeOffAccount", "42301810100001200114");
			Element chargeOffAmountNode = XmlHelper.appendSimpleElement(internalTransferGateMessage.getDocument().getDocumentElement(), "chargeOffAmount");
			XmlHelper.appendSimpleElement(chargeOffAmountNode, "value", "4000.00");

			internalTransferGateMessage.addParameter("receiverAccount", "42301810300005000615");

			Document answerFromRetail = service.sendOnlineMessage(internalTransferGateMessage, null);
			assertNotNull(answerFromRetail);

			//отзываем вставленный платёж
			String externalId = getExternalIdFromXml(answerFromRetail);

			String[] str = externalId.split(",");
			String keyString = str[0].trim();
			String kindString = str[1].trim();

			String key = keyString.split(":")[1].trim();
			String kind = kindString.split(":")[1].trim();

			GateMessage recallGateMessage = service.createRequest("recallDocument_q");

			Element documentNode = XmlHelper.appendSimpleElement(recallGateMessage.getDocument().getDocumentElement(), "document");
			XmlHelper.appendSimpleElement(documentNode, "applicationKind", kind);
			XmlHelper.appendSimpleElement(documentNode, "applicationKey", key);
			String sendId = new ExternalKeyCreator(kind, key).createId();

			Document recallAnswer = service.sendOnlineMessage(recallGateMessage, null);

			String recalledDocumentId = getRecallExternalIdFromXml(recallAnswer);

			assertEquals("Невозможно отозвать документ", recalledDocumentId, sendId);
		}
	}

	public void testAbstract_q() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = gateFactory.service(WebBankServiceFacade.class);

		GateMessage message = service.createRequest("getAccountTransactions_q");
		message.addParameter("accountReferenc", "10012632");
		message.addParameter("fromDate", "01.03.2008");
		message.addParameter("toDate", "01.09.2008");

		Document document = service.sendOnlineMessage(message, new MessageHeadImpl(null, null, null, "", null, null));

		assertNotNull(document);
	}

	public void testOpenDeposit() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("openDeposit_q");
		message.addParameter("dateCreated","07.04.2008");
		message.addParameter("chargeOffAccount","42301810100001200114");
		message.addParameter("depositConditionsId","НАКОП. до180");
		message.addParameter("receiverCurrency","RUR");
		message.addParameter("officeExternalId","1");
		message.addParameter("clientCode","32323");

		Document answerFromRetail = service.sendOnlineMessage(message, null);
		assertNotNull(answerFromRetail);
	}

	public void testExternalTransfer() throws GateException, GateLogicException, TransformerException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage externalTransferGateMessage = service.createRequest("externalTransfer_q");

		externalTransferGateMessage.addParameter("dateCreated", "04.04.2008");
		externalTransferGateMessage.addParameter("chargeOffAccount", "42301810100001200114");

		Element chargeOffAmountNode = XmlHelper.appendSimpleElement(externalTransferGateMessage.getDocument().getDocumentElement(), "chargeOffAmount");
		XmlHelper.appendSimpleElement(chargeOffAmountNode, "value", "80.00");

		externalTransferGateMessage.addParameter("receiverName", "Получатель_Наим");
		externalTransferGateMessage.addParameter("receiverBIC", "044583728");
		externalTransferGateMessage.addParameter("receiverCorAccount", "30101810800000000728");
		externalTransferGateMessage.addParameter("receiverINN", "1234567890");
		externalTransferGateMessage.addParameter("receiverKPP", "123456789");
		externalTransferGateMessage.addParameter("receiverBankName", "АБ \"ВОСХОД\"");
		externalTransferGateMessage.addParameter("receiverAccount", "12345678901234567890");

		Document answerFromRetail = service.sendOnlineMessage(externalTransferGateMessage, null);
		assertNotNull(answerFromRetail);

		String sentExternalTransferId = getExternalIdFromXml(answerFromRetail);

		GateMessage carryDepositGateMessage = service.createRequest("carryDeposit_q");

		Document carryDepositResponse = service.sendOnlineMessage(carryDepositGateMessage, null);

		assertNotNull(carryDepositResponse);

		NodeList list = XmlHelper.selectNodeList(carryDepositResponse.getDocumentElement(), "document");
		int len = list.getLength();
		for (int i = 0; i < len; ++i)
		{
			Element document = (Element) list.item(i);
			String errorCode = XmlHelper.selectSingleNode(document, "errorCode").getTextContent();

			String applicationKind = XmlHelper.selectSingleNode(document, "applicationKind").getTextContent();
			String applicationKey = XmlHelper.selectSingleNode(document, "applicationKey").getTextContent();

			ExternalKeyCreator externalKeyCreator = new ExternalKeyCreator(applicationKind, applicationKey);
			if (externalKeyCreator.createId().equals(sentExternalTransferId))
			{
				assertTrue("0".equals(errorCode));
				break;
			}
		}
	}

	public void testGetClientDepositInfo() throws GateException, GateLogicException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getClientDepositInfo_q");
		message.addParameter("date", "03.04.2008");

		Element depositNode = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "deposit");
		XmlHelper.appendSimpleElement(depositNode, "accountNumber", "42304810600001200006");

		Document answerFromRetail = service.sendOnlineMessage(message, null);
		assertNotNull(answerFromRetail);
	}

	private String getExternalIdFromXml(Document answerFromRetail) throws GateException
	{
		String key = XmlHelper.getSimpleElementValue(answerFromRetail.getDocumentElement(), "applicationKey");
		String kind = XmlHelper.getSimpleElementValue(answerFromRetail.getDocumentElement(), "applicationKind");
		if (key == null || kind == null)
			throw new GateException("Ошибка вставки платежа: key:" + key + ", kind:" + kind);
		ExternalKeyCreator externalKeyCreator = new ExternalKeyCreator(kind, key);
		return externalKeyCreator.createId();
	}

	private String getRecallExternalIdFromXml(Document answerFromRetail) throws GateException
	{
		Element documentElem;
		try
		{
			documentElem = XmlHelper.selectSingleNode(answerFromRetail.getDocumentElement(), "document");
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}

		String errorCode = XmlHelper.getSimpleElementValue(documentElem, "errorCode");
		if (!"0".equals(errorCode))
		{
			String errorText = XmlHelper.getSimpleElementValue(documentElem, "errorText");
			throw new GateException(errorText);
		}

		String key = XmlHelper.getSimpleElementValue(documentElem, "applicationKey");
		String kind = XmlHelper.getSimpleElementValue(documentElem, "applicationKind");
		ExternalKeyCreator externalKeyCreator = new ExternalKeyCreator(kind, key);
		return externalKeyCreator.createId();
	}
}