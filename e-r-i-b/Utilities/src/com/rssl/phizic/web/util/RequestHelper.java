package com.rssl.phizic.web.util;

import com.rssl.phizic.utils.StringHelper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Dorzhinov
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class RequestHelper
{
	private static final String QUERY_STRING = "javax.servlet.forward.query_string";

	/**
	 * ƒостает из request-а ip клиента, если заполнен соответствующий заголовок RS-Brigde (http-хедер BRIDGE-PEER-ADDRESS),
	 * иначе - ip web-сервера
	 * @param req
	 * @return
	 */
	public static String getIpAddress(ServletRequest req)
	{
		String clientIP = ((HttpServletRequest) req).getHeader("BRIDGE-PEER-ADDRESS");
		return StringHelper.isNotEmpty(clientIP) ? clientIP : req.getRemoteAddr();
	}

	/**
	 * ѕолучить параметры URL'а
	 * @param request реквест
	 * @return параметры URL
	 */
	public static String getUrlParameters(HttpServletRequest request)
	{
		String queryString = request.getQueryString();
		if(StringHelper.isEmpty(queryString))
			return (String)request.getAttribute(QUERY_STRING);
		return queryString;
	}
}
