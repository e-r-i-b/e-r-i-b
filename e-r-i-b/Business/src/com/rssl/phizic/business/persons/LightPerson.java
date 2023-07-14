package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.common.types.client.CreationType;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 05.11.14
 * @ $Author$
 * @ $Revision$
 *
 * Легкая версия клиента (без документов, адресов)
 */
public class LightPerson
{
	private long id;
	private CreationType creationType;
	private String firstName;
	private String surName;
	private String patrName;
	private Long departmentId;
	private Calendar birthDay;
	private UserRegistrationMode userRegistrationMode;

	/**
	 * @return иднетификатор
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id иднетификатор
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return Тип договора, по которому подключен клиент
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * @param creationType Тип договора, по которому подключен клиент
	 */
	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	/**
	 * @return имя клиента
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName имя клиента
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return фамилия клиента
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName фамилия клиента
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return отчество клиента
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName отчество клиента
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * @return идентификатор подразделения
	 */
	public Long getDepartmentId()
	{
		return departmentId;
	}

	/**
	 * @param departmentId идентификатор подразделения
	 */
	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	/**
	 * @return день рождения
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * @param birthDay день рождения
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	/**
	 * @return Индивидуальный для клиента режим самостоятельной регистрации
	 */
	public UserRegistrationMode getUserRegistrationMode()
	{
		return userRegistrationMode;
	}

	/**
	 * @param userRegistrationMode Индивидуальный для клиента режим самостоятельной регистрации
	 */
	public void setUserRegistrationMode(UserRegistrationMode userRegistrationMode)
	{
		this.userRegistrationMode = userRegistrationMode;
	}
}
