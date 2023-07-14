package com.rssl.phizic.business.notification.ip.unusual;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 10.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Оповещение о входе с нестандартного IP
 */

public class UnusualIPNotification
{
	private Long id;
	private Calendar dateCreated;
	private Long loginId;
	private long attemptsCount;
	private String message;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дата создания
	 */
	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	/**
	 * задать дата создания
	 * @param dateCreated дата
	 */
	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	/**
	 * @return идентификатор логина получателя
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * задать идентификатор логина получателя
	 * @param loginId идентификатор логина
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return количество попыток
	 */
	public long getAttemptsCount()
	{
		return attemptsCount;
	}

	/**
	 * задать количество попыток
	 * @param attemptsCount количество попыток
	 */
	public void setAttemptsCount(long attemptsCount)
	{
		this.attemptsCount = attemptsCount;
	}

	/**
	 * @return сообщение в лог
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * задать сообщение в лог
	 * @param message сообщение
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}
