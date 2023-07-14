package com.rssl.phizic.web.employees;

import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 16.11.2006 Time: 12:25:52 To change this template use
 * File | Settings | File Templates.
 */
public class ListEmployeeGroupsForm extends ListFormBase
{
	private Long employeeId;
	private Employee employee;

	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	public Long getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Long personId)
    {
        this.employeeId = personId;
    }
}
