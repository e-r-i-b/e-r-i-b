package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма получения временного логина и пароля
 * @author Jatsky
 * @ created 13.12.13
 * @ $Author$
 * @ $Revision$
 */

public class GetRegistrationForm extends ActionFormBase
{
	private String login;
	private String password;

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
