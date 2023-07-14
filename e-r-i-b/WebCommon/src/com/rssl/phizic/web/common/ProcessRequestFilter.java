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
 * ‘ильтр ищет в запросе параметры с множественными значени€ми и преобразует их к виду
 * с единственным значением, получаемым объединением всех значений через разделитель У@Ф.
 * @deprecated ѕредназначен только дл€ mAPI < 5.20 и atmAPI
 */
public class ProcessRequestFilter  extends HeaderFilter
{
	private static final String LIST_DELEMITER = "@";

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		//дл€ версий mAPI от 5.20 и выше фильтр не нужен
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
