package com.rssl.phizic.business.mdm;

import com.rssl.phizic.gate.csa.UserInfo;

import java.util.Calendar;

/**
 * Сущность для получения csaProfileId
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class MDMClientInfo
{
	private Long id;
	private Long csaProfileId;
	private String firstName;   // Имя пользователя
	private String patrName;    // Отчество пользователя
	private String surname;     // Фамилия пользователя
	private Calendar birthDate; // Дата рождения пользователя
	private String passport;    // ДУЛ пользователя
	private String tb;          // ТБ пользователя

	/**
	 * @return идентификатор для hibernate
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор для hibernate
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return Имя пользователя
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return Отчество пользователя
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @return Фамилия пользователя
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @return Дата рождения пользователя
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @return ДУЛ пользователя
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @return ТБ пользователя
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param csaProfileId идентификатор профиля в ЦСА
	 */
	public void setCsaProfileId(Long csaProfileId)
	{
		this.csaProfileId = csaProfileId;
	}

	/**
	 * @return идентификатор профиля в ЦСА
	 */
	public Long getCsaProfileId()
	{
		return csaProfileId;
	}

	/**
	 * @return UserInfo
	 */
	public UserInfo asUserInfo()
	{
		return new UserInfo(tb.equals("38") ? "99" : tb, firstName, surname, patrName, passport, birthDate);
	}

}
