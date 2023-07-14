package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeDataProvider;

import java.util.List;

/**
 * @author osminin
 * @ created 03.06.2009
 * @ $Author$
 * @ $Revision$
 */

public class AdminEmployeeRestrictionImpl implements EmployeeRestriction
{
	private static final String SERVICE_NAME = "EmployeeManagement";

	public boolean accept(Employee employee) throws BusinessException
	{
		EmployeeDataProvider employeeProvider = EmployeeContext.getEmployeeDataProvider();
		Employee providerEmployee = employeeProvider.getEmployeeData().getEmployee();

		Boolean countAllowedEmpl = AllowedDepartmentsUtil.isDepartmentAllowed(employee.getDepartmentId());

		SchemeOwnService shemeService = new SchemeOwnService();
		AccessScheme accessSheme = shemeService.findScheme(providerEmployee.getLogin(), AccessType.employee);
		List<Service> services = accessSheme.getServices();
		return (countAllowedEmpl && getService(services));
	}

	private boolean getService(List<Service> services)
	{
		for (Service service: services)
		{
			if (service.getKey().equals(SERVICE_NAME))
				return true;
		}
		return false;	
	}
}
