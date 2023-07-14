package com.rssl.phizic.web.util;

import com.rssl.phizic.web.WebContext;
import org.apache.commons.lang.ArrayUtils;

import javax.servlet.http.Cookie;

/**
 *  ласс дл€ работы с куки
 * @author Jatsky
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */

public class CookieUtil
{
	/**
	 * ¬озвращает куку по имени
	 * @param name им€ интересующей куки
	 * @return
	 */
	public static Cookie getCookieByName(String name)
	{
		Cookie[] cookies = WebContext.getCurrentRequest().getCookies();
		if (ArrayUtils.isNotEmpty(cookies))
		{
			for (Cookie cookie : cookies)
			{
				if (cookie.getName().equals(name))
				{
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * ¬озвращает значение куки
	 * @param name им€ интересующей куки
	 * @return
	 */
	public static String getCookieValueByName(String name)
	{
		Cookie resultCookie = getCookieByName(name);
		return resultCookie == null ? null : resultCookie.getValue();
	}
}
