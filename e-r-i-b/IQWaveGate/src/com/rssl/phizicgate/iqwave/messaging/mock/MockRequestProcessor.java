package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author vagin
 * @ created 11.04.2011
 * @ $Author$
 * @ $Revision$
 * Заглушка-обработчик запросов IqWave
 */
public class MockRequestProcessor
{
	private static final Map<String, MockRequestHandler> succsessHandlers = new HashMap<String, MockRequestHandler>();

	static
	{
		try
		{
			OfflineTicketResponseHandler offlineTicketResponseHandler = new OfflineTicketResponseHandler();

			succsessHandlers.put(Constants.CARDS_TRANSFER_REQUEST, new CardToCardResponseHandler());
			succsessHandlers.put(Constants.SIMPLE_PAYMENT_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.PAYMENT_FNS_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.PAYMENT_MGTS_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.PAYMENT_GKH_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.PAYMENT_MOSENERGO_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.PAYMENT_ROSTELECOM_REQUEST,   offlineTicketResponseHandler);
			succsessHandlers.put(Constants.FNS_FREE_PAYMENT_REQUEST,   new FNSFreePaymentRequestHandler());
			// результатом выполнение запросов на регистрацию редактирование и отмены автоплатежа является квитанция об исполнении
			succsessHandlers.put(Constants.AUTO_PAY_REGISTER_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.AUTO_PAY_CORRECTION_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.AUTO_PAY_CANCEL_REQUEST, offlineTicketResponseHandler);

			succsessHandlers.put(Constants.PAYMENT_DEBTS_FNS_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsFnsResponse.xml"));
			succsessHandlers.put(Constants.PAYMENT_DEBTS_MGTS_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentDebtsMgtsResponse.xml"));
			succsessHandlers.put(Constants.PAYMENT_DEBTS_GKH_REQUEST, new GKHDDetsRequestHandler());
			succsessHandlers.put(Constants.PAYMENT_DEBTS_ROSTELECOM_REQUEST, new RostelecomDebtsRequestHandler());
			succsessHandlers.put(Constants.AUTO_PAY_STATE_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AutoPayStateResponse.xml"));
			succsessHandlers.put(Constants.AUTO_PAY_LIST_REQUEST, new RandomIQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AutoPayListResponse.xml"));
			succsessHandlers.put(Constants.AUTO_PAY_REPORT_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AutoPayReportResponse.xml"));
			succsessHandlers.put(Constants.AUTO_PAY_GET_CLIENT_TYPE_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AutoPayGetClientTypeResponse.xml"));
			succsessHandlers.put(Constants.OPER_STATUS_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/OperStatusResponse.xml"));

			succsessHandlers.put(Constants.PAYMENT_CHECK_RES_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/PaymentCheckRESResponse.xml"));
			succsessHandlers.put(Constants.PAYMENT_RES_REQUEST, offlineTicketResponseHandler);

			succsessHandlers.put(Constants.CARD_TO_CARD_INFO_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/CardToCardInfoResponse.xml"));

			succsessHandlers.put(Constants.LOYALTY_GET_BALANCE_REQUEST, new LoyaltyRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/LoyaltyGetBalanceResponse.xml"));
			succsessHandlers.put(Constants.LOYALTY_GET_STATEMENT_REPORT_REQUEST, new LoyaltyStatementRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/LoyaltyGetStatementReportResponse.xml"));
			succsessHandlers.put(Constants.LOYALTY_GET_OFFERS_REQUEST, new LoyaltyRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/LoyaltyGetOffersResponse.xml"));
			succsessHandlers.put(Constants.LOYALTY_REGISTER_REQUEST, new LoyaltyRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/LoyaltyRegisterResponse.xml"));

			succsessHandlers.put(Constants.AEROEXPRESS_SCHEDULE_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AeroexpressScheduleResponse.xml"));
			succsessHandlers.put(Constants.AEROEXPRESS_PRICELIST_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AeroexpressPricelistResponse.xml"));
			succsessHandlers.put(Constants.AEROEXPRESS_FREE_SEATS_GLOBAL_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AeroexpressFreeSeatsGlobalResponse.xml"));
			succsessHandlers.put(Constants.AEROEXPRESS_FREE_SEATS_DETAIL_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AeroexpressFreeSeatsDetailRequest.xml"));
			succsessHandlers.put(Constants.AEROEXPRESS_GET_TICKETS_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AeroexpressGetTicketResponse.xml"));
			succsessHandlers.put(Constants.AEROEXPRESS_PAY_TICKETS_REQUEST, new IQWFilenameRequestHandler("com/rssl/phizicgate/iqwave/messaging/mock/xml/AeroexpressPayTicketResponse.xml"));

			succsessHandlers.put(Constants.SIMPLE_PAYMENT_ECOMMERCE_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.REFUND_SIMPLE_PAYMENT_REQUEST, offlineTicketResponseHandler);
			succsessHandlers.put(Constants.REVERSAL_SIMPLE_PAYMENT_REQUEST, offlineTicketResponseHandler);
		}
		catch (GateException e)
		{
			throw new RuntimeException("Ошибка при инициализации заглушки IqWave", e);
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
	 * <Тип запроса>
	 *  <Head>
	 *      <MessUID>
	 *          <MessageId>..</MessageId>
	 *          <MessageDate>..</MessageDate>
	 *          <FromAbonent>.</FromAbonent>
	 *      </MessUID>
	 *      <MessType>...</MessType>
	 *      <Version>...<Version>
	 *  </Head>
	 *  <Body>...</Body>
	 * </Тип запроса>
	 * @param request запрос
	 * @return тип запроса
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	private static String getRequestType(Document request) throws GateException
	{
		//cогласно спецификации Тип xml сообщения определяется именем корневого элемента, которое всегда совпадает с значением элемента  <MessType>
		String type = XmlHelper.getSimpleElementValue(request.getDocumentElement(), "MessType");
		if (StringHelper.isEmpty(type))
		{
			throw new GateException("Некорректный формат запроса запроса");
		}
		return type;
	}

	private static MockRequestHandler getRequestHandler(String requestType) throws GateException
	{
		MockRequestHandler requestHandler = succsessHandlers.get(requestType);
		if (requestHandler == null)
		{
			throw new GateException("неподдерживаемый тип запроса " + requestType);
		}
		return requestHandler;
	}
}