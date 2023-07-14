package com.rssl.phizic.security.util;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityConfig;

/**
 * ����� ��� ��������� ���������� ���������.
 * @author Dorzhinov
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class AnonymousPrincipalCreator
{
	private static final SecurityService securityService = new SecurityService();
	private static final Object lock = new Object();
	private static volatile UserPrincipal anonymousPrincipal = null;

	/**
	 * ���������� ���������� ���������
	 * @return ��������� ���������
	 */
	public static UserPrincipal getAnonymousPrincipal()
	{
		if (anonymousPrincipal == null) {
			synchronized (lock) {
				if (anonymousPrincipal == null)
					anonymousPrincipal = createAnonymousPrincipal();
			}
		}
		return anonymousPrincipal;
	}
	
	private static UserPrincipal createAnonymousPrincipal()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class);

		String anonymousName = securityConfig.getAnonymousClientName();
		if (anonymousName == null)
			throw new ConfigurationException("�� ��������� ��� ��� ���������� �������");

		AccessPolicy policy = authenticationConfig.getPolicy(AccessType.anonymous);
		if (policy == null)
			throw new ConfigurationException("�� ��������� �������� ������� ��� ��������� ��������");

		Login login = null;
		try
		{
			login = securityService.getClientLogin(anonymousName);
		}
		catch (SecurityDbException e)
		{
			throw new RuntimeException("������ ��� ������ ������ ������������ " + anonymousName, e);
		}
		if (login == null)
			throw new ConfigurationException("�� ������ ����� ������������ " + anonymousName);

		return new PrincipalImpl(login, policy, null);
	}
}
