package com.rssl.phizic.operations.employees;

/**
 * @author akrenev
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Оболочка доступного подразделения при редактировании доступа подразделений сотруднику
 */

public class AllowedDepartmentWrapper
{
	private Long id;
	private String name;
	private String tb;
	private String osb;
	private String vsp;
	private boolean allowed;

	/**
	 * дефолтный конструктор
	 */
	public AllowedDepartmentWrapper()
	{}

	/**
	 * конструктор
	 * @param tb ТБ
	 * @param osb ОСБ
	 * @param vsp ВСП
	 * @param name название подразделения
	 * @param allowed доступно ли подразделение
	 */
	public AllowedDepartmentWrapper(String tb, String osb, String vsp, String name, boolean allowed)
	{
		this.tb = tb;
		this.osb = osb;
		this.vsp = vsp;
		this.name = name;
		this.allowed = allowed;
	}

	/**
	 * @return идентификатор подразделения
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return название подразделения
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return ТБ
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return ОСБ
	 */
	public String getOsb()
	{
		return osb;
	}

	/**
	 * @return ВСП
	 */
	public String getVsp()
	{
		return vsp;
	}

	/**
	 * @return доступно ли подразделение
	 */
	public boolean isAllowed()
	{
		return allowed;
	}
}
