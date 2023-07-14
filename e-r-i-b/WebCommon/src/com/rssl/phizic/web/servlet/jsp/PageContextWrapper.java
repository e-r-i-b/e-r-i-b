package com.rssl.phizic.web.servlet.jsp;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.ErrorData;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.*;
import javax.servlet.http.HttpSession;

/**
 * @author Erkin
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class PageContextWrapper extends PageContext
{
	private final PageContext delegate;

	/**
	 * ctor
	 * @param delegate - делегат
	 */
	public PageContextWrapper(PageContext delegate)
	{
		this.delegate = delegate;
	}

	public void initialize(Servlet servlet, ServletRequest servletRequest, ServletResponse servletResponse,
	                       String errorPageURL, boolean needsSession, int bufferSize, boolean autoFlush) throws IOException, IllegalStateException, IllegalArgumentException
	{
		delegate.initialize(servlet, servletRequest, servletResponse, errorPageURL, needsSession, bufferSize, autoFlush);
	}

	public void release()
	{
		delegate.release();
	}

	public HttpSession getSession()
	{
		return delegate.getSession();
	}

	public Object getPage()
	{
		return delegate.getPage();
	}

	public ServletRequest getRequest()
	{
		return delegate.getRequest();
	}

	public ServletResponse getResponse()
	{
		return delegate.getResponse();
	}

	public Exception getException()
	{
		return delegate.getException();
	}

	public ServletConfig getServletConfig()
	{
		return delegate.getServletConfig();
	}

	public ServletContext getServletContext()
	{
		return delegate.getServletContext();
	}

	public void forward(String relativeUrlPath) throws ServletException, IOException
	{
		delegate.forward(relativeUrlPath);
	}

	public void include(String relativeUrlPath) throws ServletException, IOException
	{
		delegate.include(relativeUrlPath);
	}

	public void include(String relativeUrlPath, boolean flush) throws ServletException, IOException
	{
		delegate.include(relativeUrlPath, flush);
	}

	public void handlePageException(Exception e) throws ServletException, IOException
	{
		delegate.handlePageException(e);
	}

	public void handlePageException(Throwable throwable) throws ServletException, IOException
	{
		delegate.handlePageException(throwable);
	}

	public BodyContent pushBody()
	{
		return delegate.pushBody();
	}

	public ErrorData getErrorData()
	{
		return delegate.getErrorData();
	}

	public void setAttribute(String name, Object value)
	{
		delegate.setAttribute(name, value);
	}

	public void setAttribute(String name, Object value, int scope)
	{
		delegate.setAttribute(name, value, scope);
	}

	public Object getAttribute(String name)
	{
		return delegate.getAttribute(name);
	}

	public Object getAttribute(String name, int scope)
	{
		return delegate.getAttribute(name, scope);
	}

	public Object findAttribute(String name)
	{
		return delegate.findAttribute(name);
	}

	public void removeAttribute(String name)
	{
		delegate.removeAttribute(name);
	}

	public void removeAttribute(String name, int scope)
	{
		delegate.removeAttribute(name, scope);
	}

	public int getAttributesScope(String name)
	{
		return delegate.getAttributesScope(name);
	}

	public Enumeration getAttributeNamesInScope(int scope)
	{
		return delegate.getAttributeNamesInScope(scope);
	}

	public JspWriter getOut()
	{
		return delegate.getOut();
	}

	public ExpressionEvaluator getExpressionEvaluator()
	{
		return delegate.getExpressionEvaluator();
	}

	public VariableResolver getVariableResolver()
	{
		return delegate.getVariableResolver();
	}

	public JspWriter pushBody(Writer writer)
	{
		return delegate.pushBody(writer);
	}

	public JspWriter popBody()
	{
		return delegate.popBody();
	}
}
