package com.rssl.phizic.context;

import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeDataImpl;
import com.rssl.phizic.context.EmployeeDataProvider;

/**
 * @author niculichev
 * @ created 31.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class SimpleEmployeeDataProvider implements EmployeeDataProvider
{
	private ThreadLocal<EmployeeData> employeeData = new ThreadLocal<EmployeeData>();

	public SimpleEmployeeDataProvider(Employee employee)
	{
		employeeData.set(new EmployeeDataImpl(employee));
	}

	public EmployeeData getEmployeeData()
	{
		return employeeData.get();
	}

	public void setEmployeeData(EmployeeData data)
	{
		employeeData.set(data);
	}
}
