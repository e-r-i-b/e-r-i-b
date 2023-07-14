package com.rssl.phizic.config.authService;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author bogdanov
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */

public class AuthServiceConfig extends Config
{
	private static final String AUTH_SERVICE_KEY = "com.rssl.auth.service";
	private static final String PASS_SERVICE_KEY = "com.rssl.pass.service";
	private static final String USE_OWN_AUTH_PROPERTY = "com.rssl.auth.useOwnAuthentication";
	private Class passService;
	private Class authService;
	private boolean useOwnAuth;

	public AuthServiceConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		useOwnAuth = getBoolProperty(USE_OWN_AUTH_PROPERTY);
		try
		{
			String passServiceName = getProperty(PASS_SERVICE_KEY);
			passService = Class.forName(passServiceName);

			String authServiceName = getProperty(AUTH_SERVICE_KEY);
			authService = Class.forName(authServiceName);
		}
		catch (ClassNotFoundException e)
		{
			throw new ConfigurationException("Ошибка во время загрузки AuthServiceConfig", e);
		}
	}

	public Class getPassService()
	{
		return passService;
	}

	public Class getAuthService()
	{
		return authService;
	}

	public boolean isUseOwnAuth()
	{
		return useOwnAuth;
	}
}
