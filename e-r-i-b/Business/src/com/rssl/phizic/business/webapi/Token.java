package com.rssl.phizic.business.webapi;

import com.rssl.phizic.auth.Login;

import java.util.Calendar;

/**
 * Токен WebAPI
 * @author Jatsky
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 */

public class Token
{
	private String token;
	private Login login;
	private Calendar creationDate;

	public Token()
	{
	}

	public Token(String token, Login login, Calendar creationDate)
	{
		this.token = token;
		this.login = login;
		this.creationDate = creationDate;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public Login getLogin()
	{
		return login;
	}

	public void setLogin(Login login)
	{
		this.login = login;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}
}
