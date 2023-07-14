package com.rssl.phizic.security.config;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.auth.LoginInfoProvider;
import com.rssl.phizic.auth.PermissionsProvider;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.password.NamePasswordValidator;
import com.rssl.phizic.security.password.UserPasswordValidator;

/**
 * @author Kosyakov
 * @ created 28.12.2005
 * @ $Author: niculichev $
 * @ $Revision: 72565 $
 */
public class SecurityFactory
{
	private static CryptoService cryptoService = null;

	/**
	 * @return LoginInfoProvider
	 */
	public static LoginInfoProvider createLoginInfoProvider()
	{
		SecurityConfig securityConfig = getSecurityConfig();
		String loginInfoProviderClassName = securityConfig.getLoginInfoProviderClassName();
		return newloginInfoProvider(loginInfoProviderClassName);
	}

	/**
	 * Создать PermissionsProvider для пользователей
	 * @return PermissionsProvider
	 */
	public static PermissionsProvider createPermissionProvider()
	{
		SecurityConfig securityConfig = getSecurityConfig();
		String permissionProviderClassName = securityConfig.getPermissionProviderClassName();
		return newPermissionProvider(permissionProviderClassName);
	}

	/**
	 * Создать PermissionsProvider для гостей
	 * @return PermissionsProvider
	 */
	public static PermissionsProvider createGuestPermissionProvider()
	{
		SecurityConfig securityConfig = getSecurityConfig();
		String permissionProviderClassName = securityConfig.getGuestPermissionProviderClassName();
		return newPermissionProvider(permissionProviderClassName);
	}

	/**
	 * Создать PermissionsProvider для пользователя по умолчанию
	 * @return PermissionsProvider
	 */
	public static PermissionsProvider createAdminPermissionProvider()
	{
		SecurityConfig securityConfig = getSecurityConfig();
		String permissionProviderClassName = securityConfig.getAdminPermissionProvider();
		return newPermissionProvider(permissionProviderClassName);
	}

	private static SecurityConfig getSecurityConfig()
	{
		return ConfigFactory.getConfig(SecurityConfig.class);
	}

	private static PermissionsProvider newPermissionProvider(String permissionProviderClassName)
	{
		try
		{
			Class permissionsProviderClass = Class.forName(permissionProviderClassName);
			return (PermissionsProvider) permissionsProviderClass.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new SecurityException(e);
		}
		catch (InstantiationException e)
		{
			throw new SecurityException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new SecurityException(e);
		}
	}

	private static LoginInfoProvider newloginInfoProvider(String loginInfoProviderClassName)
	{
		try
		{
			Class loginInfoProviderClass = Class.forName(loginInfoProviderClassName);
			return (LoginInfoProvider) loginInfoProviderClass.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new SecurityException(e);
		}
		catch (InstantiationException e)
		{
			throw new SecurityException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * @return NamePasswordValidator
	 */
	public static NamePasswordValidator createPasswordValidator()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		Application application = applicationConfig.getApplicationInfo().getApplication();

		switch (application)
		{
			case PhizIC:
				return new UserPasswordValidator(SecurityService.SCOPE_CLIENT);
			case atm:
				return new UserPasswordValidator(SecurityService.SCOPE_CLIENT);
			default:
				if(applicationConfig.getApplicationInfo().isMobileApi())
					return new UserPasswordValidator(SecurityService.SCOPE_CLIENT);
				return new UserPasswordValidator(SecurityService.SCOPE_EMPLOYEE);

		}
	}

	/**
	 * @return CryptoService текущей конфигурации
	 */
	public static synchronized CryptoService cryptoService()
	{
		if (cryptoService == null){
			SecurityConfig securityConfig = getSecurityConfig();
			String cryptoServiceClassName = securityConfig.getCryptoServiceClassName();
			try
			{
				cryptoService = (CryptoService) Class.forName(cryptoServiceClassName).newInstance();
			}
			catch (InstantiationException e)
			{
				throw new SecurityException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new SecurityException(e);
			}
			catch (ClassNotFoundException e)
			{
				throw new SecurityException(e);
			}
		}
		return cryptoService;
	}

}
