package com.rssl.phizic.web.security;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author akrenev
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * класс для работы с токенами
 */

public class PageTokenUtil
{
	private static final String PAGE_TOKEN_ATTRIBUTE_KEY = "PAGE_TOKEN";
	private static final String CSRF_INPUT_PREFIX = "<input type=\"hidden\" name=\"" + PageTokenUtil.PAGE_TOKEN_ATTRIBUTE_KEY + "\" value=\"";
	private static final String CSRF_INPUT_POSTFIX = "\">";

	private static String getNewToken()
	{
		return new RandomGUID().getStringValue();
	}

	private static HttpSession getSession(HttpServletRequest request)
	{
		return request.getSession(false);
	}

	/**
	 * безопасен ли (в плане CSRF) реквест,
	 * т.е. может ли данный реквест быть атакой
	 * @param request реквест
	 * @return true - реквест ноужно проверить
	 */
	private static boolean needProtect(HttpServletRequest request)
	{
		// "пустые" реквесты и сессии не опасны в данном контексте
		if (request == null || getSession(request) == null)
			return false;

		// проверяем приложение
		Application application = ApplicationConfig.getIt().getLoginContextApplication();
		return application == Application.PhizIA || application == Application.PhizIC;
	}

	public static Map<String, String> getTokenAsParameter(HttpSession session)
	{
		return Collections.singletonMap(PAGE_TOKEN_ATTRIBUTE_KEY, getToken(session, true));
	}

	/**
	 * получить токен из сессии
	 * @param session сессия
	 * @param needCreate создавать ли новый токен, если его нет в сессии 
	 * @return токен
	 */
	public static String getToken(HttpSession session, boolean needCreate)
	{
		if (session == null)
			return StringUtils.EMPTY;

		String token = StringHelper.getEmptyIfNull(session.getAttribute(PAGE_TOKEN_ATTRIBUTE_KEY));
		if (needCreate && StringHelper.isEmpty(token))
		{
			token = getNewToken();
			session.setAttribute(PAGE_TOKEN_ATTRIBUTE_KEY, token);
		}
		return token;
	}

	/**
	 * добавить токен в JspWriter
	 * @param jspWriter сессия
	 */
	public static void addToken(JspWriter jspWriter) throws JspException
	{
		if (jspWriter == null)
			return;

		HttpServletRequest request = WebContext.getCurrentRequest();
		if (!needProtect(request))
			return;

		try
		{
			jspWriter.print(CSRF_INPUT_PREFIX);
			jspWriter.print(getToken(getSession(request), true));
			jspWriter.print(CSRF_INPUT_POSTFIX);
		}
		catch (IOException e)
		{
		    throw new JspException("Ошибка добавления поля с токеном.", e);
		}
	}

	/**
	 * проверить валидность токена
	 * @param request проверяемый реквест
	 * @return защищен реквест ли от CSRF
	 */
	public static boolean isSecureRequest(HttpServletRequest request)
	{
		if (!needProtect(request))
			return true;

		String token = getToken(getSession(request), false);
		return StringHelper.isEmpty(token) || StringUtils.equals(token, request.getParameter(PAGE_TOKEN_ATTRIBUTE_KEY));
	}
}
