package com.rssl.phizic.auth;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.util.AnonymousPrincipalCreator;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.io.Serializable;
import java.security.AccessControlException;
import java.security.Permission;
import java.security.PermissionCollection;

/**
 * User: Evgrafov
 * Date: 29.08.2005
 * Time: 14:12:45
 */
public class AuthModule implements Serializable
{
	public static final String AUTH_MODULE_KEY     = "com.rssl.phizic.AuthModule";
	/**
	 * Устанавливает для модуль аутентификации
	 * @param authModule модуль который следует сделать текущим
	 * или null для того чтоб убрать связь.
	 */
	public static void setAuthModule ( AuthModule authModule )
	{
		StoreManager.getCurrentStore().save(AUTH_MODULE_KEY, authModule);
	}

	/**
	 * Возвращает модуль аутентификации ассоциированный с нитью
	 * @return возвращает текущий модуль или null
	 */
	public static AuthModule getAuthModule ()
	{
		Store currentStore = StoreManager.getCurrentStore();
		if (currentStore == null)
			return null;
		return (AuthModule) currentStore.restore(AUTH_MODULE_KEY);
	}

	//*********************************************************************//
	//**************************  INSTANCE MEMBERS  ***********************//
	//*********************************************************************//
	private UserPrincipal principal; //текущий принципал (может быть анонимусом)
	private boolean isAnonymous; //является ли текущий принципал анонимусом. используется для оптимизации проверки
	private PermissionCollection perms; //права доступа текущего принципала
	private PermissionCollection anonymousPerms; //права доступа анонимного принципала

	/**
	 * @param principal принципал к которому относится текущий AuthModule
	 */
	public AuthModule(UserPrincipal principal)
	{
		init(principal, null);
	}

	/**
	 * @param principal принципал к которому относится текущий AuthModule
	 * @param provider поставщик прав ассоциированные с модулем
	 */
	public AuthModule(UserPrincipal principal, PermissionsProvider provider)
	{
		init(principal, provider);
	}

	private void init(UserPrincipal principal, PermissionsProvider provider)
	{
		this.principal = principal;
		isAnonymous = principal != null && principal.getAccessType().equals(AccessType.anonymous);

		//загружаем permission-ы текущего принципала
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		if (provider != null)
		{
			perms = provider.load(principal);
		}
		else if (principal != null && principal.getName().equals(securityConfig.getDefaultAdminName()))
		{
			PermissionsProvider permissionsProvider = SecurityFactory.createAdminPermissionProvider();
			perms = permissionsProvider.load(principal);
		}
		else if(principal.getAccessType().equals(AccessType.guest))
		{
			perms = SecurityFactory.createGuestPermissionProvider().load(principal);
		}
		else
		{
			perms = SecurityFactory.createPermissionProvider().load(principal);
		}
		perms.setReadOnly();

		//загружаем permission-ы анонимного принципала
		if (isAnonymousAllowed())
		{
			UserPrincipal anonymous = AnonymousPrincipalCreator.getAnonymousPrincipal();
			anonymousPerms = SecurityFactory.createPermissionProvider().load(anonymous);
			anonymousPerms.setReadOnly();
		}
	}

	/**
	 * @return принципал к которому относится текущий AuthModule
	 */
	public UserPrincipal getPrincipal()
	{
		return principal;
	}

	/**
	 * Проверяет наличие переданного Permission в правах текущего принципала
	 * если текущий принципал не имеет доступа к permission-у и не анонимус, то выполняется поиск по правам анонимного принципала
	 * @param permission ?
	 * @return true если разрешение есть, иначе - false
	 */
	public boolean implies(Permission permission)
	{
		boolean implies = perms.implies(permission);
		if (!implies && isAnonymousAllowed())
			implies = anonymousPerms.implies(permission);
		return implies;
	}

	/**
	 * Проверяет наличие наличие переданного Permission
	 * выбрасывает AccessControlException в случае если нет такого разрешения
	 * @param permission
	 * @throws AccessControlException
	 */
	public void checkPermission ( Permission permission ) throws AccessControlException
	{
		if (!implies(permission))
		{
			throw new AccessControlException("access denied "+permission, permission);
		}
	}

	private boolean isAnonymousAllowed()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return !isAnonymous && (applicationInfo.getApplication() == Application.PhizIC || applicationInfo.isMobileApi());
	}
}
