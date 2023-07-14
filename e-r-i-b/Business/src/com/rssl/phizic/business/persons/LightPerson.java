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
 * ������ ������ ������� (��� ����������, �������)
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
	 * @return �������������
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ��������, �� �������� ��������� ������
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * @param creationType ��� ��������, �� �������� ��������� ������
	 */
	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	/**
	 * @return ��� �������
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName ��� �������
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return ������� �������
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName ������� �������
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return �������� �������
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName �������� �������
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * @return ������������� �������������
	 */
	public Long getDepartmentId()
	{
		return departmentId;
	}

	/**
	 * @param departmentId ������������� �������������
	 */
	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * @param birthDay ���� ��������
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	/**
	 * @return �������������� ��� ������� ����� ��������������� �����������
	 */
	public UserRegistrationMode getUserRegistrationMode()
	{
		return userRegistrationMode;
	}

	/**
	 * @param userRegistrationMode �������������� ��� ������� ����� ��������������� �����������
	 */
	public void setUserRegistrationMode(UserRegistrationMode userRegistrationMode)
	{
		this.userRegistrationMode = userRegistrationMode;
	}
}
