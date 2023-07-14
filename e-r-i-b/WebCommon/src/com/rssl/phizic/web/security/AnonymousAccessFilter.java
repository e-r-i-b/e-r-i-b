package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.*;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import javax.servlet.*;

/**
 * @author Evgrafov
 * @ created 15.08.2007
 * @ $Author: niculichev $
 * @ $Revision: 61618 $
 */

public class AnonymousAccessFilter implements Filter
{
	private static final String APPLICATION = "application";

	private static final SecurityService securityService = new SecurityService();

	private AccessPolicy policy;
	private UserPrincipal anonymous;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		try
		{
			init0(filterConfig);
		}
		catch (SecurityDbException e)
		{
			throw new ServletException(e);
		}
	}

	private void init0(FilterConfig filterConfig) throws ServletException, SecurityDbException
	{
		String app  = filterConfig.getInitParameter(APPLICATION);

		if (StringHelper.isEmpty(app))
			throw new ConfigurationException("AnonymousAccessFilter не задан  параметр " + APPLICATION);

		Application application = Application.valueOf(app);

		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class, application);
		AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class, application);

		String clientName = securityConfig.getAnonymousClientName();
		if (clientName == null)
			return; // значит не нужен

		policy = authenticationConfig.getPolicy(AccessType.anonymous);
		if (policy == null)
			throw new ServletException("Ќе настроена политика доступа дл€ анонимных клиентов");

		Login login = securityService.getClientLogin(clientName);
		if (login == null)
			throw new ServletException("Ќе найден логин пользовател€ "+clientName);

		anonymous = new PrincipalImpl(login, policy, null);
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		AuthModule oldModule = AuthModule.getAuthModule();
		// если уже установлен AuthModule значит есть аутентифицированный пользователь
		if(oldModule == null && anonymous != null)
		{
			// 1. ”станавливаем контекст аутентификации
			AuthenticationContext.setContext(new AuthenticationContext(policy));

			// 2. ”станавливаем модуль аутентификации
			AuthModule.setAuthModule(new AuthModule(anonymous));
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy()
	{
		policy = null;
		anonymous = null;
	}
}