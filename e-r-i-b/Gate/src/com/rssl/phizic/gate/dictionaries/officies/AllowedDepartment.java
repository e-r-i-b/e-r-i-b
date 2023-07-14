package com.rssl.phizic.gate.dictionaries.officies;

/**
 * @author akrenev
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * доступное подразделение
 */

public class AllowedDepartment
{
	private String tb;
	private String osb;
	private String vsp;

	/**
	 * конструктор
	 */
	public AllowedDepartment(){}

	/**
	 * конструктор
	 * @param tb ТБ
	 * @param osb ОСБ
	 * @param vsp ВСП
	 */
	public AllowedDepartment(String tb, String osb, String vsp)
	{
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
	}

	/**
	 * @return ТБ
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * задать ТБ
	 * @param tb ТБ
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ОСБ
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * задать ОСБ
	 * @param osb ОСБ
	 */
	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	/**
	 * @return ВСП
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * задать ВСП
	 * @param vsp ВСП
	 */
	public void setVsp(String vsp)
	{
		this.vsp = vsp;
	}
}
