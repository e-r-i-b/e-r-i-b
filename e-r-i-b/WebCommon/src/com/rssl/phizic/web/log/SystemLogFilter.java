package com.rssl.phizic.web.log;

import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA. User: Evgrafov Date: 20.09.2005 Time: 12:47:37
 */
public class SystemLogFilter implements Filter
{
	private String errorRedirectUrl = null;
	private String errorExternalSystemRedirect = null;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		// получаем информацию для редиректа
		errorRedirectUrl = filterConfig.getInitParameter("redirectUrl");
		errorExternalSystemRedirect = filterConfig.getInitParameter("errorExternalSystemRedirect");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException
	{
		try
		{
			filterChain.doFilter(req, resp);
		}
		catch (Exception e) // значит в результате запроса вернулась ошибка
		{
			HttpServletResponse response = (HttpServletResponse) resp;
			HttpServletRequest  request  = (HttpServletRequest)  req;

			ExceptionLogHelper.processExceptionEntry(e,request.getSession(false));

			//Не отображаем сообщение об ошибке в зависимости от атрибута isEmptyErrorPage. Например, когда
			//используется bean:include и есть ошибка в JSP этого бина.
			if (req.getAttribute("isEmptyErrorPage") != null && (Boolean) req.getAttribute("isEmptyErrorPage"))
				return;

			Throwable throwable = ExceptionLogHelper.getRootCause(e);
			if (throwable instanceof InactiveExternalSystemException)
			{
				InactiveExternalSystemException externalSystemException = (InactiveExternalSystemException) throwable;

				ActionMessages messages = HttpSessionUtils.getSessionAttribute(request, ActionMessagesKeys.inactiveExternalSystem.getKey());
				if (messages == null)
					messages = new ActionMessages();

				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(externalSystemException.getMessage(), false));
				request.getSession().setAttribute(ActionMessagesKeys.inactiveExternalSystem.getKey(), messages);

				response.sendRedirect(errorExternalSystemRedirect);
			}
			else if (errorRedirectUrl != null)
			{
				// перенаправление на страницу с сообщением об ошибке
				response.sendRedirect(request.getContextPath() + errorRedirectUrl);
			}
		}
	}

	public void destroy()
	{
	}
}
