package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.security.SecurityUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class LoginFilter implements Filter
{
	protected String startAuthenticationUrl;
	/**
	 * если тип сменилс€ тип посещени€ пользовател€ то перерйти на этот урл.
	 */
	private String anotherUserVisitingTypeOpenedUrl;
	protected boolean addReturnTo = false;
	private Set<String> ignoredUrl = new HashSet<String>();
	private String dublicateSessionUrl;//урл страницы с информацией о дублирующейс€ сессии

	public void init ( FilterConfig filterConfig ) throws ServletException
	{
		startAuthenticationUrl = filterConfig.getInitParameter("redirectUrl");
		anotherUserVisitingTypeOpenedUrl = filterConfig.getInitParameter("anotherVisitingTypeRedirectUrl");
		String ignoredUrlParameter = filterConfig.getInitParameter("ignoredUrl");
		if(StringHelper.isNotEmpty(ignoredUrlParameter))
		{
			String[] ignoredUrlArray = ignoredUrlParameter.split(",");
			ignoredUrl = new HashSet<String>(Arrays.asList(ignoredUrlArray));
		}
		dublicateSessionUrl = filterConfig.getInitParameter("dublicateSessionUrl");
		if(startAuthenticationUrl == null)
			throw new ServletException("Ќе установлен параметр фильтра \"redirectUrl\"");

		String addReturnToStr = filterConfig.getInitParameter("addReturnTo");
		if(addReturnToStr != null && addReturnToStr.equals("true"))
			addReturnTo = true;
	}

	public void doFilter ( ServletRequest req, ServletResponse resp, FilterChain filterChain ) throws IOException, ServletException
	{
		HttpServletRequest  request  = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		boolean doChain = true;

		UserVisitingMode visitingMode = ConfirmationManager.getUserVisitingMode();
		
		if (SecurityUtil.isAuthenticationComplete())
		{
			boolean guestAjax = visitingMode == UserVisitingMode.GUEST && (this instanceof AjaxFilter || this instanceof BasicVersionLoginFilter);
			if (visitingMode != null && visitingMode != getUserMode() && !guestAjax)
				redirectToAnotherSessionOpened(request, response);

			if (PersonHelper.isFraud())
			{
				doChain = makeUrlAndRedirect(request, response);
			}

			if (PersonHelper.isPersonBlocked())
				doChain = makeUrlAndRedirect(request, response);

			String userLogonSessionId = PersonHelper.getPersonLogonSessionId();
			//провер€ем, что клиент работает в рамках той сессией, под которой был произведен последний вход в систему дл€ данного логина
			HttpSession session = request.getSession(false);
			String sessionId = (session != null) ? session.getId() : null;
			if(StringHelper.isNotEmpty(dublicateSessionUrl) && StringHelper.isNotEmpty(userLogonSessionId) && !userLogonSessionId.equals(sessionId))
			{
				sendToDublicateUrl(request,response);
				doChain = false;
			}

		   	if (PersonHelper.needKick())
			    doChain = makeUrlAndRedirect(request, response);

			if(((HttpServletRequest)req).getRequestURL().toString().contains("/login.do"))
			{
				StoreManager.getCurrentStore().clear();
			}
		}
		else
			doChain = makeUrlAndRedirect(request, response);

		if (doChain)
			filterChain.doFilter(req, resp);
	}

	private void redirectToAnotherSessionOpened(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		sendRedirect(request, response, anotherUserVisitingTypeOpenedUrl);
	}

	protected boolean makeUrlAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String url = getRedirectURI(request);
		if (url != null)
		{
			sendRedirect(request, response, url);
			return false;
		}
		return true;
	}

	private String getRedirectURI(HttpServletRequest request)
	{
		String requestURI = request.getRequestURI().substring(request.getContextPath().length());
		if(requestURI.equals(startAuthenticationUrl) || ignoredUrl.contains(requestURI))
			return null;
		
		AccessPolicy policy = AuthenticationManager.getPolicy();

		if(policy == null)
		{
			// authentication non started yet
			return AuthenticationManager.redirectToUrl(request, startAuthenticationUrl, addReturnTo);
		}

		Stage stage = AuthenticationManager.getStage();

		if(stage == null)
		{
			// непон€тна€ ситуаци€ - начнем сначала
			AuthenticationManager.abort();
			return startAuthenticationUrl;
		}

		if(!stage.getAllAllowedActions().contains(requestURI))
		{
			// URI запроса не соответствует текущей стадии авторизации
			return AuthenticationManager.redirectToStage(request, stage, addReturnTo);
		}

		return null;
	}

	protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String redirectURI) throws IOException
	{
		String uri = request.getContextPath() + redirectURI;
		HttpServletRequest asyncRequest = getAsyncRequest(request);
		if (asyncRequest != null)
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		else response.sendRedirect(uri);
	}

	private HttpServletRequest getAsyncRequest(HttpServletRequest request)
	{
		String path = request.getServletPath() + StringHelper.getEmptyIfNull(request.getPathInfo());
		return path.contains("/async/") ? request : null;
	}

	protected void sendToDublicateUrl(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		StoreManager.getCurrentStore().clear();
		sendRedirect(request, response, dublicateSessionUrl);
	}

	/**
	 * ¬озвращает режим (стандартна€ верси€ или переход с внешней ссылки), под которым мы можем работать в данном фильтре
	 */
	abstract UserVisitingMode getUserMode();

	public void destroy ()
	{
	}
}
