package com.rssl.phizic.web.client.connectUdbo;

import com.rssl.phizic.business.clients.RemoteConnectionUDBOHelper;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * запрет работы В СБОЛ без заключения УДБО
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class RemoteConnectionUDBOFilter implements Filter
{
	private static final String ALLOWED_PAGES_KEY = "allowedPages";
	private static final String REDIRECT_TO_PATH_KEY = "redirectTo";
	private static final String PAYMENT_PATH = "/private/payments/payment.do?form=RemoteConnectionUDBOClaim";
	private Set<String> allowedPages;
	private String redirectTo;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		String pages = filterConfig.getInitParameter(ALLOWED_PAGES_KEY);
		String[] allowedPagesStr = pages.replaceAll("\\s+", "").split(",");

		allowedPages = new HashSet<String>(Arrays.asList(allowedPagesStr));
		redirectTo = filterConfig.getInitParameter(REDIRECT_TO_PATH_KEY);
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		//установлен признак перехода к форме заключения договора - редиректим на нее
		if (RemoteConnectionUDBOHelper.getFromSession().isRedirectToPayment()
				&& !checkUrl(request, request.getRequestURI().replace(request.getContextPath(), "")))
		{
			response.sendRedirect(request.getContextPath() + PAYMENT_PATH);
			return;
		}

		//клиент может заключить УДБО (клиент карточный или СБОЛ, не ВИП) и запрещёна работа без УДБО - завершаем сессию клиента
		if (SecurityUtil.isAuthenticationComplete() && RemoteConnectionUDBOHelper.isShowMoreSbolMenuItem()
				&& !RemoteConnectionUDBOHelper.isWorkWithoutUDBO() && !checkUrl(request, request.getRequestURI().replace(request.getContextPath(), "")))
		{
			response.sendRedirect(request.getContextPath() + redirectTo);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 *
	 * @param request
	 * @param url
	 * @return - проверяем что на страницу разрешено перейти
	 */
	private boolean checkUrl(HttpServletRequest request, String url)
	{
		return allowedPages.contains(url) || (url.equals("/private/payments/payment.do") && (StringHelper.isEmpty(request.getParameter("form")) || request.getParameter("form").equals("RemoteConnectionUDBOClaim")));
	}

	public void destroy()
	{
	}
}
