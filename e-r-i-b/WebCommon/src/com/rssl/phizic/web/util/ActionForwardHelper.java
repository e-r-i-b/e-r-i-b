package com.rssl.phizic.web.util;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.struts.action.ActionForward;

import java.util.Collections;
import java.util.Map;

/**
 * @author Erkin
 * @ created 26.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class ActionForwardHelper
{
	/**
	 * ƒобавл€ет пареметр редиректу
	 * @param forward - форвард типа редирект
	 * @param parameter - им€ параметра
	 * @param value - значение параметра
	 * @return форвард с новым параметром
	 */
	public static ActionForward appendParam(ActionForward forward, String parameter, String value)
	{
		if (forward == null)
			throw new NullPointerException("јргумент 'forward' не может быть null");
		if (StringHelper.isEmpty(parameter))
			throw new IllegalArgumentException("јргумент 'parameter' не может быть пустым");
		if (value == null)
			throw new NullPointerException("јргумент 'value' не может быть null");

		return appendParams(forward, Collections.singletonMap(parameter, value));
	}

	/**
	 * ƒобавл€ет параметры редиректу
	 * @param forward - форвард типа редирект
	 * @param parameters - мап параметров "им€ -> значение"
	 * @return форвард с новыми параметрами
	 */
	public static ActionForward appendParams(ActionForward forward, Map<String, String> parameters)
	{
		if (forward == null)
			throw new NullPointerException("јргумент 'forward' не может быть null");
		if (!forward.getRedirect())
			return forward;
		if (parameters == null)
			throw new NullPointerException("јргумент 'parameters' не может быть null");
		if (parameters.isEmpty())
			return forward;

		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameters(parameters);
		return new ActionForward(urlBuilder.getUrl(), true);
	}
}
