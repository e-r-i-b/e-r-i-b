package com.rssl.auth.csa.wsclient.requests.info;

/**
 * @author osminin
 * @ created 18.03.14
 * @ $Author$
 * @ $Revision$
 *
 * Информация для валидации логина пользователя
 */
public class ValidateLoginInfo
{
	private String sid;
	private String login;
	private boolean sameLogin = false;
	private boolean checkPassword = false;

	/**
	 * ctor
	 * @param sid идентификатор сессии
	 * @param login логин
	 */
	public ValidateLoginInfo(String sid, String login)
	{
		this.sid = sid;
		this.login = login;
	}

	/**
	 * ctor
	 * @param sid идентификатор сессии
	 * @param login логин
	 * @param sameLogin есть ли логин у пользователя
	 */
	public ValidateLoginInfo(String sid, String login, boolean sameLogin)
	{
		this(sid, login);
		this.sameLogin = sameLogin;
	}

	/**
	 * @return идентификатор сессии
	 */
	public String getSid()
	{
		return sid;
	}

	/**
	 * @param sid идентификатор сессии
	 */
	public void setSid(String sid)
	{
		this.sid = sid;
	}

	/**
	 * @return логин
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @param login логин
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return есть ли логин у пользователя
	 */
	public boolean isSameLogin()
	{
		return sameLogin;
	}

	/**
	 * @param sameLogin есть ли логин у пользователя
	 */
	public void setSameLogin(boolean sameLogin)
	{
		this.sameLogin = sameLogin;
	}

	/**
	 * @return проверять ли логин на совпадение с паролем
	 */
	public boolean isCheckPassword()
	{
		return checkPassword;
	}

	/**
	 * @param checkPassword проверять ли логин на совпадение с паролем
	 */
	public void setCheckPassword(boolean checkPassword)
	{
		this.checkPassword = checkPassword;
	}
}
