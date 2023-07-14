package com.rssl.auth.csa.front.security;

/**
 * �������� �������� ip-�������.
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
	 * ��� ����, ��������� ���������� � ���������� ip-������� � ����� ��������� ���������� �� ���.
	 */
	private static final String IP_TO_LAST_VISITING_TIME_CACHE_NAME = IPSecurityManager.class.getName() + ".ipToLastVisitingTime";
	/**
	 * ��� ����, ��������� ���������� � ������������ ip-������� � ����� ��������� ���������� �� ���.
	 */
	private static final String NON_TRUSTED_IP_TOVISITING_TIME_CACHE_NAME = IPSecurityManager.class.getName() + ".nonTrustedIpToLastVisitingTime";

	/**
	 * ������� �������� ip �������.
	 */
	protected IPSecurityManager()
	{
		super(IP_TO_LAST_VISITING_TIME_CACHE_NAME, NON_TRUSTED_IP_TOVISITING_TIME_CACHE_NAME);
	}

	/**
	 * @return ��������� ���������.
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
