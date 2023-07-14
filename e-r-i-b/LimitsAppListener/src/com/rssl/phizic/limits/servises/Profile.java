package com.rssl.phizic.limits.servises;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.limits.handlers.ProfileInfo;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - профили клиентов
 */
public class Profile extends ActiveRecord
{
	private Long id;
	private String firstName;
	private String surName;
	private String patrName;
	private String passport;
	private String tb;
	private Calendar birthDate;

	/**
	 * ctor
	 */
	public Profile(){}

	/**
	 * ctor
	 * @param profileInfo информация о профиле
	 */
	public Profile(ProfileInfo profileInfo)
	{
		this.firstName = profileInfo.getFirstName();
		this.surName = profileInfo.getSurName();
		this.patrName = profileInfo.getPatrName();
		this.passport = profileInfo.getPassport();
		this.tb = profileInfo.getTb();
		this.birthDate = profileInfo.getBirthDate();
	}

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return имя
	 */
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

	/**
	 * @return фамилия
	 */
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

	/**
	 * @return отчество
	 */
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

	/**
	 * @return ДУЛ
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport ДУЛ
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return тербанк
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb тербанк
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return дата рождения
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @param birthDate дата рождения
	 */
	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * Найти профиль по информации о профиле
	 * @param profileInfo информация о профиле
	 * @return профиль
	 * @throws Exception
	 */
	public static Profile findByProfileInfo(final ProfileInfo profileInfo) throws Exception
	{
		if (profileInfo == null)
		{
			throw new IllegalArgumentException("Информация о профиле не может быть null");
		}
		return executeAtomic(new HibernateAction<Profile>()
		{
			public Profile run(Session session) throws Exception
			{
				return (Profile) session.getNamedQuery(Profile.class.getName() + ".findByProfileInfo")
						.setParameter("first_name", profileInfo.getFirstName())
						.setParameter("sur_name",   profileInfo.getSurName())
						.setParameter("patr_name",  profileInfo.getPatrName())
						.setParameter("passport",   profileInfo.getPassport())
						.setParameter("tb",         profileInfo.getTb())
						.setParameter("birth_date", profileInfo.getBirthDate())
						.uniqueResult();
			}
		});
	}
}
