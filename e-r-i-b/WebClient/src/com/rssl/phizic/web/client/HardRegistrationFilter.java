package com.rssl.phizic.web.client;

import com.rssl.phizic.business.clients.SelfRegistrationHelper;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * запрет отказа от регистрации в жестком режиме
 *
 * @ author gorshkov
 * @ created 11.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class HardRegistrationFilter implements Filter
{
	private static final String ALLOWED_PAGES_KEY = "allowedPages";
	private static final String REDIRECT_TO_PATH_KEY = "redirectTo";
	private Set<String> allowedPages;
	private String redirectTo;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		String pages = filterConfig.getInitParameter(ALLOWED_PAGES_KEY);
		String[] allowedPagesStr = pages.replaceAll("\\s+", "").split(",");

		allowedPages = new HashSet<String>(Arrays.asList(allowedPagesStr));
		redirectTo = filterConfig.getInitParameter(REDIRECT_TO_PATH_KEY);
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpServletRequest request = (HttpServletRequest) req;

		if (SecurityUtil.isAuthenticationComplete() && SelfRegistrationHelper.getIt().getNeedShowPreRegistrationMessage()
				&& SelfRegistrationHelper.getIt().getHardRegistrationMode()
				&& !allowedPages.contains(request.getRequestURI().replace(request.getContextPath(), ""))
				&& PermissionUtil.impliesService("ClientChangeLogin"))
		{
			response.sendRedirect(request.getContextPath() + redirectTo);
			return;
		}

		filterChain.doFilter(req, resp);
	}

	public void destroy()
	{
	}
}
