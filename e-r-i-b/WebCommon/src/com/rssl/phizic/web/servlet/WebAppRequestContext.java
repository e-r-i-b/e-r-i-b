package com.rssl.phizic.web.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 27.04.12
 * @ $Author$
 * @ $Revision$
 */

public class WebAppRequestContext extends WebRequestContext
{
	private static final String KEY = WebAppRequestContext.class.getName();

	///////////////////////////////////////////////////////////////////////////

	private WebAppRequestContext(HttpServletRequest request, WebContextPath contextPath)
	{
		super(request, contextPath);
	}

	/**
	 * ������ �������-�������� ���-����������
	 * �����! ������ ���������� �� ������� ���-����������
	 * @param request - ������� ���-����������
	 * @param applicationPath
	 * @return ����� �������-��������
	 */
	public static WebAppRequestContext create(HttpServletRequest request, String applicationPath)
	{
		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (request)
		{
			if (request.getAttribute(KEY) != null)
				throw new IllegalStateException("�������-�������� ������ ���-���������� ��� ������");
			WebContextPath contextPath = new WebContextPath(applicationPath);
			WebAppRequestContext context = new WebAppRequestContext(request, contextPath);
			request.setAttribute(KEY, context);
			return context;
		}
	}

	public static WebAppRequestContext fromRequest(ServletRequest request)
	{
		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (request)
		{
			return (WebAppRequestContext) request.getAttribute(KEY);
		}
	}
}
