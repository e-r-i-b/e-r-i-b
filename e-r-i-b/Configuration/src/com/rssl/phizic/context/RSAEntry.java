package com.rssl.phizic.context;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
class RSAEntry
{
	private ThreadLocal<Map<String, String>> value = new ThreadLocal<Map<String, String>>()
	{
		@Override
		protected Map<String, String> initialValue()
		{
			return new HashMap<String, String>();
		}
	};

	void initialize(HttpServletRequest request)
	{
		String characterEncoding = request.getCharacterEncoding();
		try
		{
			if (characterEncoding == null)
				try
				{
					request.setCharacterEncoding("UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
					throw new IllegalArgumentException("Не поддерживается кодировка UTF-8", e);
				}

			value.get().put(DOM_ELEMENTS_PARAMETER_NAME,            request.getParameter(DOM_ELEMENTS_PARAMETER_NAME));
			value.get().put(JS_EVENTS_PARAMETER_NAME,               request.getParameter(JS_EVENTS_PARAMETER_NAME));
			value.get().put(DEVICE_PRINT_PARAMETER_NAME,            request.getParameter(DEVICE_PRINT_PARAMETER_NAME));
			value.get().put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME,     getDeviceTokenCookieParameter(request));
			value.get().put(DEVICE_TOKEN_FSO_PARAMETER_NAME,        getDeviceTokenFSOParameter(request.getSession()));
			value.get().put(MOBILE_SDK_DATA_PARAMETER_NAME,         request.getParameter(MOBILE_SDK_DATA_PARAMETER_NAME));
		}
		finally
		{
			if (characterEncoding != null)
				try
				{
					request.setCharacterEncoding(characterEncoding);
				}
				catch (UnsupportedEncodingException e)
				{
					throw new IllegalArgumentException("Не поддерживается кодировка " + characterEncoding, e);
				}
		}
	}

	void initialize(HttpSession session)
	{
		//noinspection unchecked
		Map<String, String> rsaData = (Map<String, String>) session.getAttribute(CONTEXT_DATA_NAME);
		if (rsaData == null)
		{
			return;
		}

		initialize(rsaData);
	}

	void initialize(Map<String, String> rsaData)
	{
		value.get().put(DOM_ELEMENTS_PARAMETER_NAME,            rsaData.get(DOM_ELEMENTS_PARAMETER_NAME));
		value.get().put(JS_EVENTS_PARAMETER_NAME,               rsaData.get(JS_EVENTS_PARAMETER_NAME));
		value.get().put(DEVICE_PRINT_PARAMETER_NAME,            rsaData.get(DEVICE_PRINT_PARAMETER_NAME));
		value.get().put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME,     rsaData.get(DEVICE_TOKEN_COOKIE_PARAMETER_NAME));
		value.get().put(DEVICE_TOKEN_FSO_PARAMETER_NAME,        rsaData.get(DEVICE_TOKEN_FSO_PARAMETER_NAME));
		value.get().put(MOBILE_SDK_DATA_PARAMETER_NAME,         rsaData.get(MOBILE_SDK_DATA_PARAMETER_NAME));
	}

	void append(Map<String, String> rsaData)
	{
		value.get().put(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_FSO_PARAMETER_NAME,          rsaData.get(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_FSO_PARAMETER_NAME));
		value.get().put(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_COOKIE_PARAMETER_NAME,       rsaData.get(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_COOKIE_PARAMETER_NAME));
	}

	Map<String, String> getValue()
	{
		return value.get();
	}

	void clear()
	{
		value.remove();
	}

	/**
	 * Получение значения поля deviceTokenFSO
	 *
	 * @param session сессия
	 * @return значения поля deviceTokenFSO
	 */
	private String getDeviceTokenFSOParameter(HttpSession session)
	{
		return StringHelper.getEmptyIfNull(session.getAttribute(USER_DEVICE_TOKEN_PREFIX_NAME + FLASH_SO_ATTRIBUTE_NAME));
	}

	/**
	 * Получение значения поля deviceTokenFSO
	 *
	 * @param request request
	 * @return значения поля deviceTokenFSO
	 */
	private String getDeviceTokenCookieParameter(HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies))
		{
			for (Cookie cookie : cookies)
			{
				if (Constants.COOKIE_PM_DATA_ATTRIBUTE_NAME.equals(cookie.getName()))
				{
					return StringHelper.getNullIfEmpty(cookie.getValue());
				}
			}
		}
		return null;
	}
}

