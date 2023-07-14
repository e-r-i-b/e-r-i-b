package com.rssl.phizic.web.util;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * ‘ильтр, управл€ющий состо€нием личного меню
 * @author Pankin
 * @ created 02.08.14
 * @ $Author$
 * @ $Revision$
 */
public class PersonalMenuFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException
	{}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		if (PersonContext.isAvailable())
		{
			String personalMenuCookieName = ConfigFactory.getConfig(WebAPIConfig.class).getPersonalMenuCookieName();
			Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
			for (Cookie cookie : cookies)
			{
				if (personalMenuCookieName.equals(cookie.getName()))
				{
					ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
					Map<String, String> personalMenuMap = person.getStepShowPersonalMenuLinkListMap();
					for (Map.Entry<String, String> entry : personalMenuMap.entrySet())
					{
						if (StringHelper.isNotEmpty(cookie.getValue()) && cookie.getValue().equals(entry.getKey()))
							entry.setValue("1");
						else
							entry.setValue("0");
					}
					break;
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{}
}
