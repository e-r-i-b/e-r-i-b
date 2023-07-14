package com.rssl.phizic.test.wsgateclient.webAPI.wsclient;

import com.rssl.phizic.test.web.webAPI.WebAPITestContext;
import com.rssl.phizic.utils.StringHelper;
import org.apache.axis.transport.http.HTTPConstants;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;

/**
 * @author Pankin
 * @ created 26.03.14
 * @ $Author$
 * @ $Revision$
 */
public class WSWebAPIRequestHandler extends GenericHandler
{
	private static final String SESSION_COOKIE_PREFIX = "JSESSIONID=";

	private HandlerInfo handlerInfo;

	public void init(HandlerInfo info)
	{
		handlerInfo = info;
	}

	public boolean handleRequest(MessageContext messageContext)
	{
		messageContext.setProperty(HTTPConstants.HEADER_COOKIE, SESSION_COOKIE_PREFIX + WebAPITestContext.getSession());
		return true;
	}

	public boolean handleResponse(MessageContext context)
	{
		String cookie = (String) context.getProperty(HTTPConstants.HEADER_COOKIE);
		if (StringHelper.isNotEmpty(cookie))
			WebAPITestContext.setSession(cookie.replace(SESSION_COOKIE_PREFIX, ""));
		return true;
	}

	public QName[] getHeaders()
	{
		return handlerInfo.getHeaders();
	}
}
