package com.rssl.auth.csa.front.security;

/**
 * Менеджер проверки запросов с логином
 * @author niculichev
 * @ created 06.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoginSecurityManager extends SecurityManagerBase
{
	private static volatile LoginSecurityManager this0;

	/**
	 * Имя кеша, хранящего информацию о доверенных ip-адресах и время последней активности по ним.
	 */
	private static final String LOGIN_TO_LAST_VISITING_TIME_CACHE_NAME = LoginSecurityManager.class.getName() + ".loginToLastVisitingTime";
	/**
	 * Имя кеша, хранящего информацию о недоверенных ip-адресах и время последней активности по ним.
	 */
	private static final String NON_TRUSTED_LOGIN_TO_VISITING_TIME_CACHE_NAME = LoginSecurityManager.class.getName() + ".nonTrustedLoginToLastVisitingTime";

	/**
	 * Создает менеджер ip адресов.
	 */
	private LoginSecurityManager()
	{
		super(LOGIN_TO_LAST_VISITING_TIME_CACHE_NAME, NON_TRUSTED_LOGIN_TO_VISITING_TIME_CACHE_NAME);
	}

	/**
	 * @return экземпляр менеджера.
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
