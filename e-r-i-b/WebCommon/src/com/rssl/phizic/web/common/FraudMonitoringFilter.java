package com.rssl.phizic.web.common;

import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ������ ����������� ������������� ��������
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
			HeaderContext.initialize(request);          //���������� ��������� �������� ���������� �������
			RSAContext.initialize(request);             //���������� ��������� ���� �����������

			filterChain.doFilter(request, response);
		}
		finally
		{
			HeaderContext.clear();                      //������ ���������
			RSAContext.clear();                         //������ ���������
		}
	}

	public void destroy() {}
}
