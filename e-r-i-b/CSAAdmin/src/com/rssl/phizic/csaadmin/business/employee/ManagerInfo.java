package com.rssl.phizic.csaadmin.business.employee;

/**
 * @author akrenev
 * @ created 17.07.2014
 * @ $Author$
 * @ $Revision$
 *
 * Информация о персональном менеджере
 */

public class ManagerInfo
{
	private String id;
	private String name;
	private String email;
	private String phone;

	/**
	 * @return идентификатор менеджера
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * задать идентификатор менеджера
	 * @param id идентификатор менеджера
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return имя менеждера
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать имя менеждера
	 * @param name имя менеждера
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return адрес электронной почты
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * задать адрес электронной почты
	 * @param email адрес электронной почты
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return адрес электронной почты
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * задать номер телефона
	 * @param phone номер телефона
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
