package com.rssl.auth.csa.front.security;

/**
 * �������� �������� �������� � �������
 * @author niculichev
 * @ created 06.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoginSecurityManager extends SecurityManagerBase
{
	private static volatile LoginSecurityManager this0;

	/**
	 * ��� ����, ��������� ���������� � ���������� ip-������� � ����� ��������� ���������� �� ���.
	 */
	private static final String LOGIN_TO_LAST_VISITING_TIME_CACHE_NAME = LoginSecurityManager.class.getName() + ".loginToLastVisitingTime";
	/**
	 * ��� ����, ��������� ���������� � ������������ ip-������� � ����� ��������� ���������� �� ���.
	 */
	private static final String NON_TRUSTED_LOGIN_TO_VISITING_TIME_CACHE_NAME = LoginSecurityManager.class.getName() + ".nonTrustedLoginToLastVisitingTime";

	/**
	 * ������� �������� ip �������.
	 */
	private LoginSecurityManager()
	{
		super(LOGIN_TO_LAST_VISITING_TIME_CACHE_NAME, NON_TRUSTED_LOGIN_TO_VISITING_TIME_CACHE_NAME);
	}

	/**
	 * @return ��������� ���������.
	 */
	public static LoginSecurityManager getIt()
	{
		if (this0 != null)
			return this0;

		synchronized (LoginSecurityManager.class)
		{
			if (this0 != null)
				return this0;

			this0 = new LoginSecurityManager();
			return this0;
		}
	}
}
