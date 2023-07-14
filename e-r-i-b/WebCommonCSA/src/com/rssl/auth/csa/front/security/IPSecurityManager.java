package com.rssl.auth.csa.front.security;

/**
 * Менеджер проверки ip-адресов.
 *
 * @author bogdanov
 * @ created 10.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class IPSecurityManager extends SecurityManagerBase
{
	private static volatile IPSecurityManager this0;

	/**
	 * Имя кеша, хранящего информацию о доверенных ip-адресах и время последней активности по ним.
	 */
	private static final String IP_TO_LAST_VISITING_TIME_CACHE_NAME = IPSecurityManager.class.getName() + ".ipToLastVisitingTime";
	/**
	 * Имя кеша, хранящего информацию о недоверенных ip-адресах и время последней активности по ним.
	 */
	private static final String NON_TRUSTED_IP_TOVISITING_TIME_CACHE_NAME = IPSecurityManager.class.getName() + ".nonTrustedIpToLastVisitingTime";

	/**
	 * Создает менеджер ip адресов.
	 */
	protected IPSecurityManager()
	{
		super(IP_TO_LAST_VISITING_TIME_CACHE_NAME, NON_TRUSTED_IP_TOVISITING_TIME_CACHE_NAME);
	}

	/**
	 * @return экземпляр менеджера.
	 */
	public static IPSecurityManager getIt()
	{
		if (this0 != null)
			return this0;

		synchronized (IPSecurityManager.class)
		{
			if (this0 != null)
				return this0;

			this0 = new IPSecurityManager();
			return this0;
		}
	}
}
