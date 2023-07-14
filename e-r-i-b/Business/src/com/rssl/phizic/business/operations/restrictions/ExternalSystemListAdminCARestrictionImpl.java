package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;

/**
 * @author: vagin
 * @ created: 07.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class ExternalSystemListAdminCARestrictionImpl implements ExternalSystemListAdminCARestriction
{
	public boolean accept() throws BusinessException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		Long employeeDepartmentId = employee.getDepartmentId();
		return AllowedDepartmentsUtil.isDepartmentAllowed(employeeDepartmentId);
	}
}
