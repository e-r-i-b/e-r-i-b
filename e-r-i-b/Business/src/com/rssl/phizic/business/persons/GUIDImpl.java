package com.rssl.phizic.business.persons;

import com.rssl.phizic.gate.clients.GUID;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 08.12.14
 * @ $Author$
 * @ $Revision$
 *
 * внешний идентификатор клиента (ФИО+ДУЛ+ДР+ТБ)
 */
public class GUIDImpl implements GUID
{
	private String surName;
	private String firstName;
	private String patrName;
	private String passport;
	private Calendar birthDay;
	private String tb;

	/**
	 * ctor
	 */
	public GUIDImpl()
	{
	}

	/**
	 * ctor
	 * @param surName фамилия
	 * @param firstName имя
	 * @param patrName отчество
	 * @param passport паспорт
	 * @param birthDay дата рождения
	 * @param tb тербанк
	 */
	public GUIDImpl(String surName, String firstName, String patrName, String passport, Calendar birthDay, String tb)
	{
		this.surName = surName;
		this.firstName = firstName;
		this.patrName = patrName;
		this.passport = passport;
		this.birthDay = birthDay;
		this.tb = tb;
	}

	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName фамилия
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName имя
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName отчество
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport паспорт
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * @param birthDay дата рождения
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb тербанк департамента обслуживания
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
