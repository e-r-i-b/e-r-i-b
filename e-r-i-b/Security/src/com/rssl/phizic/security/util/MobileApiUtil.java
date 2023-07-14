package com.rssl.phizic.security.util;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.util.ApplicationUtil;

import java.security.AccessControlException;

/**
 * ”тилитный класс дл€ mAPI.
 * @author Dorzhinov
 * @ created 14.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileApiUtil
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);
	
	/**
	 * ¬озвращает номер версии API клиентского приложени€.
	 * ¬ерси€ беретс€ из контекста аутентификации, куда сохран€етс€ из параметра запроса version.
	 * @return
	 */
	public static VersionNumber getApiVersionNumber()
	{
		AuthenticationContext authContext = AuthenticationContext.getContext();
		return (authContext != null) ? authContext.getClientMobileAPIVersion() : null;
	}

	/**
	 * ѕровер€ет, что верси€ API > указанной
	 * ≈сли текущее приложение не mAPI или верси€ == null, возвращает false
	 * @param comparingVersion верси€ дл€ сравнени€
	 * @return true - если это mAPI и верси€ mAPI > указанной
	 */
	public static boolean isMobileApiGT(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.gt(comparingVersion);
	}

	/**
	 * ѕровер€ет, что верси€ API >= указанной
	 * ≈сли текущее приложение не mAPI или верси€ == null, возвращает false
	 * @param comparingVersion верси€ дл€ сравнени€
	 * @return true - если это mAPI и верси€ mAPI >= указанной
	 */
	public static boolean isMobileApiGE(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.ge(comparingVersion);
	}

	/**
	 * ѕровер€ет, что верси€ API < указанной
	 * ≈сли текущее приложение не mAPI или верси€ == null, возвращает false
	 * @param comparingVersion верси€ дл€ сравнени€
	 * @return true - если это mAPI и верси€ mAPI < указанной
	 */
	public static boolean isMobileApiLT(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.lt(comparingVersion);
	}

	/**
	 * ѕровер€ет, что верси€ API <= указанной
	 * ≈сли текущее приложение не mAPI или верси€ == null, возвращает false
	 * @param comparingVersion верси€ дл€ сравнени€
	 * @return true - если это mAPI и верси€ mAPI <= указанной
	 */
	public static boolean isMobileApiLE(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.le(comparingVersion);
	}

	/**
	 * @return работает ли клиент в доавторизационной зоне
	 */
	public static boolean isLimitedScheme()
	{
		UserPrincipal principal = AuthModule.getAuthModule().getPrincipal();
		if (principal == null)
		{
			LOG.error("ѕринципал не заполнен");
			return true; //возвращаем доавторизационную как более ограниченную
		}
		return principal.isMobileLimitedScheme();
	}

	/**
	 * @return работает ли клиент по light схеме
	 */
	public static boolean isLightScheme()
	{
		UserPrincipal principal = AuthModule.getAuthModule().getPrincipal();
		if (principal == null)
		{
			LOG.error("ѕринципал не заполнен");
			return true; //возвращаем light как более ограниченную
		}
		return principal.isMobileLightScheme();
	}

	/**
	 * @return работает ли клиент по full схеме
	 */
	public static boolean isFullScheme()
	{
		return !isLimitedScheme() && !isLightScheme();
	}

	/**
	 * @return работает ли клиент в авторизованной зоне (full или light)
	 */
	public static boolean isAuthorizedZone()
	{
		return !isLimitedScheme();
	}

	/**
	 * ѕровер€ет работает ли клиент по full схеме и если нет, то выбрасывает AccessControlException
	 */
	public static void checkFullVersion()
	{
		if (!isFullScheme())
			throw new AccessControlException("«апрос выполнен не из full-схемы.");
	}

	/**
	 * ѕровер€ет работает ли клиент в авторизованной зоне (full или light схемы) и если нет, то выбрасывает AccessControlException
	 */
	public static void checkAuthorizedZone()
	{
		if (!isAuthorizedZone())
			throw new AccessControlException("«апрос выполнен из доавторизационной схемы.");
	}

	/**
	 * @return работает ли клиент по light схеме
	 */
	public static Boolean isLightSchemeAuthContext()
	{
		MobileAppScheme mobileAppScheme = AuthenticationContext.getContext().getMobileAppScheme();
		return (mobileAppScheme == null)? null :mobileAppScheme.equals(MobileAppScheme.LIGHT);
	}
}
