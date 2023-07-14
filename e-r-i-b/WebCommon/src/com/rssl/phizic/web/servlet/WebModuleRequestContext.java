package com.rssl.phizic.web.servlet;

import com.rssl.phizic.config.build.WebModule;
import com.rssl.phizic.web.modulus.WebModuleRequestURL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 27.04.12
 * @ $Author$
 * @ $Revision$
 */

public class WebModuleRequestContext extends WebRequestContext
{
	private static final String KEY = WebModuleRequestContext.class.getName();

	///////////////////////////////////////////////////////////////////////////
	// ����, ����������� ���-�����������

	private final WebModule module;

	/**
	 * ���� "���� -> ����������" ��������� ������ ���-����������
	 */
	private Map<String, Object> sessionAttributes = Collections.emptyMap();

	/**
	 * �������� ��� ��������� ���������� �������� ���-������
	 * �� ������� ���-����������
	 */
	private RequestDispatcher containerRenderer;

	///////////////////////////////////////////////////////////////////////////
	// ����, ����������� ���-�������

	/**
	 * ������ "������������ ��������� �������� ���-������".
	 * false = �������� ����� ���������� ��� ����������
	 * WebModulePageTag ���������� ���� � true
	 */
	private boolean useContainer = false;

	/**
	 * �������� ��� ��������� �������� ���-������
	 */
	private RequestDispatcher pageRenderer;

	/**
	 * ����� � ���������� ���������� ��������
	 */
	private String pageDescription;

	/**
	 * ������� ������ � ���������� ���������� ��������
	 */
	private String pageBreadcrumbs;

	private String pageFilter;

	private String pageSubmenu;

	private String pageTitle;

	///////////////////////////////////////////////////////////////////////////

	private WebModuleRequestContext(WebModule module, HttpServletRequest request, WebContextPath contextPath)
	{
		super(request, contextPath);
		this.module = module;
	}

	/**
	 * ������ �������-�������� ���-������
	 * �����! ������ ���������� �� ������� ���-����������
	 * @param request - ������� ���-����������
	 * @param appRequestContext
	 * @param moduleURL
	 * @return ����� �������-��������
	 */
	public static WebModuleRequestContext create(HttpServletRequest request, WebAppRequestContext appRequestContext, WebModuleRequestURL moduleURL)
	{
		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (request)
		{
			if (request.getAttribute(KEY) != null)
				throw new IllegalStateException("�������-�������� ������ ���-������ ��� ������");
			String applicationPath = appRequestContext.getContextPath().getApplicationPath();
			WebContextPath contextPath = new WebContextPath(applicationPath, moduleURL.getFolder());
			WebModuleRequestContext context = new WebModuleRequestContext(moduleURL.getModule(), request, contextPath);
			request.setAttribute(KEY, context);
			return context;
		}
	}

	public static WebModuleRequestContext fromRequest(ServletRequest request)
	{
		//noinspection SynchronizationOnLocalVariableOrMethodParameter
		synchronized (request)
		{
			return (WebModuleRequestContext) request.getAttribute(KEY);
		}
	}

	/**
	 * @return ���-������
	 */
	public WebModule getModule()
	{
		return module;
	}

	public Map<String, Object> getSessionAttributes()
	{
		return Collections.unmodifiableMap(sessionAttributes);
	}

	public void setSessionAttributes(Map<String, Object> sessionAttributes)
	{
		if (sessionAttributes == null)
			this.sessionAttributes = null;
		else this.sessionAttributes = new HashMap<String, Object>(sessionAttributes);
	}

	public RequestDispatcher getContainerRenderer()
	{
		return containerRenderer;
	}

	public void setContainerRenderer(RequestDispatcher containerRenderer)
	{
		this.containerRenderer = containerRenderer;
	}

	public boolean isUseContainer()
	{
		return useContainer;
	}

	public void setUseContainer(boolean useContainer)
	{
		this.useContainer = useContainer;
	}

	public RequestDispatcher getPageRenderer()
	{
		return pageRenderer;
	}

	public void setPageRenderer(RequestDispatcher pageRenderer)
	{
		this.pageRenderer = pageRenderer;
	}

	public String getPageDescription()
	{
		return pageDescription;
	}

	public void setPageDescription(String pageDescription)
	{
		this.pageDescription = pageDescription;
	}

	public String getPageBreadcrumbs()
	{
		return pageBreadcrumbs;
	}

	public void setPageBreadcrumbs(String pageBreadcrumbs)
	{
		this.pageBreadcrumbs = pageBreadcrumbs;
	}

	public String getPageFilter()
	{
		return pageFilter;
	}

	public void setPageFilter(String pageFilter)
	{
		this.pageFilter = pageFilter;
	}

	public String getPageSubmenu()
	{
		return pageSubmenu;
	}

	public void setPageSubmenu(String pageSubmenu)
	{
		this.pageSubmenu = pageSubmenu;
	}

	public String getPageTitle()
	{
		return pageTitle;
	}

	public void setPageTitle(String pageTitle)
	{
		this.pageTitle = pageTitle;
	}
}
