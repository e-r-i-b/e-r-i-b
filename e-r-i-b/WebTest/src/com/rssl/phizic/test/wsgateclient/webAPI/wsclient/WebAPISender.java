package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import com.rssl.phizgate.common.ws.WSCSARequestHandler;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServiceLocator;
import com.rssl.phizic.test.wsgateclient.webAPI.generated.ERIBClientServiceSoapBindingStub;
import com.rssl.phizic.test.wsgateclient.webAPI.wsclient.exceptions.WebAPIException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;

import java.rmi.RemoteException;
import javax.xml.rpc.Stub;

/**
 * Отправка запроса в WebAPI
 * @author Jatsky
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class WebAPISender
{
	public static final String WEB_API_WS_URL = "/ws/ERIBClientServicePort";

	/**
	 * Отправить запрос в WebAPI
	 * @param request имя запроса
	 * @return тело ответа
	 */
	public String sendRequest(RequestData request, String operation, String ip, String url) throws WebAPIException
	{
		MessageGenerator messageGenerator = MessageGenerator.getInstance();
		return send(messageGenerator.buildMessage(request.getBody(), operation, ip), url);
	}

	private String send(String request, String url) throws WebAPIException
	{
		String response = null;
		ERIBClientServiceSoapBindingStub stub = getStub(url);
		try
		{
			response = stub.process(request);
			return response;
		}
		catch (RemoteException e)
		{
			throw new WebAPIException(e);
		}
	}

	private ERIBClientServiceSoapBindingStub getStub(String url)
	{
		try
		{
			ERIBClientServiceLocator locator = new ERIBClientServiceLocator();
			WSRequestHandlerUtil.addWSRequestHandlerToService(locator, WSWebAPIRequestHandler.class);
			ERIBClientServiceSoapBindingStub stub = (ERIBClientServiceSoapBindingStub) locator.getERIBClientServicePort();
			stub.setMaintainSession(true);
			String shift = PromoterContext.getShift();
			if (!StringHelper.isEmpty(shift))
				WSRequestHandlerUtil.addWSRequestHandlerToService(locator, WSCSARequestHandler.class);
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url+WEB_API_WS_URL);
			return stub;
		}
		catch (javax.xml.rpc.ServiceException e)
		{
			throw new RuntimeException(e);
		}
	}
}
