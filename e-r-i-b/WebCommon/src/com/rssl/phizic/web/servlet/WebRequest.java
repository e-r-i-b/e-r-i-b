package com.rssl.phizic.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Erkin
 * @ created 27.04.12
 * @ $Author$
 * @ $Revision$
 */

public class WebRequest extends HttpServletRequestWrapper
{
	private static final String KEY = WebRequest.class.getName();

	private static final String CONTEXT_KEY = WebRequestContext.class.getName();

	private WebRequest(HttpServletRequest request)
	{
		super(request);
	}

	public static WebRequest fromServletRequest(HttpServletRequest servletRequest)
	{
		if (servletRequest instanceof WebRequest)
			return (WebRequest) servletRequest;

		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (servletRequest)
		{
			WebRequest request = (WebRequest) servletRequest.getAttribute(KEY);
			if (request != null)
				return request;

			//noinspection ReuseOfLocalVariable
			request = new WebRequest(servletRequest);
			servletRequest.setAttribute(KEY, request);
			return request;
		}
	}

	public WebRequestContext getContext()
	{
		return (WebRequestContext) getAttribute(CONTEXT_KEY);
	}

	public void setContext(WebRequestContext context)
	{
		setAttribute(CONTEXT_KEY, context);
	}

	@Override
	public String getContextPath()
	{
		WebRequestContext context = getContext();
		if (context == null)
			throw new IllegalStateException("Не определён реквест-контекст");
		return context.getContextPath().toString();
	}
}
