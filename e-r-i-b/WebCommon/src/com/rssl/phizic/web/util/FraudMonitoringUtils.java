package com.rssl.phizic.web.util;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.Constants;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.WebContext;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static com.rssl.phizic.context.Constants.COOKIE_PM_DATA_ATTRIBUTE_NAME;
import static com.rssl.phizic.context.Constants.FLASH_SO_ATTRIBUTE_NAME;

/**
 * @author khudyakov
 * @ created 31.07.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringUtils
{
	private static final int COOKIE_MAX_AGE_PARAMETER_VALUE         = 365*24*60*60;         //365 дней в секундах
	private static final String PM_FSO_URL                          = "/%s/pmfso";
	private static final String PM_FSO_REGEXP                       = "[^0-9a-zA-Z/+=]";

	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Web);
	private static final Set<String> DENY_CONTEXT_PATH = new HashSet<String>();
	static
	{
		DENY_CONTEXT_PATH.add("/loginPromo.do");
		DENY_CONTEXT_PATH.add("/CSAFrontLogin.do");
		DENY_CONTEXT_PATH.add("/front/payOrderPaymentLogin.do");
		DENY_CONTEXT_PATH.add("/CSAFrontGuestLogin.do");
	}

	/**
	 * Сохранение полученных от ФМ значения device токенов в сессию
	 */
	public static void storeTokens()
	{
		if (StringHelper.isNotEmpty(RSAContext.getSystemDeviceTokenCookie()))
		{
			WebContext.getCurrentRequest().getSession().setAttribute(COOKIE_PM_DATA_ATTRIBUTE_NAME, RSAContext.getSystemDeviceTokenCookie());
		}
		if (StringHelper.isNotEmpty(RSAContext.getSystemDeviceTokenFSO()))
		{
			WebContext.getCurrentRequest().getSession().setAttribute(FLASH_SO_ATTRIBUTE_NAME, RSAContext.getSystemDeviceTokenFSO());
		}
	}

	/**
	 * Проставить cookie PMData
	 */
	public static void storeCookie()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if ((applicationInfo.isPhizIC() || applicationInfo.isAdminApplication() || applicationInfo.isCSA())
				&& RSAConfig.getInstance().isCookieSendActive())
		{
			HttpServletRequest request = WebContext.getCurrentRequest();
			if (!DENY_CONTEXT_PATH.contains(request.getServletPath()))
			{
				WebContext.getCurrentResponce().addCookie(getCookie(request));
			}
		}
	}

	private static Cookie getCookie(HttpServletRequest request)
	{
		RSAConfig config = RSAConfig.getInstance();

		Cookie cookie = new Cookie(COOKIE_PM_DATA_ATTRIBUTE_NAME, StringHelper.getEmptyIfNull(request.getSession().getAttribute(COOKIE_PM_DATA_ATTRIBUTE_NAME)));
		cookie.setMaxAge(COOKIE_MAX_AGE_PARAMETER_VALUE);
		cookie.setPath(StringHelper.getNullIfEmpty(config.getCookiePath()));
		if (StringHelper.isNotEmpty(config.getCookieDomainName()))
		{
			cookie.setDomain(config.getCookieDomainName());
		}
		cookie.setSecure(config.isCookieSecure());
		return cookie;
	}

	/**
	 * Проверка на активность работы сервиса Фрод мониторинга
	 * @return true - активен
	 */
	public static boolean isScriptsRSAActive()
	{
		return RSAConfig.getInstance().isSystemActive();
	}

	/**
	 * Проверка на активность работы сервиса Фрод мониторинга
	 * @return true - активен
	 */
	public static boolean isFSORSAActive()
	{
		return RSAConfig.getInstance().isFSOActive();
	}

	/**
	 * url сервлета FSO
	 * @return true - активен
	 */
	public static String getFSORSAServletUrl()
	{
		Application application = ApplicationConfig.getIt().getApplicationInfo().getApplication();
		if (application == null)
		{
			LOG.error("При определении url FSO сервлета не определено приложение.");
			return StringUtils.EMPTY;
		}
		return String.format(PM_FSO_URL, application.name());
	}

	/**
	 * Проверка на валидность FSO токена
	 * @return true - валиден
	 */
	public static boolean isFSOTokenValid()
	{
		Matcher m = Pattern.compile(PM_FSO_REGEXP).matcher(getFSOTokenValue());
		return m.find();
	}

	/**
	 * Получить значение флеш объекта, сохраненное в сессии
	 * @return значение флеш объекта
	 */
	public static String getFSOTokenValue()
	{
		return StringHelper.getEmptyIfNull(StoreManager.getCurrentStore().restore(FLASH_SO_ATTRIBUTE_NAME));
	}
}
