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
 * ����� ��� �������� ������� �������� � ������� ������
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

		//���� ������ ��� ������
		if (!status.hasLastTimeVisit())
		{
			status.setLastAccessTime(new GregorianCalendar());
			status.setTrustUser(true);
		}
		//���� ������������ ��� ��������, ��������� ��� �� ������� ��������
		else
		{
			checkUserOnTrusted(status);
		}
	}

	/**
	 * ��������, �������� �� ������������ ����� ������� ��� �� N ������
	 * @param status ���������� � ��������� ������������
	 */
	private void checkUserOnTrusted(StatusDescriptionUserSecurityManager status)
	{
		Calendar lastAccessTime = status.getLastAccessTime();
		SimpleSecurityConfig config = (SimpleSecurityConfig) ConfigFactory.getConfig(SecurityConfig.class);
		long deltaOfTimeBetweenRequests = config.getCommonCaptchaDelay() * 1000;
		//���� ����� ����� ��������� ��������� ������ deltaOfTimeBetweenRequests ������, �� �� ������. �����, �� �������� ������������
		boolean isTrusted = DateHelper.getCurrentTimeInMillis() - lastAccessTime.getTimeInMillis() > deltaOfTimeBetweenRequests;
		status.setTrustUser(isTrusted);
		status.setLastAccessTime(new GregorianCalendar());
	}

	/**
	 * ��������, �������� �� �� ������������
	 * @return ��, ���� �� �������� ������������. ��� � ��������� ������.
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
	 * �������� ��������� ������ � ������� ������������
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
