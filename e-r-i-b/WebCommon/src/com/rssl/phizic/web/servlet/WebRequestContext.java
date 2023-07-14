package com.rssl.phizic.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 27.04.12
 * @ $Author$
 * @ $Revision$
 */

public class WebRequestContext
{
	private final HttpServletRequest request;

	private final WebContextPath contextPath;

	protected WebRequestContext(HttpServletRequest request, WebContextPath contextPath)
	{
		this.request = request;
		this.contextPath = contextPath;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public WebContextPath getContextPath()
	{
		return contextPath;
	}
}
