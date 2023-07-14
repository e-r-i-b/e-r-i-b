package com.rssl.phizic.web.util;

import com.rssl.phizic.web.WebContext;
import org.apache.commons.lang.ArrayUtils;

import javax.servlet.http.Cookie;

/**
 * ����� ��� ������ � ����
 * @author Jatsky
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */

public class CookieUtil
{
	/**
	 * ���������� ���� �� �����
	 * @param name ��� ������������ ����
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
	 * ���������� �������� ����
	 * @param name ��� ������������ ����
	 * @return
	 */
	public static String getCookieValueByName(String name)
	{
		Cookie resultCookie = getCookieByName(name);
		return resultCookie == null ? null : resultCookie.getValue();
	}
}
