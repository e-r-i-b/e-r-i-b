package com.rssl.phizic.web.wsclient.webApi;

import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.wsclient.webApi.exceptions.WebAPIException;
import com.rssl.phizic.web.wsclient.webApi.generated.ERIBClientServiceLocator;
import com.rssl.phizic.web.wsclient.webApi.generated.ERIBClientServiceSoapBindingStub;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import javax.xml.rpc.Stub;

/**
 * Отправка запроса в WebAPI
 * @author Jatsky
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class WebAPISender
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	/**
	 * Отправить запрос в WebAPI
	 * @param request имя запроса
	 * @return тело ответа
	 */
	public String sendRequest(RequestData request, String operation, String ip) throws WebAPIException
	{
		MessageGenerator messageGenerator = MessageGenerator.getInstance();
		return send(messageGenerator.buildMessage(request.getBody(), operation, ip));
	}

	private String send(String request) throws WebAPIException
	{
		String response = null;
		ERIBClientServiceSoapBindingStub stub = getStub();
		try
		{
			log.debug("Отправка LOGOFF в WebAPI. request = " + request);
			response = stub.process(request);
			return response;
		}
		catch (RemoteException e)
		{
			throw new WebAPIException(e);
		}
	}

	private ERIBClientServiceSoapBindingStub getStub()
	{
		try
		{
			ERIBClientServiceLocator locator = new ERIBClientServiceLocator();
			WSRequestHandlerUtil.addWSRequestHandlerToService(locator, WSWebAPIRequestHandler.class);
			ERIBClientServiceSoapBindingStub stub = (ERIBClientServiceSoapBindingStub) locator.getERIBClientServicePort();
			stub.setMaintainSession(true);
			WebAPIConfig webAPIConfig = ConfigFactory.getConfig(WebAPIConfig.class);

			String endpointAddress = null;
			try
			{
				endpointAddress = String.format(webAPIConfig.getWebApiWsUrl(), webAPIConfig.getWebApiWsProtocol(), ApplicationConfig.getIt().getApplicationHost().getHostAddress(), ConfigFactory.getConfig(BuildContextConfig.class).getApplicationPort());
			}
			catch (UnknownHostException e)
			{
				throw new RuntimeException("Отправка LOGOFF в WebAPI. Ошибка получения адреса сервиса.");
			}

			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
			log.debug("Отправка LOGOFF в WebAPI. endpoint address = " + endpointAddress);
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new RuntimeException(e);
		}
	}
}
