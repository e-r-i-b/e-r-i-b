package com.rssl.phizic.business.helpers;

import com.rssl.auth.security.SessionSecurityManagerImp;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.captcha.CaptchaServlet;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SimpleSecurityConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.auth.security.SecurityManager;
import org.apache.struts.Globals;
import org.apache.struts.util.TokenProcessor;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import com.rssl.phizic.logging.system.Log;

/**
 * Класс-помошник для регистрации гостя.
 * @author usachev
 * @ created 16.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestRegistrationHelper
{
	protected static final String DEFAULT_CAPTCHA_SERVLET_NAME = "selfRegistrationCaptchaServlet";
	private static final SecurityManager SECURITY_MANAGER = new SessionSecurityManagerImp();
	private static final String EMPTY_KEY = null;
	private static final String PREFIX = "phiz-csa";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**  Проверка, не исчерпано ли колличество попытокввода логина до показа CAPTCHA
	 * @return Да, если количетво попыток исчерпано. Нет в противном случае.
	 */
	public static boolean exceededCountCheckLogin()
	{
		if (!PersonContext.isAvailable())
			return false;

		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Long checkLoginCount = getCheckLoginCount(person);

		return checkLoginCount >= getMaxCountAttemptCheckLogin();
	}

	/**
	 * Получение количества попыток ввода логина.
	 * @param person Текущий пользователь.
	 * @return Количество ввода ввода логина.
	 */
	private static Long getCheckLoginCount(ActivePerson person)
	{
		Long checkLoginCount = person.getCheckLoginCount();
		Calendar currentDate = Calendar.getInstance();
		Calendar lastAttempt = person.getLastFailureLoginCheck();
		if (lastAttempt == null || checkLoginCount == null)
		{
			lastAttempt = Calendar.getInstance();
			lastAttempt.setTimeInMillis(0L);
		}
		if (DateHelper.diff(currentDate, lastAttempt) > getMinuteToResetCaptchaAtRegistration() * DateHelper.MILLISECONDS_IN_MINUTE)
		{
			checkLoginCount = 0L;
		}
		return checkLoginCount;
	}

	/**
	 * Увеличение числа неудачных попыток ввода логина
	 */
	public static void incCheckLoginCount()
	{
		if (!PersonContext.isAvailable())
		{
			return;
		}
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Long checkLoginCount = getCheckLoginCount(person);
		checkLoginCount = checkLoginCount == null ? 0L : ++checkLoginCount;
		person.setCheckLoginCount(checkLoginCount);
		person.setLastFailureLoginCheck(Calendar.getInstance());
	}

	/**
	 * Получение максимального числа попыток проверки логина.
	 * @return Настройка: максимальное число попыток проверки логина.
	 */
	public static int getMaxCountAttemptCheckLogin()
	{
		return ConfigFactory.getConfig(SecurityConfig.class).getNumberOfLoginAttemptsAtRegistration();
	}

	/**
	 * Получение числа минут через сколько сброситься ввод капчи.
	 * @return настройка: число минут через сколько сброситься ввод капчи.
	 */
	public static int getMinuteToResetCaptchaAtRegistration()
	{
		return ConfigFactory.getConfig(SecurityConfig.class).getMinuteToResetCaptchaAtRegistration();
	}

	/**
	 * Проверка, нужно ли показывать капчу. Метод учитывает 3 параметра: количество перебора логина, частота запросов и включена ли глобально капча. Если нужно показывать капчу, то выставляется флаг в запросе.
	 * @param request Запрос
	 * @return Да, если нужно показвать капчу. Нет в противном случае.
	 */
	public static boolean needShowCaptcha(HttpServletRequest request)
	{
		try
		{
			SECURITY_MANAGER.processUserAction(EMPTY_KEY);
			if (isCaptchaTurnOnAll() || exceededCountCheckLogin() || !SECURITY_MANAGER.userTrusted(EMPTY_KEY))
			{
				CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
				return true;
			}
			CaptchaServlet.resetActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			return false;
		}
		catch (Exception e)
		{
			log.error("Ошибка установки CAPTCHA. Принята стратегия отображения капчи.", e);
			CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			return true;
		}
	}

	/**
	 * Проверка, активирована ли CAPTCHA
	 * @param request Запрос
	 * @return Да, если CAPTCHA активна. Нет в противном случае
	 */
	public static boolean isViewCaptcha(HttpServletRequest request)
	{
		return CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
	}

	/**
	 * Проверка, правильно ли введена CAPTCHA. ВНИМАНИЕ! Ожидается приход не самих символов CAPTCHA, а их кодов.
	 * @param request Запрос
	 * @return Да, если капча НЕ верна. Нет, если капча корректна.
	 */
	public static boolean isNotCorrectCaptcha(HttpServletRequest request)
	{
		return CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME) && CaptchaServlet.wrongCaptcha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
	}

	/**
	 * Проверка, включена ли капча всегда
	 * @return Да, если нужно всегда отображать капчу. Нет в противном случае
	 */
	private static boolean isCaptchaTurnOnAll(){
		return ((SimpleSecurityConfig)ConfigFactory.getConfig(SecurityConfig.class)).isTurnOnCaptchaAll();
	}

	/**
	 * Сброс данных в SECURITY_MANAGER по данной сессии
	 */
	public static void resetSecurityManager(){
		SECURITY_MANAGER.reset(EMPTY_KEY);
	}

	/**
	 * Метод предназначен для генерирования токена, установку его в сессию и возврат токена в вызывающий код.
	 * @param context
	 * @return  Токен
	 */
	public static String setAndReturnToken(PageContext context)
	{
		HttpServletRequest request = (HttpServletRequest)context.getRequest();
		return setAndReturnToken(request);
	}

	/**
	 * Метод предназначен для генерирования токена, установку его в сессию и возврат токена в вызывающий код.
	 * @param request Запрос
	 * @return Токен
	 */
	public static String setAndReturnToken(HttpServletRequest request)
	{
		TokenProcessor tokenProcessor = TokenProcessor.getInstance();
		tokenProcessor.saveToken(request);
		return  (String)request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
	}
}
