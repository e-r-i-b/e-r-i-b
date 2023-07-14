package com.rssl.phizic.business.ant.csaadmin.mapping;

/**
 * @author akrenev
 * @ created 11.12.2013
 * @ $Author$
 * @ $Revision$
 *
 *  Доступные подразделения ЦСА Админ
 */

@SuppressWarnings("ALL")
public class CSAAdminAllowedDepartment
{
	public static final String FULL_ACCESS = "*";

	private Long id;
	private CSAAdminLogin login;
	private String tb;
	private String osb;
	private String vsp;

	/**
	 * конструктор
	 */
	public CSAAdminAllowedDepartment(){}

	/**
	 * конструктор
	 * @param login логин
	 */
	public CSAAdminAllowedDepartment(CSAAdminLogin login)
	{
		this.login = login;
		this.tb = FULL_ACCESS;
		this.osb = FULL_ACCESS;
		this.vsp = FULL_ACCESS;
	}
}
