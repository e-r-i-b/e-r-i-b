package com.rssl.phizic.web.wsclient.webApi;

import com.rssl.phizic.web.wsclient.webApi.exceptions.WebAPIException;
import com.rssl.phizic.web.wsclient.webApi.exceptions.WebAPILogicException;

/**
 * @author Jatsky
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class WebAPIRequestHelper
{
	private static final WebAPISender sender = new WebAPISender();

	/**
	 * Послать запрос на выход.
	 * @param ip IP-адрес машины конечного клиента
	 * @return ответ
	 */
	public static String sendLogoffRq(String ip) throws WebAPIException, WebAPILogicException
	{
		String responseData = sender.sendRequest(new LogoffRequestData(), RequestConstants.LOGOFF_OPERATION, ip);
		return responseData;
	}
}
