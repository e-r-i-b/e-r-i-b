package com.rssl.phizic.web.common;

import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ‘ильтр профилакити мошейнических операций
 *
 * @author khudyakov
 * @ created 30.07.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request   = (HttpServletRequest)  servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		try
		{
			HeaderContext.initialize(request);          //заполнение контекста значений заголовков запроса
			RSAContext.initialize(request);             //заполнение контекста фрод мониторинга

			filterChain.doFilter(request, response);
		}
		finally
		{
			HeaderContext.clear();                      //чистка контекста
			RSAContext.clear();                         //чистка контекста
		}
	}

	public void destroy() {}
}
