package com.rssl.phizic.csaadmin.business.employee;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * сотрудник контактного центра
 */

public class ContactCenterEmployee
{
	private Long id;
	private String externalId;
	private String name;
	private String department;
	private String area;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return внешний идентификатор
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * задать внешний идентификатор
	 * @param externalId идентификатор
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return им€
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать им€
	 * @param name им€
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return подразделение
	 */
	public String getDepartment()
	{
		return department;
	}

	/**
	 * задать подразделение
	 * @param department подразделение
	 */
	public void setDepartment(String department)
	{
		this.department = department;
	}

	/**
	 * @return площадка
	 */
	public String getArea()
	{
		return area;
	}

	/**
	 * задать площадку
	 * @param area площадка
	 */
	public void setArea(String area)
	{
		this.area = area;
	}
}
