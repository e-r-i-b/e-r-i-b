package com.rssl.phizic.business.addressBook.reports;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовая сущность записи лога работы с адресной книгой
 */

public abstract class ReportEntityBase
{
	private Long login;
	private String name;
	private String document;
	private Calendar birthday;
	private String tb;

	/**
	 * @return идентификатор логина
	 */
	public Long getLogin()
	{
		return login;
	}

	/**
	 * задать идентификатор логина
	 * @param login идентификатор логина
	 */
	public void setLogin(Long login)
	{
		this.login = login;
	}

	/**
	 * @return ФИО пользователя
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать ФИО пользователя
	 * @param name ФИО пользователя
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ДУЛ пользователя
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * задать ДУЛ пользователя
	 * @param document ДУЛ пользователя
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return ДР пользователя
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * задать ДР пользователя
	 * @param birthday ДР пользователя
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return ТБ клиента
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * задать ТБ клиента
	 * @param tb ТБ
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
