package com.rssl.phizic.business.clients.list;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Коннектор клиента
 */

public class Connector
{
	private String userId;
	private String login;
	private Calendar lastLogonDate;

	/**
	 * @return идентификатор
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * задать идентификатор
	 * @param userId идентификатор
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return логин
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * задать логин
	 * @param login логин
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return дата последнего входа
	 */
	public Calendar getLastLogonDate()
	{
		return lastLogonDate;
	}

	/**
	 * задать дату последнего входа
	 * @param lastLogonDate дата последнего входа
	 */
	public void setLastLogonDate(Calendar lastLogonDate)
	{
		this.lastLogonDate = lastLogonDate;
	}
}
