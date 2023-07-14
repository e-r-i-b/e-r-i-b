package com.rssl.phizic.web.servlet.jsp;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.servlet.WebAppRequestContext;
import com.rssl.phizic.web.servlet.WebModuleRequestContext;
import com.rssl.phizic.web.servlet.WebRequest;
import com.rssl.phizic.web.servlet.WebRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.jsp.PageContext;

/**
 * @author Erkin
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class WebPageContext extends PageContextWrapper
{
	private final HttpServletRequest request;

	private WebPageContext(PageContext pageContext, HttpServletRequest request)
	{
		super(pageContext);
		this.request = request;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	/**
	 * ������ �������� �������� � �������������� contextPath
	 * @param pageContext - �������� �������� ��������
	 * @param contextPathName - ��� contextPath
	 * ���������� ��������:
	 *  application - ���� contextPath �� �������-��������� ���-����������
	 *  module      - ���� contextPath �� �������-��������� ���-������
	 *  null        - ���� contextPath �� �������� �������-���������
	 * @return �������������� �������� ��������
	 */
	public static WebPageContext create(PageContext pageContext, String contextPathName)
	{
		HttpServletRequest servletRequest = (HttpServletRequest) pageContext.getRequest();

		WebRequestContext requestContext = getRequestContext(pageContext, contextPathName);
		HttpServletRequest pageRequest = new WebPageRequest(servletRequest, requestContext);
		return new WebPageContext(pageContext, pageRequest);
	}

	private static WebRequestContext getRequestContext(PageContext pageContext, String contextPathName)
	{
		HttpServletRequest servletRequest = (HttpServletRequest) pageContext.getRequest();

		WebRequestContext requestContext = null;
		if (StringHelper.isEmpty(contextPathName))
		{
			WebRequest request = WebRequest.fromServletRequest(servletRequest);
			requestContext = request.getContext();
		}
		else if ("application".equals(contextPathName))
		{
			requestContext = WebAppRequestContext.fromRequest(servletRequest);
		}
		else if ("module".equals(contextPathName))
		{
			requestContext = WebModuleRequestContext.fromRequest(servletRequest);
		}
		else
		{
			throw new IllegalArgumentException("������ ������������ contextPathName");
		}

		if (requestContext == null)
			throw new IllegalStateException("�� �������� �������-��������");

		return requestContext;
	}

	private static class WebPageRequest extends HttpServletRequestWrapper
	{
		private final WebRequestContext requestContext;

		private WebPageRequest(HttpServletRequest servletRequest, WebRequestContext requestContext)
		{
			super(servletRequest);
			this.requestContext = requestContext;
		}

		@Override
		public String getContextPath()
		{
			return requestContext.getContextPath().toString();
		}
	}
}
