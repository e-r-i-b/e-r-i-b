package com.rssl.phizic.web.client.security;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Обновление информации по WebAPI
 * @author Pankin
 * @ created 19.08.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdateAPILoginAction extends LoginStageActionSupport
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Cookie resetWebAPICookie = new Cookie(ConfigFactory.getConfig(WebAPIConfig.class).getCookieName(), "");
		resetWebAPICookie.setPath("/");
		resetWebAPICookie.setMaxAge(0);
		resetWebAPICookie.setDomain(ConfigFactory.getConfig(WebAPIConfig.class).getWebApiCookieDomain());
		response.addCookie(resetWebAPICookie);
		completeStage();
		return null;
	}
}
