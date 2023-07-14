/**
 * @author Gainanov
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
package com.rssl.phizic.authgate;

import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.authgate.passwords.PasswordGateService;
import com.rssl.phizic.config.*;
import com.rssl.phizic.config.authService.AuthServiceConfig;

public class AuthGateSingleton
{
	private static final AuthGateService authService = createAuthService();
	private static final PasswordGateService passwordService = createPasswordService();

	private static PasswordGateService createPasswordService()
	{
		try
		{
			PasswordGateService service = (PasswordGateService) ConfigFactory.getConfig(AuthServiceConfig.class).getPassService().newInstance();
			return service;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка создания сервиса одноразовых паролей", e);
		}
	}

	private static AuthGateService createAuthService()
	{
		try
		{
			AuthGateService service = (AuthGateService) ConfigFactory.getConfig(AuthServiceConfig.class).getAuthService().newInstance();
			return service;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка создания сервиса авторизации", e);
		}
	}

	public static AuthGateService getAuthService()
	{
		return authService;
	}

	public static PasswordGateService getPasswordService()
	{
		return passwordService;
	}
}
