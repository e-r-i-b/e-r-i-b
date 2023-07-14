package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Set;

/**
 * @author egorova
 * @ created 27.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentRestrictionImpl implements DepartmentRestriction
{
	public boolean accept(Long departmentId) throws BusinessException
	{
		if (departmentId == null || departmentId.equals(Long.valueOf(0)))
			return isSuperEmployee();

		return AllowedDepartmentsUtil.isDepartmentAllowed(departmentId);
	}

	private boolean isSuperEmployee()
	{
		SecurityConfig securityConfig= ConfigFactory.getConfig(SecurityConfig.class);
        String superUserName = securityConfig.getDefaultAdminName();
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();        return superUserName != null && superUserName.equals(employee.getLogin().getUserId());
	}
}
