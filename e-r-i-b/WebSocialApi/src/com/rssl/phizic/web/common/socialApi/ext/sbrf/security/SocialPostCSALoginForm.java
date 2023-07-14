package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author osminin
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 *
 * форма аутентификации пользователя соц. приложения
 */
public class SocialPostCSALoginForm extends ActionFormBase
{
	private String token;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}
