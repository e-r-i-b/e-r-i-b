package com.rssl.phizic.web.security;

import org.apache.struts.action.ActionForm;

/**
 * @author mihaylov
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма авторизации клиента в системе после аутентификации в CSAAdmin
 */
public class PostCSAAdminLoginForm extends ActionForm
{
	private String token;

	/**
	 * @return токен сессии в CSAAdmin
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * Установить токен сессии в CSAAdmin
	 * @param token - токен
	 */
	public void setToken(String token)
	{
		this.token = token;
	}
}
