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
	 * ������� �� request-� ip �������, ���� �������� ��������������� ��������� RS-Brigde (http-����� BRIDGE-PEER-ADDRESS),
	 * ����� - ip web-�������
	 * @param req
	 * @return
	 */
	public static String getIpAddress(ServletRequest req)
	{
		String clientIP = ((HttpServletRequest) req).getHeader("BRIDGE-PEER-ADDRESS");
		return StringHelper.isNotEmpty(clientIP) ? clientIP : req.getRemoteAddr();
	}

	/**
	 * �������� ��������� URL'�
	 * @param request �������
	 * @return ��������� URL
	 */
	public static String getUrlParameters(HttpServletRequest request)
	{
		String queryString = request.getQueryString();
		if(StringHelper.isEmpty(queryString))
			return (String)request.getAttribute(QUERY_STRING);
		return queryString;
	}
}
