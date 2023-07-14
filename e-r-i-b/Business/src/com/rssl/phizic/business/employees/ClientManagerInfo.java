package com.rssl.phizic.business.employees;

import com.rssl.phizic.gate.employee.ManagerInfo;

/**
 * @author akrenev
 * @ created 17.07.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ������������ ���������
 */

public class ClientManagerInfo implements ManagerInfo
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
