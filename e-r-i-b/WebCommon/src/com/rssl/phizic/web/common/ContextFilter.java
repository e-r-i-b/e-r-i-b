package com.rssl.phizic.web.common;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.util.RequestHelper;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.logging.quick.pay.Constants.CLICKED_BLOCK_IDS;

/**
 * @author Evgrafov
 * @ created 29.06.2006
 * @ $Author: krenev $
 * @ $Revision: 81778 $
 */

public class ContextFilter implements Filter
{
	private static final String CODE_ATM       = "codeATM";
	private static final String CHIP_CARD_SIGN = "isChipCard";

	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		WebContext.setCurrentRequest(request);
		WebContext.setCurrentResponce(response);
		StoreManager.setCurrentStore(new SessionStore());

		try
		{
			setCurrentContext(request);
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			HttpSession session = request.getSession(false);
			if(CollectionUtils.isNotEmpty(LogThreadContext.getClickedBlockIds()) && session != null)
				session.setAttribute(CLICKED_BLOCK_IDS, LogThreadContext.getClickedBlockIds());
			clearCurrentContext();

			StoreManager.setCurrentStore(null);
			WebContext.setCurrentRequest(null);
		    WebContext.setCurrentResponce(null);
		}
	}

	public void destroy()
	{
	}

	public static void clearCurrentContext()
	{
		OperationContext.clear();
		LogThreadContext.clear();
	}

	private void setCurrentContext(HttpServletRequest request)
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();;
		Application application = applicationConfig.getLoginContextApplication();

		refreshSessionParameters(request);

		// для CSAFront это не нужно
		if(application != Application.CSAFront)
			refreshContext(request);
	}

	/**
	 * обновление тех параметров, которые могут измениться в процессе выполнения запроса.
	 */
	public static void refreshContext(HttpServletRequest request)
	{
		AuthModule    authModule    = AuthModule.getAuthModule();
		UserPrincipal principal     = authModule == null ? null : authModule.getPrincipal();
		boolean isGuestLogin = principal != null && AccessType.guest.equals(principal.getAccessType());
		CommonLogin commonLogin = principal  == null ? null : principal.getLogin();
		Long          loginId = null;
		if (!isGuestLogin)
			loginId       = commonLogin  == null ? null : commonLogin.getId();

		setCodeATMToLogThreadContext();
		setChipCardSignToThreadContext();

		if(loginId != null)
		{
			Long prevLoginId = LogThreadContext.getLoginId();
			boolean isChanged = !loginId.equals(prevLoginId);
			if(isChanged)
			{
				ContextFillHelper.fillContextByLogin(commonLogin);
			}
		}
		//если вход гостевой
		else if (isGuestLogin)
		{
			GuestLogin guestLogin = (GuestLogin) commonLogin;
			Long prevGuestCode = LogThreadContext.getGuestCode();
			if (guestLogin != null && guestLogin.getGuestCode() != null && !guestLogin.getGuestCode().equals(prevGuestCode))
			{
				ContextFillHelper.fillContextByLogin(guestLogin);
				AuthenticationContext authenticationContext = AuthenticationContext.getContext();
				if (authenticationContext != null)
					LogThreadContext.setLogin(authenticationContext.getUserAlias());
			}
		}

		LoggingHelper.setAppServerInfoToLogThreadContext(request);
	}

	private static void setChipCardSignToThreadContext()
	{
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		if (authenticationContext == null)
		{
			return;
		}

		LogThreadContext.setChipCard(authenticationContext.getAuthenticationParameter(CHIP_CARD_SIGN));
	}

	private static void setCodeATMToLogThreadContext()
	{
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		if (authenticationContext != null)
			LogThreadContext.setCodeATM(authenticationContext.getAuthenticationParameter(CODE_ATM));		
	}

	/**
	 * Обновление контекста данными привязанными к сессии пользователя
	 * @param request
	 */
	public static void refreshSessionParameters(HttpServletRequest request)
	{
		HttpSession session = request.getSession(); // sessionId нужен в LogThreadContext, поэтому создаём сессию
		String sessionId = session.getId();
		LogThreadContext.setSessionId(sessionId);
		LogThreadContext.setIPAddress(RequestHelper.getIpAddress(request));
		List<Long> ids = (List<Long>)session.getAttribute(CLICKED_BLOCK_IDS);
		LogThreadContext.setClickedBlockIds(ids);
		session.removeAttribute(CLICKED_BLOCK_IDS);
		LoggingHelper.setAppServerInfoToLogThreadContext(request);
	}
}
