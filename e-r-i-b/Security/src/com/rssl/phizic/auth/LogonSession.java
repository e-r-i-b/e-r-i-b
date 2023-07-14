package com.rssl.phizic.auth;

import com.rssl.phizic.common.types.ApplicationType;

import java.io.Serializable;

/**
 * @author krenev
 * @ created 31.10.2013
 * @ $Author$
 * @ $Revision$
 * Сессии, открытые в контексте конкретного логина и в разрезе типа приложения.
 */

public class LogonSession implements Serializable
{
	private Long loginId;
	private ApplicationType applicationType;
	private String sessionId;

	/**
	 * дефолтный конструктор
	 */
	public LogonSession()
	{
	}

	/**
	 * конструктор
	 * @param loginId идентифкатор логина
	 * @param applicationType тип приложения
	 * @param sessionId идентфикатор сессии
	 */
	public LogonSession(Long loginId, ApplicationType applicationType, String sessionId)
	{
		this.loginId = loginId;
		this.applicationType = applicationType;
		this.sessionId = sessionId;
	}

	/**
	 * @return идентифкатор сессии
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * установить идентификатор сессии
	 * @param sessionId идентфикатор сессии
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * @return идентфикатор логина
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * Установить идентфикатор логина
	 * @param loginId идентфикатор логина
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return тип приложения
	 */
	public ApplicationType getApplicationType()
	{
		return applicationType;
	}

	/**
	 * установить тип приложения
	 * @param applicationType тип приложения
	 */
	public void setApplicationType(ApplicationType applicationType)
	{
		this.applicationType = applicationType;
	}
}
