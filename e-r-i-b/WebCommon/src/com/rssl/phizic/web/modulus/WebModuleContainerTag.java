package com.rssl.phizic.web.modulus;

import com.rssl.phizic.config.build.WebModule;
import com.rssl.phizic.web.servlet.WebModuleRequestContext;
import com.rssl.phizic.web.servlet.WebRequest;
import com.rssl.phizic.web.servlet.WebRequestContext;
import com.rssl.phizic.web.servlet.WebResponse;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Erkin
 * @ created 10.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class WebModuleContainerTag extends TagSupport
{
	private static final String WEB_MODULE_PAGE_DESCRIPTION_ATTRIBUTE = "webModuleDescription";

	private static final String WEB_MODULE_PAGE_BREADCRUMBS_ATTRIBUTE = "webModulePageBreadcrumbs";

	private static final String WEB_MODULE_PAGE_BODY_ATTRIBUTE = "webModulePageBody";

	private static final String WEB_MODULE_PAGE_FILTER_ATTRIBUTE = "webModulePageFilter";

	private static final String WEB_MODULE_PAGE_SUBMENU_ATTRIBUTE = "webModulePageSubmenu";

	private static final String WEB_MODULE_PAGE_TITLE_ATTRIBUTE = "webModulePageTitle";

	private static final String PAGE_RENDERER_SERVLET_NAME = WebModulePageRendererServlet.NAME;

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int doStartTag() throws JspException
	{
		HttpServletRequest servletRequest = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse servletResponse = (HttpServletResponse) pageContext.getResponse();

		WebRequest request = WebRequest.fromServletRequest(servletRequest);
		WebModuleRequestContext moduleRequestContext = WebModuleRequestContext.fromRequest(request);

		// 1. ѕередаЄм запрос на обработку в jsp веб-модул€
		String modulePageHtml = renderModulePage();

		// 2.A ћодуль согласилс€ использовать контейнер страницы
		if (moduleRequestContext.isUseContainer())
		{
			// ¬ыгружаем на jsp-страницу ответ веб-модул€ в виде атрибутов
			pageContext.setAttribute(WEB_MODULE_PAGE_DESCRIPTION_ATTRIBUTE, moduleRequestContext.getPageDescription());
			pageContext.setAttribute(WEB_MODULE_PAGE_BREADCRUMBS_ATTRIBUTE, moduleRequestContext.getPageBreadcrumbs());
			pageContext.setAttribute(WEB_MODULE_PAGE_FILTER_ATTRIBUTE, moduleRequestContext.getPageFilter());
			pageContext.setAttribute(WEB_MODULE_PAGE_BODY_ATTRIBUTE, modulePageHtml);
			pageContext.setAttribute(WEB_MODULE_PAGE_SUBMENU_ATTRIBUTE, moduleRequestContext.getPageSubmenu());
			pageContext.setAttribute(WEB_MODULE_PAGE_TITLE_ATTRIBUTE, moduleRequestContext.getPageTitle());
			return EVAL_BODY_INCLUDE;
		}

		// 2.B ћодуль отказалс€ использовать контейнер страницы
		else
		{
			try
			{
				// ѕомещаем страницу в ответ веб-приложени€
				servletResponse.getOutputStream().print(modulePageHtml);
				// Ќичего более не выводим
				return SKIP_BODY;
			}
			catch (IOException e)
			{
				throw new JspException(e);
			}
		}
	}

	private String renderModulePage() throws JspException
	{
		HttpServletRequest servletRequest = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse servletResponse = (HttpServletResponse) pageContext.getResponse();

		WebRequest request = WebRequest.fromServletRequest(servletRequest);
		WebModuleRequestContext moduleRequestContext = WebModuleRequestContext.fromRequest(request);
		// ¬сегда отправл€ем в WebModulePageRendererServlet
		RequestDispatcher pageRenderer = getPageRendererServlet(moduleRequestContext.getModule());

		WebRequestContext prevRequestContext = request.getContext();
		try
		{
			// ѕереключаемс€ в режим обработки модульного запроса
			request.setContext(moduleRequestContext);
			WebResponse modulePageResponse = new WebResponse(servletResponse);
			pageRenderer.include(servletRequest, modulePageResponse);
			return modulePageResponse.getString();
		}
		catch (IOException e)
		{
			throw new JspException(e);
		}
		catch (ServletException e)
		{
			throw new JspException(e);
		}
		finally
		{
			// ¬ыходим из режима обработки модульного запроса
			request.setContext(prevRequestContext);
		}
	}

	private RequestDispatcher getPageRendererServlet(WebModule module) throws JspException
	{
		ServletContext servletContext = pageContext.getServletContext();

		ServletContext moduleServletContext = servletContext.getContext(module.getContextPath());
		if (moduleServletContext == null) {
			// Ћибо неправильно указан путь к контексту,
			// либо нет веб-приложени€,
			// либо —ѕ не умеет/не допускает кросс-контекстные вызовы
			throw new JspException("Ќе удалось получить доступ к веб-модулю " + module.getName());
		}

		RequestDispatcher pageRendererServlet = moduleServletContext.getNamedDispatcher(PAGE_RENDERER_SERVLET_NAME);
		if (pageRendererServlet == null)
			throw new JspException("¬ сервлет-контексте веб-модул€ " + module.getName() + " не найден сервлет " + PAGE_RENDERER_SERVLET_NAME);
		return pageRendererServlet;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @param pageContext
	 * @return строка с описанием страницы веб-модул€
	 */
	public static String getWebModulePageDescription(PageContext pageContext)
	{
		return (String) pageContext.getAttribute(WEB_MODULE_PAGE_DESCRIPTION_ATTRIBUTE);
	}

	/**
	 * @param pageContext
	 * @return "’лебные крошки" дл€ страницы веб-модул€
	 */
	public static String getWebModulePageBreadcrumbs(PageContext pageContext)
	{
		return (String) pageContext.getAttribute(WEB_MODULE_PAGE_BREADCRUMBS_ATTRIBUTE);
	}

	/**
	 * @param pageContext
	 * @return html-фрагмент, содержащий страницу веб-модул€
	 */
	public static String getWebModulePageBody(PageContext pageContext)
	{
		return (String) pageContext.getAttribute(WEB_MODULE_PAGE_BODY_ATTRIBUTE);
	}

	/**
	 * @param pageContext
	 * @return html-фрагмент, содержащий фильтр дл€ контейнера страницы веб-модул€
	 */
	public static String getWebModulePageFilter(PageContext pageContext)
	{
		return (String) pageContext.getAttribute(WEB_MODULE_PAGE_FILTER_ATTRIBUTE);
	}

	/**
	 * @param pageContext
	 * @return строка, содержаща€ название вкладки левого меню страницы веб-модул€
	 */
	public static String getWebModulePageSubmenu(PageContext pageContext)
	{
		return (String) pageContext.getAttribute(WEB_MODULE_PAGE_SUBMENU_ATTRIBUTE);
	}

	/**
	 * @param pageContext
	 * @return строка, содержаща€ заголовок страницы веб-модул€
	 */
	public static String getWebModulePageTitle(PageContext pageContext)
	{
		return (String) pageContext.getAttribute(WEB_MODULE_PAGE_TITLE_ATTRIBUTE);
	}
}
