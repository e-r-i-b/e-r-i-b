package com.rssl.auth.security;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SimpleSecurityConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.WebContext;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSession;

/**
 * Класс для проверки частоты запросов с помощью сессии
 *
 * @author usachev
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 */
public class SessionSecurityManagerImp implements SecurityManager
{
	private static final String STATUS_OF_TRUSTED = "STATUS_OF_TRUSTED";
	private static final String PREFIX = "phiz-csa";

	public void processUserAction(String key)
	{
		processUserAction();
	}

	public boolean userTrusted(String key)
	{
		return userTrusted();
	}

	public void reset(String key)
	{
		reset();
	}

	private void processUserAction()
	{
		HttpSession session = WebContext.getCurrentRequest().getSession();
		if (session == null)
		{
			return;
		}
		StatusDescriptionUserSecurityManager status = (StatusDescriptionUserSecurityManager) session.getAttribute(STATUS_OF_TRUSTED);
		if (status == null)
		{
			status = new StatusDescriptionUserSecurityManager();
			session.setAttribute(STATUS_OF_TRUSTED, status);
		}

		//Если первый раз пришли
		if (!status.hasLastTimeVisit())
		{
			status.setLastAccessTime(new GregorianCalendar());
			status.setTrustUser(true);
		}
		//Если пользователь уже приходил, проверяем его на частоту запросов
		else
		{
			checkUserOnTrusted(status);
		}
	}

	/**
	 * Проверка, отправил ли пользователь ответ быстрее чем за N секунд
	 * @param status Информация о состоянии пользователя
	 */
	private void checkUserOnTrusted(StatusDescriptionUserSecurityManager status)
	{
		Calendar lastAccessTime = status.getLastAccessTime();
		SimpleSecurityConfig config = (SimpleSecurityConfig) ConfigFactory.getConfig(SecurityConfig.class);
		long deltaOfTimeBetweenRequests = config.getCommonCaptchaDelay() * 1000;
		//Если время между запросами составило больше deltaOfTimeBetweenRequests секунд, то всё хорошо. Иначе, не доверяем пользователю
		boolean isTrusted = DateHelper.getCurrentTimeInMillis() - lastAccessTime.getTimeInMillis() > deltaOfTimeBetweenRequests;
		status.setTrustUser(isTrusted);
		status.setLastAccessTime(new GregorianCalendar());
	}

	/**
	 * Проверка, доверяем ли мы пользователю
	 * @return Да, если мы доверяем пользователю. Нет в противном случае.
	 */
	private boolean userTrusted()
	{
		HttpSession session = WebContext.getCurrentRequest().getSession();
		if (session == null)
		{
			return false;
		}
		StatusDescriptionUserSecurityManager status = (StatusDescriptionUserSecurityManager) session.getAttribute(STATUS_OF_TRUSTED);
		return status.isTrustUser();
	}

	/**
	 * Сбросить настройки данным о заходах пользователя
	 */
	private void reset()
	{
		HttpSession session = WebContext.getCurrentRequest().getSession();
		if (session == null)
		{
			return;
		}
		session.removeAttribute(STATUS_OF_TRUSTED);
	}
}
