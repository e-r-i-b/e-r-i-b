package com.rssl.phizic.web.common;

import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.web.servlet.HttpServletEditableRequest;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * User: Moshenko
 * Date: 06.04.2012
 * Time: 13:39:49
 * ������ ���� � ������� ��������� � �������������� ���������� � ����������� �� � ����
 * � ������������ ���������, ���������� ������������ ���� �������� ����� ����������� �@�.
 * @deprecated ������������ ������ ��� mAPI < 5.20 � atmAPI
 */
public class ProcessRequestFilter  extends HeaderFilter
{
	private static final String LIST_DELEMITER = "@";

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		//��� ������ mAPI �� 5.20 � ���� ������ �� �����
		if (MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_20))
		{
			chain.doFilter(req, res);
			return;
		}

        HttpServletRequest request  = (HttpServletRequest)  req;

		String queryString = request.getQueryString();
	    String qs = (queryString != null) ? "?" + queryString : "";
	    String url = request.getRequestURI() + qs;

		String requestEncoding = getActionRequestEncoding(request, url);
		if ( requestEncoding != null )
		{
			request.setCharacterEncoding(requestEncoding);
		}

	    HttpServletEditableRequest editableRequest = new HttpServletEditableRequest(request);
		Enumeration<String> paramNames = editableRequest.getParameterNames();
		while(paramNames.hasMoreElements())
		{
			String name = paramNames.nextElement();
			String[] values = editableRequest.getParameterValues(name);
			if (values != null && values.length > 1)
			{
				editableRequest.putParameter(name, StringUtils.join(values, LIST_DELEMITER));
			}
		}

		chain.doFilter(editableRequest, res);
	}
}
