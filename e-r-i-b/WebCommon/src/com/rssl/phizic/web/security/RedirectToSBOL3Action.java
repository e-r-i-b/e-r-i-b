package com.rssl.phizic.web.security;

import com.rssl.phizic.business.webapi.TokenService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.CookieUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн для редиректа из СБОЛ2 в СБОЛ3
 * @author Jatsky
 * @ created 10.04.14
 * @ $Author$
 * @ $Revision$
 */

public class RedirectToSBOL3Action extends OperationalActionBase
{
	protected static final String AUTH_TOKEN_PARAM_NAME = "AuthToken";
	protected static final String IS_AUTHENTICATION_COMPLETED_PARAM_NAME = "isAuthenticationCompleted";
	protected static final TokenService tokenService = new TokenService();
	private static final String FORWARD_LOGIN = "login";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String token = new RandomGUID().getStringValue();
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			return mapping.findForward(FORWARD_LOGIN);
		tokenService.createToken(token, personData.getLogin());
		ActionRedirect redirect = new ActionRedirect(ConfigFactory.getConfig(WebAPIConfig.class).getUrlSBOL3());
		redirect.addParameter(AUTH_TOKEN_PARAM_NAME, token);
		redirect.addParameter(IS_AUTHENTICATION_COMPLETED_PARAM_NAME, "true");
		String dpCookieName = ConfigFactory.getConfig(WebAPIConfig.class).getDpCookieName();
		String dpCookieValue = CookieUtil.getCookieValueByName(dpCookieName);
		redirect.addParameter(dpCookieName, dpCookieValue);
		return redirect;
	}
}
