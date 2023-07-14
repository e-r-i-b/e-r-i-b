package com.rssl.phizic.csaadmin.business.session;

import com.rssl.phizic.csaadmin.business.login.Login;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Сессия
 */

public class Session
{
	private String sid;
	private Login login;
	private Calendar creationDate;
	private Calendar closeDate;
	private SessionState state;

	/**
	 * Пустой конструктор для хибернейта
	 */
	public Session()
	{
	}

	/**
	 * Открытие сессии для логина
	 * @param login - логин сотрудника
	 */
	public Session(Login login)
	{
		if (login == null)
		{
			throw new IllegalArgumentException("Логин не может быть null");
		}
		this.login = login;
		this.creationDate = Calendar.getInstance();
		this.state = SessionState.ACTIVE;
	}


	/**
	 * @return идентификатор сессии
	 */
	public String getSid()
	{
		return sid;
	}

	/**
	 * задать идентификатор сессии
	 * @param sid идентификатор сессии
	 */
	public void setSid(String sid)
	{
		this.sid = sid;
	}

	/**
	 * @return логин, для которого открылась сессия
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * задать логин, для которого открылась сессия
	 * @param login логин, для которого открылась сессия
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return дата создания сессии
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * задать дату создания сессии
	 * @param creationDate дата создания сессии
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return дата закрытия сессии
	 */
	public Calendar getCloseDate()
	{
		return closeDate;
	}

	/**
	 * задать дату закрытия сессии
	 * @param closeDate дата закрытия сессии
	 */
	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	/**
	 * @return состояние сессии
	 */
	public SessionState getState()
	{
		return state;
	}

	/**
	 * задать состояние сессии
	 * @param state состояние сессии
	 */
	public void setState(SessionState state)
	{
		this.state = state;
	}
}
