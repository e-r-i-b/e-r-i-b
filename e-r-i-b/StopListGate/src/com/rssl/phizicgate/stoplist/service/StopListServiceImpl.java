package com.rssl.phizicgate.stoplist.service;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.stoplist.ClientStopListState;
import com.rssl.phizic.gate.clients.stoplist.StopListGateException;
import com.rssl.phizic.gate.clients.stoplist.StopListService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.stoplist.configs.StopListConfig;
import com.rssl.phizicgate.stoplist.generated.jaxb.ObjectFactory;
import com.rssl.phizicgate.stoplist.generated.jaxb.Request;
import com.rssl.phizicgate.stoplist.generated.jaxb.Response;
import com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceLocator;
import com.rssl.phizicgate.stoplist.generated.ws.FLSearchServiceSoap_BindingStub;
import com.rssl.phizicgate.stoplist.generated.ws.SearchFL;
import com.rssl.phizicgate.stoplist.generated.ws.SearchFLResponse;
import org.apache.axis.client.Stub;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;

/**
 * @author usachev
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы со стоп-листом
 */
public class StopListServiceImpl extends AbstractService implements StopListService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final String ENCODING = "UTF-8";
	private static volatile JAXBContext jaxbContext;

	/**
	 * Конструктор для инициализации сервиса
	 * @param factory Фабрика
	 */
	public StopListServiceImpl(GateFactory factory)
	{
		super(factory);
	}


	/**
	 * Получение JAXB-контекста
	 * @return JAXB-контекст
	 * @throws GateException
	 */
	private JAXBContext getContext() throws GateException
	{
		try
		{
			if (jaxbContext == null)
			{
				synchronized (StopListServiceImpl.class)
				{
					if (jaxbContext == null)
					{
						jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
					}
				}
			}
			return jaxbContext;
		}
		catch (JAXBException e)
		{
			log.error("Не удалось проинициализировать Стоп-лист сервис", e);
			throw new GateException(e);
		}
	}

	public ClientStopListState check(String INN, String firstName, String middleName, String lastName, String documentSeries, String documentNumber, Calendar dateOfBirth) throws GateLogicException, GateException
	{
		ObjectFactory objectFactory = new ObjectFactory();
		Request request = objectFactory.createRequest();
		request.setInn(INN);
		request.setFirstName(firstName);
		request.setMiddleName(middleName);
		request.setLastName(lastName);
		if (StringHelper.isNotEmpty(documentSeries))
		{
			request.setDocSeries(documentSeries);
		}
		request.setDocNum(documentNumber);
		if (dateOfBirth != null)
		{
			request.setBirthday(DateHelper.formatDateToStringWithPoint(dateOfBirth));
		}
		return check(request);
	}

	/**
	 * Метод серелизует req, отпраляет запрос на сервер и обрабатывает ответ.
	 * @param req Запрос
	 * @return Статус клиента в стоп-листе.
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private ClientStopListState check(Request req) throws GateLogicException, GateException
	{
		String request = marshal(req);
		String resp = sendRequest(request);
		Response response = unmarshal(resp);
		return handleResponse(response);
	}

	/**
	 * Получение вердитка о допустимоти операции изходя из кода ответ.
	 * @param response Ответ
	 * @return Статус клиента в стоп-листе
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private ClientStopListState handleResponse(Response response) throws GateLogicException, GateException
	{
		switch (response.getCode())
		{
			case -2:
				throw new StopListGateException("Внутренняя ошибка web-сервиса: " + response.getMessage());
			case -1:
				throw new StopListGateException("Неверный формат запроса: " + response.getMessage());
			case 0:
				return ClientStopListState.trusted;
			case 1:
				return ClientStopListState.shady;
			case 2:
				return ClientStopListState.blocked;
		}
		return ClientStopListState.blocked;
	}

	/**
	 * Конвертирование объекта {@link Request} в XML
	 * @param request Запрос в объектном представлении
	 * @return Запрос в XML представлении
	 * @throws GateException
	 */
	private String marshal(Request request) throws GateException
	{
		try
		{
			Marshaller marshaller = getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);

			StringWriter writer = new StringWriter();

			marshaller.marshal(request, writer);

			return writer.toString();
		}
		catch (JAXBException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Конвертирование объекта строку в XML
	 * @param responseString Запрос в XML представлении
	 * @return Запрос в объектном представлении {@link Response}
	 * @throws GateException
	 */
	private Response unmarshal(String responseString) throws GateException
	{
		try
		{
			Unmarshaller um = getContext().createUnmarshaller();
			InputStream stream = new ByteArrayInputStream(responseString.getBytes(ENCODING));
			return (Response) um.unmarshal(new StreamSource(stream));
		}
		catch (JAXBException e)
		{
			throw new GateException(e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Неподдерживаемая кодировка " + ENCODING, e);
		}
	}

	/**
	 * Отправка запрос на сервер.
	 * @param request Запрос в виде XML
	 * @return Ответ в виде XML
	 * @throws GateException
	 */
	private String sendRequest(String request) throws GateException
	{
		SearchFL requestMessage = new SearchFL(request);
		SearchFLResponse responseMessage = null;
		Calendar begin = Calendar.getInstance();
		try
		{

			responseMessage = getStub().searchFL(requestMessage);
			return responseMessage.getSearchFLReturn();
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
		finally
		{
			write2Log(DateHelper.diff(Calendar.getInstance(), begin), requestMessage, responseMessage);
		}
	}

	/**
	 * Получение веб-сервиса для отправки запроса
	 * @return Веб-сервис
	 * @throws GateException
	 */
	private FLSearchServiceSoap_BindingStub getStub() throws GateException
	{
		try
		{
			StopListConfig config = ConfigFactory.getConfig(StopListConfig.class);
			ExternalSystemHelper.check(config.getStopListSystemCode());

			FLSearchServiceLocator flSearchServiceLocator = new FLSearchServiceLocator();

			FLSearchServiceSoap_BindingStub stub = (FLSearchServiceSoap_BindingStub) flSearchServiceLocator.getFLSearchServiceSoap();
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, config.getStopListServerURL());

			Long timeOutValue = config.getTimeout();
			if (timeOutValue != null)
			{
				stub.setTimeout(timeOutValue.intValue());
			}
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new GateException("Ошибка при запросе к стоп листу: " + e);
		}
	}

	/**
	 * Логирование запроса/ответа.
	 * @param time Время выполнения
	 * @param requestMessage Запрос
	 * @param responseMessage Ответ
	 */
	private void write2Log(long time, SearchFL requestMessage, SearchFLResponse responseMessage)
	{
		try
		{
			MessageLogWriter codLogService = MessageLogService.getMessageLogWriter();

			MessagingLogEntry logEntry = MessageLogService.createLogEntry();
			logEntry.setExecutionTime(time);
			logEntry.setMessageType("stoplist");
			logEntry.setMessageRequestId("noid");
			logEntry.setMessageRequest(requestMessage.getXmlRequest());
			logEntry.setMessageResponseId("noid");
			String respMessage = responseMessage != null ? responseMessage.getSearchFLReturn() : StringUtils.EMPTY;
			logEntry.setMessageResponse(respMessage);
			logEntry.setSystem(com.rssl.phizic.logging.messaging.System.cod);

			codLogService.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Не удалось произвести запись в лог, при обработке ответа с внешней системы Стоп-лист", e);
		}
	}
}
