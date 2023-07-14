package com.rssl.phizic.web.modulus;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.build.*;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.servlet.WebAppRequestContext;
import com.rssl.phizic.web.servlet.WebModuleRequestContext;
import com.rssl.phizic.web.servlet.WebRequest;
import com.rssl.phizic.web.servlet.WebRequestContext;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Erkin
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обрабатывает запросы на стороне веб-приложения
 * В случае веб-модульного запроса отдаёт его веб-модулю
 */
public class WebAppRequestProcessor extends TilesRequestProcessor
{
	/**
	 * Связка "url-folder -> веб-модуль"
	 */
	private List<WebModuleBinding> webModuleBindings;

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws ServletException
	{
		super.init(servlet, moduleConfig);

		webModuleBindings = Collections.emptyList();

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		Application app = applicationConfig.getLoginContextApplication();

		// 1. Читаем информацию о веб-приложении и его веб-модулях
		BuildConfig config = BuildConfigIO.readCurrentBuildConfigXml();
		WebApplication webApplication = config.getWebApplication(app.name());
		if (webApplication == null)
			return; // веб-приложение не использует веб-модули

		// 2. Запоминаем связки "url-folder -> веб-модуль"
		webModuleBindings = new ArrayList<WebModuleBinding>(webApplication.getWebModuleBindings());
	}

	@Override
	public void destroy()
	{
		webModuleBindings = null;

		super.destroy();
	}

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		// Помещаем реквест в реквест-контекст приложения
		WebAppRequestContext appRequestContext = getWebAppRequestContext(request);
		WebRequest webRequest = WebRequest.fromServletRequest(request);
		webRequest.setContext(appRequestContext);

		if (processModuleRequest(webRequest, response))
			return;

