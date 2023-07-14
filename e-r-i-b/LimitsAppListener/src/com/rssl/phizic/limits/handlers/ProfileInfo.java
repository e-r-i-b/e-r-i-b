package com.rssl.phizic.limits.handlers;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Информация о профиле
 */
public class ProfileInfo
{
	private String firstName;
	private String surName;
	private String patrName;
	private String passport;
	private String tb;
	private Calendar birthDate;

	/**
	 * конструтор
	 * @param firstName имя
	 * @param surName фамилия
	 * @param patrName отчество
	 * @param passport ДУЛ
	 * @param tb тербанк
	 * @param birthDate дата рождения
	 */
	public ProfileInfo(String firstName, String surName, String patrName, String passport, String tb, Calendar birthDate)
	{
		this.firstName = firstName;
		this.surName = surName;
		this.patrName = patrName;
		this.passport = passport;
		this.tb = tb;
		this.birthDate = birthDate;
	}

	/**
	 * @return имя
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return фамилия
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @return отчество
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @return ДУЛ
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @return тербанк
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return дата рождения
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}
}
