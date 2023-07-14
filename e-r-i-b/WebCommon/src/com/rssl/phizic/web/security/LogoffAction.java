package com.rssl.phizic.web.security;

import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Evgrafov
 * @ created 19.01.2006
 * @ $Author: egorovaav $
 * @ $Revision: 47168 $
 */
public class LogoffAction extends LogoffActionBase
{
	public static final String TO_LOGIN_PARAMETER = "toLogin";

	protected ActionForward findForward(ActionMapping mapping, HttpServletRequest request)
	{
		AuthConfig config = AuthGateSingleton.getAuthService().getConfig();
		if (BooleanUtils.toBoolean(request.getParameter(TO_LOGIN_PARAMETER)))
			return new ActionRedirect(config.getProperty("csaFront.login.url"));

		return new ActionRedirect(config.getProperty("csaFront.logoff.url"));
	}
}
