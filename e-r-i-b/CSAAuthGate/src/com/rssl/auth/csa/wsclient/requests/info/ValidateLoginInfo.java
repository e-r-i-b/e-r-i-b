package com.rssl.auth.csa.wsclient.requests.info;

/**
 * @author osminin
 * @ created 18.03.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��� ��������� ������ ������������
 */
public class ValidateLoginInfo
{
	private String sid;
	private String login;
	private boolean sameLogin = false;
	private boolean checkPassword = false;

	/**
	 * ctor
	 * @param sid ������������� ������
	 * @param login �����
	 */
	public ValidateLoginInfo(String sid, String login)
	{
		this.sid = sid;
		this.login = login;
	}

	/**
	 * ctor
	 * @param sid ������������� ������
	 * @param login �����
	 * @param sameLogin ���� �� ����� � ������������
	 */
	public ValidateLoginInfo(String sid, String login, boolean sameLogin)
	{
		this(sid, login);
		this.sameLogin = sameLogin;
	}

	/**
	 * @return ������������� ������
	 */
	public String getSid()
	{
		return sid;
	}

	/**
	 * @param sid ������������� ������
	 */
	public void setSid(String sid)
	{
		this.sid = sid;
	}

	/**
	 * @return �����
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @param login �����
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return ���� �� ����� � ������������
	 */
	public boolean isSameLogin()
	{
		return sameLogin;
	}

	/**
	 * @param sameLogin ���� �� ����� � ������������
	 */
	public void setSameLogin(boolean sameLogin)
	{
		this.sameLogin = sameLogin;
	}

	/**
	 * @return ��������� �� ����� �� ���������� � �������
	 */
	public boolean isCheckPassword()
	{
		return checkPassword;
	}

	/**
	 * @param checkPassword ��������� �� ����� �� ���������� � �������
	 */
	public void setCheckPassword(boolean checkPassword)
	{
		this.checkPassword = checkPassword;
	}
}
