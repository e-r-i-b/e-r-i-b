package com.rssl.auth.csa.wsclient;

import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.generated.AuthServiceLocator;
import com.rssl.auth.csa.wsclient.generated.AuthServiceSoapBindingStub;
import com.rssl.auth.csa.wsclient.requests.RequestData;
import com.rssl.auth.csa.wsclient.responses.ResponseData;
import com.rssl.phizgate.common.ws.WSCSARequestHandler;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.authgate.csa.CSAConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.rmi.RemoteException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.rpc.Stub;

/**
 * Сендер для CSABack
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSABackRequestSender
{
	private static final String VALID_STATUS_CODE = "0";

	/**
	 * Отправить запрос в CSABack
	 * @param request имя запроса
	 * @return тело ответа
	 */
	public ResponseData sendRequest(RequestData request) throws BackException, BackLogicException
	{
		MessageGenerator messageGenerator = MessageGenerator.getInstance();
		String responce = send(messageGenerator.buildMessage(request.getBody()));

		ResponseHandler responseHandler = parceResponce(request.getName(), responce);

		if(!VALID_STATUS_CODE.equals(responseHandler.getStatusCode()))
			ErrorProcessor.processError(
					responseHandler.getStatusCode(),
					responseHandler.getStatusDescription(),
					responseHandler.getResponseData().getBody());

		return responseHandler.getResponseData();
	}

	private ResponseHandler parceResponce(String requestName, String responce) throws BackException
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			ResponseHandler handler = new ResponseHandler(requestName);

			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new InputSource(new StringReader(responce)), handler);

			return handler;
		}
		catch (Exception e)
		{
			throw new BackException(e);
		}
	}

	private String send(String request) throws BackException
	{
		String response = null;
		AuthServiceSoapBindingStub stub = getStub();
		try
		{
			response = stub.process(request);
			return response;
		}
		catch (RemoteException e)
		{
			throw new BackException(e);
		}
	}

	private AuthServiceSoapBindingStub getStub() throws BackException
	{
		try
		{
			AuthServiceLocator locator = new AuthServiceLocator();
			AuthServiceSoapBindingStub stub = (AuthServiceSoapBindingStub)locator.getAuthServicePort();
			Application app = LogThreadContext.getApplication();
			if (app == Application.CSAFront || app == Application.PhizIC)
				WSRequestHandlerUtil.addWSRequestHandlerToService(locator, WSCSARequestHandler.class);
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, ConfigFactory.getConfig(CSAConfig.class).getProperty(CSAConfig.CSA_BACK_URL));
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new BackException(e);
		}
	}
}
