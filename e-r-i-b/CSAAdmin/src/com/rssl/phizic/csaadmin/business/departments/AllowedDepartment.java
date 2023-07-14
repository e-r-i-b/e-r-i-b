package com.rssl.phizic.csaadmin.business.departments;

import com.rssl.phizic.csaadmin.business.login.Login;
import org.apache.commons.lang.StringUtils;

/**
 * @author akrenev
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������������
 */

public class AllowedDepartment
{
	public static final String FULL_ACCESS_MASK = "*";
	//������������ ��������
	@SuppressWarnings("UnusedDeclaration")
	private Long id;
	private Login login;
	private Department department;

	/**
	 * ����������
	 */
	public AllowedDepartment(){}

	/**
	 * �����������
	 * @param login �����
	 * @param department �������������
	 */
	public AllowedDepartment(Login login, Department department)
	{
		this.login = login;
		this.department = getMasked(department);
	}

	private Department getMasked(Department source)
	{
		return new Department(getMasked(source.getTb()), getMasked(source.getOsb()), getMasked(source.getVsp()));
	}


	private String getMasked(String source)
	{
		return StringUtils.defaultIfEmpty(source, FULL_ACCESS_MASK);
	}

	/**
	 * @return �����
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * ������ �����
	 * @param login �����
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return �������������
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * ������ �������������
	 * @param department �������������
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}
}
