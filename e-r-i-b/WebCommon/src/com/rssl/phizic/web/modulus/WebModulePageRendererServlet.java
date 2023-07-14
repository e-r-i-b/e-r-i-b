package com.rssl.phizic.web.modulus;

import com.rssl.phizic.web.servlet.WebModuleRequestContext;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.commons.collections.MapUtils;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Erkin
 * @ created 12.02.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * —ервлет, который вызываетс€ перед рендерингом страницы модул€
 */
public class WebModulePageRendererServlet extends HttpServlet
{
	public static final String NAME = "modulePageRenderer";

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		WebModuleRequestContext moduleRequestContext = WebModuleRequestContext.fromRequest(request);
		RequestDispatcher pageRenderer = moduleRequestContext.getPageRenderer();

		// ¬ыкладываем в рендерер страницы сессионные данные
		Map<String,Object> sessionAttributes = moduleRequestContext.getSessionAttributes();
		if (!MapUtils.isEmpty(sessionAttributes))
		{
			HttpSession moduleSession = request.getSession(true);
			HttpSessionUtils.clearSessionAttributes(moduleSession);
			HttpSessionUtils.putSessionAttributes(moduleSession, sessionAttributes);
		}
		try
		{
			pageRenderer.include(request, response);
		}
		finally
		{
			// «абираем из рендерера страницы сессионные данные
			HttpSession moduleSession = request.getSession(false);
			if (moduleSession != null)
				moduleRequestContext.setSessionAttributes(HttpSessionUtils.mapSessionAttributes(moduleSession));
		}
	}
}
