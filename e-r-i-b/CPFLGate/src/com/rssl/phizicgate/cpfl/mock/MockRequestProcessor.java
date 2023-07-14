package com.rssl.phizicgate.cpfl.mock;

import com.rssl.phizgate.common.messaging.mock.FilenameRequestHandler;
import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 * Заглушка-обработчик запросов в ЦПФЛ
 */
public class MockRequestProcessor
{
	private static long counter = 0;

	private static final Map<String, MockRequestHandler> succsessHandlers = new HashMap<String, MockRequestHandler>();
	private static final MockRequestHandler errorHandler;

	static
	{
		try
		{
			succsessHandlers.put("recipientListDemand_q", new RecipientListDemandHandler());
			succsessHandlers.put("preparePaymentDemand_q", new PreparePaymentDemandHandler());
			succsessHandlers.put("typePaymentList_q", new TypePaymentListHandler());
			errorHandler = new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/error_a.xml");
		}
		catch (GateException e)
		{
			throw new RuntimeException("Ошибка при инициализации заглушки ЦПФЛ", e);
		}
	}

	/**
	 * Обработать сообщение
	 * @param message сообщение
	 * @return ответ.
	 */
	public static String processMessage(String message) throws GateException
	{
		//получаем DOM-представление запроса.
		Document request;
		try
		{
			request = XmlHelper.parse(message);
		}
		catch (Exception e)
		{
			throw new GateException("Ошибка парсинга запроса", e);
		}

		String requestType = getRequestType(request);//получаем тип запроса
		MockRequestHandler requestHandler = getRequestHandler(requestType); //получаем обработчик запроса

		//обрабатываем запрос
		try
		{
			return XmlHelper.convertDomToText(requestHandler.proccessRequest(request));
		}
		catch (TransformerException e)
		{
			throw new GateException("Ошибка при конвертации ответа в строку", e);
		}
	}

	/**
	 * Получаем тип запроса
	 * запрос имеет вид:
	 * <message>
	 *    <messageId>...</messageId>
	 *    <messageDate>...</messageDate>
	 *    <fromAbonent>...</fromAbonent>
	 *    <ТИП_ЗАПРОСА>...</ТИП_ЗАПРОСА>
	 * </message>
	 * @param request запрос
	 * @return тип запроса(4 элемент в документе)
	 * @throws GateException
	 */
	private static String getRequestType(Document request) throws GateException
	{
		try
		{
			NodeList childNodes = XmlHelper.selectNodeList(request.getDocumentElement(), "/message/*");
			if (childNodes.getLength() < 4)
			{
				throw new GateException("Некорректный формат запроса запроса");
			}
			return childNodes.item(3).getNodeName();
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private static MockRequestHandler getRequestHandler(String requestType) throws GateException
	{
		MockRequestHandler requestHandler = succsessHandlers.get(requestType);
		if (requestHandler == null)
		{
			throw new GateException("неподдерживаемый тип запроса " + requestType);
		}
		//иногда имитируем ошибочный ответ.
		counter++;
		if (counter % 10 == 5)
		{
			//на каждый 10 запрос возвращаем ответ-ошибку.
			return errorHandler;
		}
		return requestHandler;
	}
}
