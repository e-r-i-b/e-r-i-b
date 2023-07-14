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
 * �������� - ������� ��������
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
	 * @param profileInfo ���������� � �������
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
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName ���
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return �������
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName �������
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return ��������
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName ��������
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * @return ���
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport ���
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return �������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb �������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @param birthDate ���� ��������
	 */
	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * ����� ������� �� ���������� � �������
	 * @param profileInfo ���������� � �������
	 * @return �������
	 * @throws Exception
	 */
	public static Profile findByProfileInfo(final ProfileInfo profileInfo) throws Exception
	{
		if (profileInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������� �� ����� ���� null");
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
