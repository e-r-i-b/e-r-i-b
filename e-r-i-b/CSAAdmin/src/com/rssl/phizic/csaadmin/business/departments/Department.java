package com.rssl.phizic.csaadmin.business.departments;

/**
 * @author akrenev
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Подразделение
 */

public class Department
{
	private Long id;
	private String tb;
	private String osb;
	private String vsp;

	/**
	 * конструктор
	 */
	public Department()
	{}

	/**
	 * конструктор
	 * @param tb ТБ
	 * @param osb ОСБ
	 * @param vsp ВСП
	 */
	public Department(String tb, String osb, String vsp)
	{
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
	}

	/**
	 * копирующий конструктор
	 * @param department источник данных
	 */
	public Department(Department department)
	{
		this(department.getTb(), department.getOsb(), department.getVsp());
	}

	/**
	 * @return номер ТБ
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * задать номер ТБ
	 * @param tb номер ТБ
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return номер ОСБ
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * задать номер ОСБ
	 * @param osb номер ОСБ
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return номер ВСП
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * задать номер ВСП
	 * @param vsp номер ВСП
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}
}
