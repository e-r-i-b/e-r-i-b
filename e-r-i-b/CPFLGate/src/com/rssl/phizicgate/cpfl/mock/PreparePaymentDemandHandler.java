package com.rssl.phizicgate.cpfl.mock;

import com.rssl.phizgate.common.messaging.mock.FilenameRequestHandler;
import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.xml.transform.TransformerException;

/**
 * ќбработчик запроса preparePaymentDemand_q
 * User: Novikov_A
 * Date: 26.09.2006
 * Time: 12:19:46
 */
public class PreparePaymentDemandHandler extends MockRequestHandlerBase
{
	private final Map<String, MockRequestHandler> handlers = new HashMap<String, MockRequestHandler>();
	private final MockRequestHandler defaultHandler;
	private MockRequestHandler ncrcHandler; //хендлер дл€ недоговорных получателей
	private MockRequestHandler taxHandler; //хендлер дл€ налогов

	public PreparePaymentDemandHandler() throws GateException
	{
		handlers.put("26275", new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/preparedPayment_a_26275.xml"));
		handlers.put("41900", new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/preparedPayment_a_41900.xml"));
		handlers.put("59930", new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/preparedPayment_a_59930.xml"));
		handlers.put("59900", new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/preparedPayment_a_tax.xml"));
		defaultHandler = new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/preparedPayment_a.xml");
		ncrcHandler = new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/preparedPayment_a_NCRC.xml");
		taxHandler = new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/preparedPayment_a_tax.xml");
	}

	public Document doProcessRequest(Document request) throws GateException
	{
		try
		{
			Element requestRoot = request.getDocumentElement();
			//«агружаем конечный ответ (шаблон)
			Document result = getResponceExample(request);
			Element resultRoot = result.getDocumentElement();
			//ѕолучаем секции specialClient запроса.
			NodeList requestSpecialClientList = XmlHelper.selectNodeList(requestRoot, "/message/preparePaymentDemand_q/specialClient");
			//ѕолучаем список specialClient из шаблона ответа.
			NodeList resultSpecialClientList = XmlHelper.selectNodeList(resultRoot, "/message/preparedPayment_a/specialClient");
			int i = 0;
			int requestSpecialClientsLength = requestSpecialClientList.getLength();
			for (; i < requestSpecialClientsLength; i++)
			{
				//дл€ каждой секции specialClient из шаблона копируем  значение enteredData в шаблон
				copyEnteredDataForSpecialClient(resultSpecialClientList.item(i), requestSpecialClientList.item(i));
			}

			Element resultBody = XmlHelper.selectSingleNode(resultRoot, "/message/preparedPayment_a");
			int exampleSpecialClientLength = resultSpecialClientList.getLength();
			//убираем лишние секции specialClient дл€ последующих запросов
			for (int j = i + 1; j < exampleSpecialClientLength; j++)
			{
				resultBody.removeChild(XmlHelper.selectSingleNode(resultRoot, "/message/preparedPayment_a/specialClient[" + (i + 2) + "]"));
			}

			Element nplatRequisites = XmlHelper.selectSingleNode(resultRoot, "/message/preparedPayment_a/nplatRequisites");
			//если количество секций SpecialClient в запросе и шаблоне одинаково, документ готов к отправке
			if (exampleSpecialClientLength == requestSpecialClientsLength)
			{
				//1) вставл€ем комисиию preparedPayment_a/commision
				Element commissionElement = result.createElement("commision");
				commissionElement.setTextContent("10.02");
				resultBody.insertBefore(commissionElement, resultBody.getFirstChild());
				//2) вставл€ем тег preparedPayment_a/nplatRequisites/requisites с назначением платежа.
				Element requisitesElement = result.createElement("requisites");
				requisitesElement.setTextContent("0018@0@1009732222@");
				nplatRequisites.insertBefore(requisitesElement, nplatRequisites.getFirstChild());

				//ќтдельно обрабатываем случай создани€ ƒѕ.

				if (!StringHelper.isEmpty(XmlHelper.getElementValueByPath(requestRoot, "/message/preparePaymentDemand_q/isForm190")))
				{
					addLongOfferParameters(result);
				}
			}
			return result;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ƒобавить в сообщение доп пол€ по ƒѕ.
	 * @param result сообщение
	 */
	private void addLongOfferParameters(Document result) throws TransformerException
	{
		Element specialClientElement = XmlHelper.selectSingleNode(result.getDocumentElement(), "/message/preparedPayment_a/specialClient");
		Element resultBody = XmlHelper.selectSingleNode(result.getDocumentElement(), "/message/preparedPayment_a");

		Element commisionLabelElement = result.createElement("commisionLabel");
		commisionLabelElement.setTextContent("FIX00");
		resultBody.insertBefore(commisionLabelElement, specialClientElement);

		Element serviceCodeElement = result.createElement("serviceCode");
		serviceCodeElement.setTextContent("000");
		resultBody.insertBefore(serviceCodeElement, specialClientElement);

		Element ackClientBankCanChangeSummElement = result.createElement("ackClientBankCanChangeSumm");
		ackClientBankCanChangeSummElement.setTextContent(String.valueOf(new Random().nextBoolean()));
		resultBody.insertBefore(ackClientBankCanChangeSummElement, specialClientElement);
		int tarifCount = new Random().nextInt(6);
		for (int i = 0; i < tarifCount; i++)
		{
			Element tariffElement = result.createElement("tariff");

			Element element = result.createElement("tariffCode");
			element.setTextContent(String.valueOf(666+i));
			tariffElement.appendChild(element);

			element = result.createElement("tariffName");
			element.setTextContent("Ќазвание тарифа дл€ клиента "+i);
			tariffElement.appendChild(element);

			element = result.createElement("tariffValue");
			element.setTextContent(String.valueOf(100666000+i));
			tariffElement.appendChild(element);
			resultBody.insertBefore(tariffElement, specialClientElement);
		}
	}

	/**
	 * скопировать значение enteredData в элементы /requisites/field из источника в приемник
	 * @param dest приемник копировани€
	 * @param source источник копировани€
	 */
	private void copyEnteredDataForSpecialClient(Node dest, Node source) throws Exception
	{
		NodeList destRequisites = XmlHelper.selectNodeList((Element) dest, "./requisites");
		NodeList srcRequisites = XmlHelper.selectNodeList((Element) source, "./requisites");
		int srcListLength = srcRequisites.getLength();
		for (int i = 0; i < destRequisites.getLength(); i++)
		{
			//дл€ каждого реквизита
			if (i >= srcListLength)
			{
				//если в истонике нет больше реквизитов.. копировать нечего
				return;
			}
			//из источника копируем  значение enteredData в приемник занчени€ из всех полей
			copyEnteredDataForRequisite(destRequisites.item(i), srcRequisites.item(i));
		}
	}

	/**
	 * скопировать значение enteredData в элементы /field из источника в приемник
	 * @param dest приемник копировани€
	 * @param source источник копировани€
	 */
	private void copyEnteredDataForRequisite(Node dest, Node source) throws Exception
	{
		Document destDocument = dest.getOwnerDocument();
		NodeList destFields = XmlHelper.selectNodeList((Element) dest, "./field");
		NodeList srcFields = XmlHelper.selectNodeList((Element) source, "./field");
		int srcListLength = srcFields.getLength();
		for (int i = 0; i < destFields.getLength(); i++)
		{
			//дл€ каждого пол€
			if (i >= srcListLength)
			{
				//если в истонике нет больше полей.. копировать нечего
				return;
			}
			//получаем элемент из источника
			Element enteredData = XmlHelper.selectSingleNode((Element) srcFields.item(i), "./enteredData");
			if (enteredData != null)
			{
				//из источника копируем  значение enteredData в приемник знaчение enteredData
				destFields.item(i).appendChild(destDocument.importNode(enteredData, true));
			}
		}
	}

	/**
	 * ѕолучить последний ответ на подготовку платежа
	 * @param request запрос
	 * @return ответ(шаблон) на подготовку платежа
	 */
	private Document getResponceExample(Document request) throws GateException
	{
		try
		{
			String recipientUniqueNumber = XmlHelper.getElementValueByPath(request.getDocumentElement(), "/message/preparePaymentDemand_q/uniqueNumber");
			if (StringHelper.isEmpty(recipientUniqueNumber))
			{
				String account = XmlHelper.getElementValueByPath(request.getDocumentElement(), "/message/preparePaymentDemand_q/account");
				if (account.startsWith("40101"))
				{
					return taxHandler.proccessRequest(request);
				}
				return ncrcHandler.proccessRequest(request);
			}

			MockRequestHandler handler = handlers.get(recipientUniqueNumber);
			if (handler == null)
			{
				handler = defaultHandler;
			}
			return handler.proccessRequest(request);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}
}