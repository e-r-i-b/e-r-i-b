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
 * �����-�������� ��� ����������� �����.
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

	/**  ��������, �� ��������� �� ����������� ������������ ������ �� ������ CAPTCHA
	 * @return ��, ���� ��������� ������� ���������. ��� � ��������� ������.
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
	 * ��������� ���������� ������� ����� ������.
	 * @param person ������� ������������.
	 * @return ���������� ����� ����� ������.
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
	 * ���������� ����� ��������� ������� ����� ������
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
	 * ��������� ������������� ����� ������� �������� ������.
	 * @return ���������: ������������ ����� ������� �������� ������.
	 */
	public static int getMaxCountAttemptCheckLogin()
	{
		return ConfigFactory.getConfig(SecurityConfig.class).getNumberOfLoginAttemptsAtRegistration();
	}

	/**
	 * ��������� ����� ����� ����� ������� ���������� ���� �����.
	 * @return ���������: ����� ����� ����� ������� ���������� ���� �����.
	 */
	public static int getMinuteToResetCaptchaAtRegistration()
	{
		return ConfigFactory.getConfig(SecurityConfig.class).getMinuteToResetCaptchaAtRegistration();
	}

	/**
	 * ��������, ����� �� ���������� �����. ����� ��������� 3 ���������: ���������� �������� ������, ������� �������� � �������� �� ��������� �����. ���� ����� ���������� �����, �� ������������ ���� � �������.
	 * @param request ������
	 * @return ��, ���� ����� ��������� �����. ��� � ��������� ������.
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
			log.error("������ ��������� CAPTCHA. ������� ��������� ����������� �����.", e);
			CaptchaServlet.setActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
			return true;
		}
	}

	/**
	 * ��������, ������������ �� CAPTCHA
	 * @param request ������
	 * @return ��, ���� CAPTCHA �������. ��� � ��������� ������
	 */
	public static boolean isViewCaptcha(HttpServletRequest request)
	{
		return CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
	}

	/**
	 * ��������, ��������� �� ������� CAPTCHA. ��������! ��������� ������ �� ����� �������� CAPTCHA, � �� �����.
	 * @param request ������
	 * @return ��, ���� ����� �� �����. ���, ���� ����� ���������.
	 */
	public static boolean isNotCorrectCaptcha(HttpServletRequest request)
	{
		return CaptchaServlet.isActiveCaptha(request, DEFAULT_CAPTCHA_SERVLET_NAME) && CaptchaServlet.wrongCaptcha(request, DEFAULT_CAPTCHA_SERVLET_NAME);
	}

	/**
	 * ��������, �������� �� ����� ������
	 * @return ��, ���� ����� ������ ���������� �����. ��� � ��������� ������
	 */
	private static boolean isCaptchaTurnOnAll(){
		return ((SimpleSecurityConfig)ConfigFactory.getConfig(SecurityConfig.class)).isTurnOnCaptchaAll();
	}

	/**
	 * ����� ������ � SECURITY_MANAGER �� ������ ������
	 */
	public static void resetSecurityManager(){
		SECURITY_MANAGER.reset(EMPTY_KEY);
	}

	/**
	 * ����� ������������ ��� ������������� ������, ��������� ��� � ������ � ������� ������ � ���������� ���.
	 * @param context
	 * @return  �����
	 */
	public static String setAndReturnToken(PageContext context)
	{
		HttpServletRequest request = (HttpServletRequest)context.getRequest();
		return setAndReturnToken(request);
	}

	/**
	 * ����� ������������ ��� ������������� ������, ��������� ��� � ������ � ������� ������ � ���������� ���.
	 * @param request ������
	 * @return �����
	 */
	public static String setAndReturnToken(HttpServletRequest request)
	{
		TokenProcessor tokenProcessor = TokenProcessor.getInstance();
		tokenProcessor.saveToken(request);
		return  (String)request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
	}
}
