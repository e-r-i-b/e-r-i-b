package com.rssl.phizic.web.client.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author bogdanov
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginFromCSAFrontForm extends ActionFormBase
{
	private String authToken;

	public String getAuthToken()
	{
		return authToken;
	}

	public void setAuthToken(String authToken)
	{
		this.authToken = authToken;
	}
}