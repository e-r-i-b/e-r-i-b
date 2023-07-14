package com.rssl.phizic.web.modulus;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.AppServerType;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.common.SessionStore;
import com.rssl.phizic.web.servlet.WebAppRequestContext;
import com.rssl.phizic.web.servlet.WebModuleRequestContext;
import com.rssl.phizic.web.servlet.WebRequest;
import com.rssl.phizic.web.servlet.WebRequestContext;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.util.RequestUtils;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * @author Erkin
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class WebModuleRequestProcessor extends TilesRequestProcessor
{
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		// 1. Достаём контекст запроса
		WebModuleRequestContext moduleRequestContext = WebModuleRequestContext.fromRequest(request);
		if (moduleRequestContext == null) {
			// Веб-модуль позвали не из веб-приложения
			response.sendError(SC_BAD_REQUEST, "Не найден контекст-реквест");
			return;
		}

		// 2. Настраиваем WebContext и Store веб-модуля
		SessionStore moduleStore = new SessionStore();

		HttpServletRequest appRequest = WebContext.getCurrentRequest();
		HttpServletResponse appResponse = WebContext.getCurrentResponce();
		Store appStore = StoreManager.getCurrentStore();

		WebContext.setCurrentRequest(request);
		WebContext.setCurrentResponce(response);
		StoreManager.setCurrentStore(moduleStore);

		// Выкладываем в веб-модуль сессионные данные
		Map<String,Object> sessionAttributes = moduleRequestContext.getSessionAttributes();
		if (!MapUtils.isEmpty(sessionAttributes))
		{
			HttpSession moduleSession = request.getSession(true);
			HttpSessionUtils.clearSessionAttributes(moduleSession);
			HttpSessionUtils.putSessionAttributes(moduleSession, sessionAttributes);
		}

		try
		{
			// 3. Передаём запрос в экшены
			super.process(request, response);
		}
		finally
		{
			// Запоминаем сессионные данные веб-модуля
			HttpSession moduleSession = request.getSession(false);
			if (moduleSession != null)
				moduleRequestContext.setSessionAttributes(HttpSessionUtils.mapSessionAttributes(moduleSession));

			// 4. Восстанавливаем WebContext и Store веб-приложения
			WebContext.setCurrentRequest(appRequest);
			WebContext.setCurrentResponce(appResponse);
			StoreManager.setCurrentStore(appStore);
		}
	}

	@Override
	protected void processForwardConfig(HttpServletRequest servletRequest, HttpServletResponse response, ForwardConfig forward) throws IOException, ServletException
	{
		// Required by struts contract
		if (forward == null)
			return;

		String forwardPath = forward.getPath();
		String uri = null;

		// paths not starting with / should be passed through without any processing
		// (ie. they're absolute)
		if (forwardPath.startsWith("/")) {
		    uri = RequestUtils.forwardURL(servletRequest, forward, null);    // get module relative uri
		} else {
		    uri = forwardPath;
		}

		WebRequest request = WebRequest.fromServletRequest(servletRequest);
		WebAppRequestContext appRequestContext = WebAppRequestContext.fromRequest(request);
		WebModuleRequestContext moduleRequestContext = WebModuleRequestContext.fromRequest(request);

		if (forward.getRedirect())
		{
		    if (uri.startsWith("/"))
		        uri = moduleRequestContext.getContextPath().toString() + uri;
		    response.sendRedirect(response.encodeRedirectURL(uri));
		}
		else
		{
			RequestDispatcher containerRenderer = moduleRequestContext.getContainerRenderer();
			RequestDispatcher pageRenderer = getServletContext().getRequestDispatcher(uri);

			WebRequestContext prevRequestContext = request.getContext();

			request.setContext(appRequestContext);
			moduleRequestContext.setPageRenderer(pageRenderer);

			// Передаём сессионные данные контейнер-рендереру
			HttpSession moduleSession = request.getSession(false);
			if (moduleSession != null)
				moduleRequestContext.setSessionAttributes(HttpSessionUtils.mapSessionAttributes(moduleSession));
			try
			{
				//это следствие различной работы oc4j и WebSphere
				BuildContextConfig config = ConfigFactory.getConfig(BuildContextConfig.class);
				if(config.getApplicationServerType().equals(AppServerType.websphere))
					containerRenderer.forward(servletRequest, response);
				else
					containerRenderer.include(servletRequest, response);
			}
			finally
			{
				// Забираем сессионные данные из контейнер-рендерера
				if (moduleSession != null)
				{
					HttpSessionUtils.clearSessionAttributes(moduleSession);
					HttpSessionUtils.putSessionAttributes(moduleSession, moduleRequestContext.getSessionAttributes());
				}

				moduleRequestContext.setPageRenderer(null);
				request.setContext(prevRequestContext);
			}
		}
	}
}
