package com.rssl.phizic.csaadmin.business.departments;

import com.rssl.phizic.csaadmin.business.login.Login;
import org.apache.commons.lang.StringUtils;

/**
 * @author akrenev
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Доступное подразделение
 */

public class AllowedDepartment
{
	public static final String FULL_ACCESS_MASK = "*";
	//используется маппинга
	@SuppressWarnings("UnusedDeclaration")
	private Long id;
	private Login login;
	private Department department;

	/**
	 * констрктор
	 */
	public AllowedDepartment(){}

	/**
	 * конструктор
	 * @param login логин
	 * @param department подразделение
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
	 * @return логин
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * задать логин
	 * @param login логин
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return подразделение
	 */
	public Department getDepartment()
	{
		return department;
	}

	/**
	 * задать подразделение
	 * @param department подразделение
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
	}
}
