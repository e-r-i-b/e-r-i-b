package com.rssl.phizic.csaadmin.business.employee;

/**
 * @author akrenev
 * @ created 17.07.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ������������ ���������
 */

public class ManagerInfo
{
	private String id;
	private String name;
	private String email;
	private String phone;

	/**
	 * @return ������������� ���������
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ���������
	 * @param id ������������� ���������
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ���������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��� ���������
	 * @param name ��� ���������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ����� ����������� �����
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * ������ ����� ����������� �����
	 * @param email ����� ����������� �����
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return ����� ����������� �����
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * ������ ����� ��������
	 * @param phone ����� ��������
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
