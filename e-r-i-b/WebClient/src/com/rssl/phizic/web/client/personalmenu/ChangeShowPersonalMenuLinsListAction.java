package com.rssl.phizic.web.client.personalmenu;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gololobov
 * @ created 01.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChangeShowPersonalMenuLinsListAction extends AsyncOperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Map<String, String> map = new HashMap<String, String>(person.getStepShowPersonalMenuLinkListMap());
		map.putAll(getRequestParameters(request));
		person.setStepShowPersonalMenuLinkListMap(map);
		Cookie cookie = new Cookie(ConfigFactory.getConfig(WebAPIConfig.class).getPersonalMenuCookieName(), getOpenedPersonalMenu(map));
		cookie.setPath("/");
		response.addCookie(cookie);
		return null;
	}

	private Map<String, String> getRequestParameters(HttpServletRequest request)
	{
		Map<String, String> parameters = new HashMap<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements())
		{
			String parameterKey   = parameterNames.nextElement();
			String parameterValue = request.getParameter(parameterKey);
			//ћапа хранит шаги раскрыти€ списков в личном меню 
			if (!StringHelper.isEmpty(parameterKey))
				parameters.put(parameterKey, parameterValue);
		}
		return parameters;
	}

	private String getOpenedPersonalMenu(Map<String, String> personalMenuMap)
	{
		for (Map.Entry<String, String> entry : personalMenuMap.entrySet())
		{
			if ("1".equals(entry.getValue()))
				return entry.getKey();
		}

		return StringUtils.EMPTY;
	}
}
