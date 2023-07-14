package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.security.SecurityUtil;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bogdanov
 * @ created 19.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * фильтр для работы с логинами сотрудников, проверка на аетентификацию.
 */

public class EmployeeVersionLoginFilter extends BasicVersionLoginFilter
{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		boolean doChain = true;

		UserVisitingMode visitingMode = ConfirmationManager.getUserVisitingMode();

		if (SecurityUtil.isAuthenticationComplete() && visitingMode == getUserMode())
		{
			//если работаем с автоплатежами в модуле сотрудника и найденный клиент заблокирован или контекст недоступен,
			//то переходим на форму поиска
			String url = request.getRequestURL().toString();
			if (url.contains(Application.PhizIA.name() + "/private/payments/") || url.contains(Application.PhizIA.name() + "/autopayment/"))
			{
				if (PersonHelper.isPersonBlocked())
				{
					ActionMessages messages = new ActionMessages();
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Вы не можете выполнить данную операцию, потому что клиент заблокирован.", false));
					request.getSession().setAttribute(ActionMessagesKeys.sessionError.getKey(), messages);
					doChain = false;
					sendRedirect(request, response, "/person/search.do");
				}

				if (!PersonContext.isAvailable())
				{
					doChain = false;
					sendRedirect(request, response, "/person/search.do");
				}
			}
			else if (url.contains(Application.PhizIA.name() + "/person/pfp/") && !PersonContext.isAvailable())
			{
				doChain = false;
				sendRedirect(request, response, "/pfp/person/search.do");
			}
			else if (url.contains("/login.do"))
			{
				StoreManager.getCurrentStore().clear();
			}
		}
		else
			doChain = makeUrlAndRedirect(request, response);

		if (doChain)
			filterChain.doFilter(req, resp);
	}
}