		super.process(webRequest, response);
	}

	private WebAppRequestContext getWebAppRequestContext(HttpServletRequest request)
	{
		synchronized (request)
		{
			// Реквест-контекст уровня веб-приложения уже создан => используем его
			WebAppRequestContext context = WebAppRequestContext.fromRequest(request);
			if (context != null)
				return context;
			return WebAppRequestContext.create(request, request.getContextPath());
		}
	}

	private boolean processModuleRequest(WebRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		// 1. Определяем веб-модуль, ответственный за обработку запроса
		WebModuleRequestURL moduleURL = parseRequest(request);
		if (moduleURL == null)
			return false; // это не веб-модульный запрос

		// 2. Обрабатываем заголовки запроса
		processHeaders(request, response);

		// 3. Ищем контейнер-рендерер для страницы веб-модуля
		RequestDispatcher containerRenderer = getWebModuleContainerRenderer(request, moduleURL);

		// 4. Ищем обработчик запроса на стороне веб-модуля
		RequestDispatcher requestDispatcher = getWebModuleRequestDispatcher(moduleURL);

		// 5. Создаём контекст модульного запроса
		WebAppRequestContext appRequestContext = WebAppRequestContext.fromRequest(request);
		WebModuleRequestContext moduleRequestContext = WebModuleRequestContext.create(request, appRequestContext, moduleURL);

		EmployeeContext.setEmployeeDataProvider(EmployeeContext.getEmployeeDataProvider(), Application.WebPFP);
		PersonContext.setPersonDataProvider(PersonContext.getPersonDataProvider(),         Application.WebPFP);

		WebRequestContext prevRequestContext = request.getContext();

		HttpSession appSession = request.getSession(false);
		if (appSession != null)
			moduleRequestContext.setSessionAttributes(HttpSessionUtils.mapSessionAttributes(appSession));
		try
		{
			// 6. Переключаемся в контекст модульного запроса
			request.setContext(moduleRequestContext);
			moduleRequestContext.setContainerRenderer(containerRenderer);

			// 7. Передаём запрос обработчику веб-модуля
			requestDispatcher.forward(request, response);
		}
		finally
		{
			// 8. Забираем из веб-модуля данные сессии
			if (appSession != null)
			{
				HttpSessionUtils.clearSessionAttributes(appSession);
				HttpSessionUtils.putSessionAttributes(appSession, moduleRequestContext.getSessionAttributes());
			}

			EmployeeContext.setEmployeeDataProvider(null, Application.WebPFP);
			PersonContext.setPersonDataProvider(null,     Application.WebPFP);

			// 9. Выходим из контекста обработки модульного запроса
            moduleRequestContext.setContainerRenderer(null);
			request.setContext(prevRequestContext);
		}

		return true;
	}

	private WebModuleRequestURL parseRequest(HttpServletRequest request)
	{
		WebPath path = new WebPath(request.getServletPath() + StringHelper.getEmptyIfNull(request.getPathInfo()));
		for (WebModuleBinding binding : webModuleBindings)
		{
			WebPath folder = new WebPath(binding.getUrlFolder());
			if (path.startsWith(folder)) {
				WebPath action = path.subpath(folder.depth());
				return new WebModuleRequestURL(binding.getWebModule(), folder.toString(), action.toString());
			}
		}
		return null;
	}

	private void processHeaders(HttpServletRequest request, HttpServletResponse response)
	{
		// Select a Locale for the current user if requested
		processLocale(request, response);

		// Set the content type and no-caching headers if requested
		processContent(request, response);
		processNoCache(request, response);

		processCachedMessages(request, response);
	}

	private RequestDispatcher getWebModuleContainerRenderer(HttpServletRequest request, WebModuleRequestURL moduleURL) throws ServletException
	{
		WebPath path = new WebPath(moduleURL.getPath());
		ActionConfig actionConfig = findMatchestActionConfig(path);
		if (actionConfig == null)
			throw new ConfigurationException("В struts-config.xml не найден маппинг для контейнера веб-модуля "
					+ moduleURL.getModule().getName() + ". request-path=" + path);

		// Запоминаем конфиг для будущего использования
		request.setAttribute(Globals.MAPPING_KEY, actionConfig);

		String forward = actionConfig.getForward();
		RequestDispatcher containerRenderer = getServletContext().getRequestDispatcher(forward);
		if (containerRenderer == null)
			throw new ServletException("Не найден реквест-диспетчер для " + forward);

		return containerRenderer;
	}

	private RequestDispatcher getWebModuleRequestDispatcher(WebModuleRequestURL moduleURL) throws ServletException
	{
		// 1. Получаем сервлет-контекст веб-модуля
		WebModule module = moduleURL.getModule();
		ServletContext moduleContext = getServletContext().getContext(module.getContextPath());
		if (moduleContext == null) {
			// Либо неправильно указан путь к контексту,
			// либо нет веб-приложения,
			// либо СП не умеет/не допускает кросс-контекстные вызовы
			throw new ServletException("Не удалось получить доступ к веб-модулю " + module.getName());
		}

		// 2. Достаём из сервлет-контекста веб-модуля обработчик, отвечающий за обработку запроса
		RequestDispatcher requestDispatcher = moduleContext.getRequestDispatcher(moduleURL.getAction());
		if (requestDispatcher == null)
			throw new ServletException("Не найден обработчик запроса " + moduleURL.getAction() + " в модуле " + module.getName());
		return requestDispatcher;
	}

	/**
	 * Возвращает такой action-mapping, путь которого совпадает с <wanted> наилучшим образом
	 * Например, если
	 *  wanted = /pfp/journal/view.do
	 *  action1.path = /pfp
	 *  action2.path = /pfp/journal
	 * , тогда action2 подходит больше, чем action1
	 * @param wanted
	 * @return
	 */
	private ActionConfig findMatchestActionConfig(WebPath wanted)
	{
		ActionConfig bestActionConfig = null;
		WebPath bestActionPath = null;
		for (ActionConfig actionConfig : moduleConfig.findActionConfigs())
		{
			if (StringHelper.isEmpty(actionConfig.getPath()))
				continue;

			WebPath actionPath = new WebPath(actionConfig.getPath());
			if (!wanted.startsWith(actionPath))
				continue;

			boolean best = (bestActionConfig == null);
			best=best || (bestActionPath.depth() < actionPath.depth());
			if (best) {
				bestActionConfig = actionConfig;
				bestActionPath = actionPath;
			}
		}

		return bestActionConfig;
	}
}
