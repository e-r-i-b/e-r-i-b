package com.rssl.phizic.context;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
class HeaderEntry
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
		value.get().put(HTTP_ACCEPT_HEADER_NAME,                request.getHeader(HTTP_ACCEPT_HEADER_NAME));
		value.get().put(HTTP_ACCEPT_CHARS_HEADER_NAME,          request.getCharacterEncoding());
		value.get().put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME,       request.getHeader(HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		value.get().put(HTTP_ACCEPT_ENCODING_HEADER_NAME,       request.getHeader(HTTP_ACCEPT_ENCODING_HEADER_NAME));
		value.get().put(HTTP_REFERRER_HEADER_NAME,              request.getHeader(HTTP_REFERRER_HEADER_NAME));
		value.get().put(HTTP_USER_AGENT_HEADER_NAME,            request.getHeader(HTTP_USER_AGENT_HEADER_NAME));
		value.get().put(PAGE_ID_PARAMETER_NAME,                 request.getRequestURI());
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

	void initialize(Map<String, String> map)
	{
		value.get().put(HTTP_ACCEPT_HEADER_NAME,            map.get(HTTP_ACCEPT_HEADER_NAME));
		value.get().put(HTTP_ACCEPT_CHARS_HEADER_NAME,      map.get(HTTP_ACCEPT_CHARS_HEADER_NAME));
		value.get().put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME,   map.get(HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		value.get().put(HTTP_ACCEPT_ENCODING_HEADER_NAME,   map.get(HTTP_ACCEPT_ENCODING_HEADER_NAME));
		value.get().put(HTTP_REFERRER_HEADER_NAME,          map.get(HTTP_REFERRER_HEADER_NAME));
		value.get().put(HTTP_USER_AGENT_HEADER_NAME,        map.get(HTTP_USER_AGENT_HEADER_NAME));
		value.get().put(PAGE_ID_PARAMETER_NAME,             map.get(PAGE_ID_PARAMETER_NAME));
	}

	Map<String, String> getValue()
	{
		return value.get();
	}

	void clear()
	{
		value.remove();
	}
}
