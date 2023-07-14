package com.rssl.phizgate.messaging.internalws.client;

import com.rssl.phizgate.common.ws.WSCSARequestHandler;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizgate.messaging.internalws.InternalServiceConfig;
import com.rssl.phizgate.messaging.internalws.client.generated.InternalServiceLocator;
import com.rssl.phizgate.messaging.internalws.client.generated.InternalServiceSoapBindingStub;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.rmi.RemoteException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.rpc.Stub;

import static com.rssl.phizgate.messaging.internalws.Constants.VALID_STATUS_CODE;
/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class InternalServiceSender
{
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_GATE.toValue());

	private String destinationSystemName; //система, получатель сообщений

	/**
	 * ctor
	 * @param destinationSystemName - система, получатель сообщений
	 */
	public InternalServiceSender(String destinationSystemName)
	{
		this.destinationSystemName = destinationSystemName;
	}

	/**
	 * Отправить запрос
	 * @param request имя запроса
	 * @return тело ответа
	 */
	public ResponseData sendRequest(RequestData request) throws GateException, GateLogicException
	{
		try
		{
			MessageGenerator messageGenerator = MessageGenerator.getInstance();
			String responce = send(messageGenerator.buildMessage(request.getBody(), destinationSystemName));

			ResponseHandler responseHandler = parseResponce(responce);

			if(!VALID_STATUS_CODE.equals(responseHandler.getStatusCode()))
				InternalServiceConfig.getInstance().getErrorHandler(responseHandler.getStatusCode()).process(
						responseHandler.getStatusCode(),
						responseHandler.getStatusDescription(),
						responseHandler.getResponseData().getBody());

			return responseHandler.getResponseData();
		}
		catch (GateException ge)
		{
			throw ge;
		}
		catch (GateLogicException gle)
		{
			throw gle;
		}
		catch (Exception e)
		{
			log.error("InternalServiceSender: необработанное исключение", e);
			throw new RuntimeException(e);
		}
	}

	private ResponseHandler parseResponce(String responce) throws GateException
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			ResponseHandler handler = new ResponseHandler();

			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(responce)), handler);

			return handler;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private String send(String request) throws GateException
	{
		InternalServiceSoapBindingStub stub = getStub();
		try
		{
			return stub.process(request);
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
	}

	private InternalServiceSoapBindingStub getStub() throws GateException
	{
		try
		{
			InternalServiceLocator locator = new InternalServiceLocator();
			InternalServiceSoapBindingStub stub = (InternalServiceSoapBindingStub)locator.getInternalServicePort();
			String shift = PromoterContext.getShift();
			if (!StringHelper.isEmpty(shift))
				WSRequestHandlerUtil.addWSRequestHandlerToService(locator, WSCSARequestHandler.class);

			String url = ConfigFactory.getConfig(GateConnectionConfig.class).getInternalSystemWSAddress(destinationSystemName);
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
			stub.setTimeout(getServiceTimeOut());
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new GateException(e);
		}
	}

	//Получение размера таймаута сервиса.
	protected int getServiceTimeOut()
	{
		String timeout = ConfigFactory.getConfig(PropertyConfig.class).getProperty("com.rssl.phizgate.messaging.internalws.client.internalService.timeout");
		return StringHelper.isEmpty(timeout) ? 0 : Integer.parseInt(timeout);
	}
}
