package com.rssl.phizic.web.client.security;

import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Evgrafov
 * @ created 13.12.2006
 * @ $Author: erkin $
 * @ $Revision: 24884 $
 */

@SuppressWarnings({"JavaDoc"})
public class IndexForm extends ActionFormBase
{
	private String login;
	private AuthenticationConfig authenticationConfig;


	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public AuthenticationConfig getAuthenticationConfig()
	{
		return authenticationConfig;
	}

	public void setAuthenticationConfig(AuthenticationConfig authenticationConfig)
	{
		this.authenticationConfig = authenticationConfig;
	}
}