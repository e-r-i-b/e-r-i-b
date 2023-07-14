package com.rssl.phizic.web.wsclient.webApi;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.web.util.CookieUtil;
import org.apache.axis.transport.http.HTTPConstants;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;

/**
 * @author Jatsky
 * @ created 22.04.14
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
		messageContext.setProperty(HTTPConstants.HEADER_COOKIE, SESSION_COOKIE_PREFIX + CookieUtil.getCookieValueByName(ConfigFactory.getConfig(WebAPIConfig.class).getCookieName()));
		return true;
	}

	public QName[] getHeaders()
	{
		return handlerInfo.getHeaders();
	}
}
