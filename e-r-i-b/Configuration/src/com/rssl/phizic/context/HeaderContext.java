package com.rssl.phizic.context;

import org.apache.commons.collections.MapUtils;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.context.Constants.*;

/**
 *  онтекст значений заголовков запросов
 *
 * @author khudyakov
 * @ created 29.01.15
 * @ $Author$
 * @ $Revision$
 */
public class HeaderContext
{
	private static HeaderEntry entries = new HeaderEntry();

	/**
	 * »нициализаци€ контекста
	 * @param request запрос
	 */
	public static void initialize(HttpServletRequest request)
	{
		entries.initialize(request);
	}

	/**
	 * »нициализаци€ контекста
	 * @param session запрос
	 */
	public static void initialize(HttpSession session)
	{
		entries.initialize(session);
	}

	/**
	 * »нициализации контекста чере ассоциативный массив
	 * @param map - ассоциативный массив
	 */
	public static void initialize(Map<String, String> map)
	{
		entries.initialize(map);
	}

	/**
	 * „истка контекста
	 */
	public static void clear()
	{
		entries.clear();
	}

	/**
	 * @return значение Accept заголовка
	 */
	public static String getHttpAccept()
	{
		return entries.getValue().get(HTTP_ACCEPT_HEADER_NAME);
	}

	/**
	 * @return значение Accept-Charset заголовка
	 */
	public static String getHttpAcceptChars()
	{
		return entries.getValue().get(HTTP_ACCEPT_CHARS_HEADER_NAME);
	}

	/**
	 * @return значение Accept-Encoding заголовка
	 */
	public static String getHttpAcceptEncoding()
	{
		return entries.getValue().get(HTTP_ACCEPT_ENCODING_HEADER_NAME);
	}

	/**
	 * @return значение Accept-Language заголовка
	 */
	public static String getHttpAcceptLanguage()
	{
		return entries.getValue().get(HTTP_ACCEPT_LANGUAGE_HEADER_NAME);
	}

	/**
	 * @return значение Referer заголовка
	 */
	public static String getHttpReferrer()
	{
		return entries.getValue().get(HTTP_REFERRER_HEADER_NAME);
	}

	/**
	 * @return значение User-Agent заголовка
	 */
	public static String getUserAgent()
	{
		return entries.getValue().get(HTTP_USER_AGENT_HEADER_NAME);
	}

	/**
	 * @return значение идентификатор страницы
	 */
	public static String getPageId()
	{
		return entries.getValue().get(PAGE_ID_PARAMETER_NAME);
	}
}
