package com.rssl.phizicgate.csaadmin.service.types;

import com.rssl.phizic.gate.employee.ContactCenterEmployee;
import com.rssl.phizicgate.csaadmin.service.generated.ContactCenterEmployeeType;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * сотрудник контактного центра
 */

public class ContactCenterEmployeeImpl implements ContactCenterEmployee
{
	private Long id;
	private String externalId;
	private String name;
	private String department;
	private String area;

	/**
	 * конструктор
	 * @param employee источник данных
	 */
	public ContactCenterEmployeeImpl(ContactCenterEmployeeType employee)
	{
		id = employee.getId();
		externalId = employee.getExternalId();
		name = employee.getName();
		department = employee.getDepartment();
		area = employee.getArea();
	}

	public Long getId()
	{
		return id;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public String getName()
	{
		return name;
	}

	public String getDepartment()
	{
		return department;
	}

	public String getArea()
	{
		return area;
	}
}
